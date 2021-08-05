package com.sorokin.dogWalkingService.myPlugin.services.jira;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueInputParametersImpl;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.user.ApplicationUser;
import com.sorokin.dogWalkingService.myPlugin.managers.ClientManager;
import com.sorokin.dogWalkingService.myPlugin.managers.DogManager;
import com.sorokin.dogWalkingService.myPlugin.models.Client;
import com.sorokin.dogWalkingService.myPlugin.models.Dog;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class DogIssueService {

    private final ActiveObjects ao;
    private final ClientManager clientManager;
    private final DogManager dogManager;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy", Locale.ENGLISH);


    public DogIssueService(ActiveObjects ao){
        this.ao = ao;
        clientManager = ClientManager.create(ao);
        dogManager = DogManager.create(ao);
    }

    private String findFullName(String id) throws Exception{
        Client client = clientManager.getByUniqueId(id);
        return client.getName() + " " + client.getLastName();
    }

    private String findIssueId(String dogId) {
        Dog dog = dogManager.getByUniqueId(dogId);
        return dog.getIssueId();
    }

    public void updateIssue(Map<CustomField, Object> map, MutableIssue issue) throws Exception {

        ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        for (Map.Entry<CustomField, Object> entry: map.entrySet()) {
            try {
                issue.setCustomFieldValue(entry.getKey(), entry.getValue());
            } catch (Exception ex) {
                throw new Exception("ошибка здеся   " + ex);
            }

        }

        try {
            ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false);
        } catch (Exception ex) {
            throw new Exception("вот тута ошибка в конце    " + ex);
        }

    }

    public void deleteAllByOwnerId (String ownerId) throws Exception{
        for (Dog dogs : dogManager.getByOwnerId(ownerId)) {
            try {
                deleteIssue(dogs.getUniqueId());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage() + " no pets ");
            }

        }

    }

    public void deleteIssue(String uniqueId) throws Exception{
        try {
            IssueService.DeleteValidationResult deleteValidationResult = ComponentAccessor
                    .getIssueService().validateDelete(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(findIssueId(uniqueId)));

            if(deleteValidationResult.isValid()) {
                ComponentAccessor.getIssueService().delete(ComponentAccessor
                        .getJiraAuthenticationContext().getLoggedInUser(), deleteValidationResult);
            }

        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public boolean updateChangeStatusIssue(Dog dog) {
        try {
            int transitionId = 31;

            if(dog.getDogStatus().toString().equals("WALKING")) {
                transitionId = 21;
            }

            IssueService.TransitionValidationResult transitionValidationResult = ComponentAccessor
                    .getIssueService().validateTransition(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(findIssueId(dog.getUniqueId())),
                            transitionId, new IssueInputParametersImpl());

            if(transitionValidationResult.isValid()) {
                ComponentAccessor.getIssueService().transition(
                        ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                        transitionValidationResult);

                return true;
            }

        }catch (Exception ex){
            String exs = ex.getMessage();
        }
        return false;
    }

    public Issue create(Dog dog) throws Exception{
        IssueService issueService = ComponentAccessor.getIssueService();
        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();

        issueInputParameters.setProjectId(IssueConstants.PROJECT_ID.numId);
        issueInputParameters.setIssueTypeId(IssueConstants.ISSUE_TYPE_DOG_ID.id);
        issueInputParameters.setSummary(dog.getBreed() + " " + dog.getDogName());

        issueInputParameters.addCustomFieldValue("customfield_10101", dog.getDogName());
        issueInputParameters.addCustomFieldValue("customfield_10100", dog.getGender().getDescription());
        issueInputParameters.addCustomFieldValue("customfield_10102", dateFormat.format(dog.getDogBirthDate()));
        issueInputParameters.addCustomFieldValue("customfield_10103", dog.getBreed());
        issueInputParameters.addCustomFieldValue("customfield_10104", dog.getColor());
        issueInputParameters.addCustomFieldValue("customfield_10105", dog.getDogCharacter());
        issueInputParameters.addCustomFieldValue("customfield_10106", dog.getDogStatus().getDescription());
        issueInputParameters.addCustomFieldValue("customfield_10121", findFullName(dog.getOwnerId()));

        IssueService.CreateValidationResult createValidationResult = issueService
                .validateCreate(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                        issueInputParameters);

        if(createValidationResult.isValid()){
            return issueService.create(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                    createValidationResult).getIssue();
        } else {
            throw new Exception(createValidationResult.getErrorCollection().toString() +
                    createValidationResult.getWarningCollection().toString() +
                    "//////     " + dateFormat.format(dog.getDogBirthDate()) +  "       //////" +
                    dog.getDogStatus().toString() + "  " + dog.getDogStatus() + "  гендер = " +
                    dog.getGender());
        }

    }
}
