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
import com.sorokin.dogWalkingService.myPlugin.managers.DogWalkerManager;
import com.sorokin.dogWalkingService.myPlugin.models.DogWalker;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class DogWalkerIssueService {

    private ActiveObjects ao;
    private final DogWalkerManager dogWalkerManager;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy", Locale.ENGLISH);

    public DogWalkerIssueService(ActiveObjects ao) {
        this.ao = ao;
        dogWalkerManager = DogWalkerManager.create(ao);
    }

    private String findIssueId(String dogWalkerId) {
        DogWalker dogWalker = dogWalkerManager.getByUniqueId(dogWalkerId);
        return dogWalker.getIssueId();
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

    public void deleteIssue(String dogWalkerId) throws Exception{
        try {
            IssueService.DeleteValidationResult deleteValidationResult = ComponentAccessor
                    .getIssueService().validateDelete(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(findIssueId(dogWalkerId)));

            if(deleteValidationResult.isValid()){
                ComponentAccessor.getIssueService().delete(ComponentAccessor
                        .getJiraAuthenticationContext().getLoggedInUser(),
                        deleteValidationResult);
            }

        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public void changeIssueStatus(DogWalker dogWalker) throws Exception{
        try {
            int transitionId = 31;

            if (dogWalker.getDogWalkerStatus().toString().equals("BUSY")) transitionId = 21;

            IssueService.TransitionValidationResult transitionValidationResult = ComponentAccessor
                    .getIssueService().validateTransition(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(findIssueId(dogWalker.getUniqueId())),
                            transitionId, new IssueInputParametersImpl());

            if (transitionValidationResult.isValid()) {
                ComponentAccessor.getIssueService().transition(ComponentAccessor
                        .getJiraAuthenticationContext().getLoggedInUser(), transitionValidationResult);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Issue create(DogWalker dogWalker) throws Exception{

        IssueService issueService = ComponentAccessor.getIssueService();
        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();

        issueInputParameters.setProjectId(IssueConstants.PROJECT_ID.numId);
        issueInputParameters.setIssueTypeId(IssueConstants.ISSUE_TYPE_DOG_WALKER_ID.id);
        issueInputParameters.setSummary(dogWalker.getName() + " " + dogWalker.getLastName());

        issueInputParameters.addCustomFieldValue("customfield_10108",dogWalker.getName());
        issueInputParameters.addCustomFieldValue("customfield_10109",dogWalker.getLastName());
        issueInputParameters.addCustomFieldValue("customfield_10110", dogWalker.getMiddleName());
        issueInputParameters.addCustomFieldValue("customfield_10111", dateFormat.format(dogWalker.getBirthDate()));
        issueInputParameters.addCustomFieldValue("customfield_10112", dogWalker.getPhoneNumber().toString());
        issueInputParameters.addCustomFieldValue("customfield_10113", dogWalker.getEmail());
        issueInputParameters.addCustomFieldValue("customfield_10115", dogWalker.getDogWalkerStatus().getDescription());

        IssueService.CreateValidationResult createValidationResult = issueService
                .validateCreate(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                        issueInputParameters);

        if(createValidationResult.isValid()){
            return issueService.create(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                    createValidationResult).getIssue();
        } else {
            throw new Exception(createValidationResult.getErrorCollection().toString() +
                    createValidationResult.getWarningCollection().toString() +
                    "//////     " + dateFormat.format(dogWalker.getBirthDate()) +  "       //////");
        }

    }
}
