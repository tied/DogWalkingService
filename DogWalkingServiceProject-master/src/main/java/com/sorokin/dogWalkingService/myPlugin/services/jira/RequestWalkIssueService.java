package com.sorokin.dogWalkingService.myPlugin.services.jira;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueInputParametersImpl;
import com.sorokin.dogWalkingService.myPlugin.managers.ClientManager;
import com.sorokin.dogWalkingService.myPlugin.managers.DogManager;
import com.sorokin.dogWalkingService.myPlugin.managers.DogWalkerManager;
import com.sorokin.dogWalkingService.myPlugin.managers.RequestWalkManager;
import com.sorokin.dogWalkingService.myPlugin.models.Client;
import com.sorokin.dogWalkingService.myPlugin.models.Dog;
import com.sorokin.dogWalkingService.myPlugin.models.DogWalker;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestWalkIssueService {

    private final ActiveObjects ao;
    private final ClientManager clientManager;
    private final DogManager dogManager;
    private final DogWalkerManager dogWalkerManager;
    private final RequestWalkManager requestWalkManager;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy h:mm a", Locale.ENGLISH);

    public RequestWalkIssueService(ActiveObjects ao) {
        this.ao = ao;
        clientManager = ClientManager.create(ao);
        dogManager = DogManager.create(ao);
        dogWalkerManager = DogWalkerManager.create(ao);
        requestWalkManager = new RequestWalkManager(ao);
    }

    private String findDogWalker(String id) {
        if (!id.equals("No dog walker")) {
            DogWalker dogWalker = dogWalkerManager.getByUniqueId(id);
            return dogWalker.getLastName() + " " + dogWalker.getName();
        } else {
            return "No dog walker";
        }
    }

    private String findFullName(String id) {
        Client client = clientManager.getByUniqueId(id);
        return client.getLastName() + " " + client.getName();
    }

    private String findDogNameAndBreed(String id) {
        Dog dog = dogManager.getByUniqueId(id);
        return dog.getBreed() + " " + dog.getDogName();
    }

    private String findIdIssue(String id) {
        RequestWalk requestWalk = requestWalkManager.getByUniqueId(id);
        return requestWalk.getIssueId();
    }

    public void updateIssue(RequestWalk requestWalk) {
        try{
            IssueService issueService = ComponentAccessor.getIssueService();
            IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();

            RequestWalk updatedRequestWalk = requestWalkManager.getByUniqueId(requestWalk.getUniqueId());

            issueInputParameters.addCustomFieldValue("customfield_10120",
                    updatedRequestWalk.getRequestWalkStatus().toString());
            issueInputParameters.addCustomFieldValue("customfield_10122",
                    findDogWalker(updatedRequestWalk.getDogWalkerId()));

            IssueService.UpdateValidationResult updateValidationResult = ComponentAccessor
                    .getIssueService().validateUpdate(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(updatedRequestWalk.getIssueId()), issueInputParameters);

            if(updateValidationResult.isValid()) {
                ComponentAccessor.getIssueService().update(ComponentAccessor
                                .getJiraAuthenticationContext().getLoggedInUser(),
                        updateValidationResult);
            }

        }catch (Exception ex) {
            String exs = ex.getMessage();
        }
    }

    public void changeIssueStatus(RequestWalk requestWalk) {
        try {
            int transitionId = 31;

            if (requestWalk.getRequestWalkStatus().toString().equals("WALKING")) transitionId = 21;

            IssueService.TransitionValidationResult transitionValidationResult = ComponentAccessor
                    .getIssueService().validateTransition(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(findIdIssue(requestWalk.getUniqueId())),
                            transitionId, new IssueInputParametersImpl());

            if (transitionValidationResult.isValid()) {
                ComponentAccessor.getIssueService().transition(ComponentAccessor
                        .getJiraAuthenticationContext().getLoggedInUser(), transitionValidationResult);
            }

        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
    }

    public Issue create(RequestWalk requestWalk) throws Exception {
        try {
            IssueService issueService = ComponentAccessor.getIssueService();
            IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();

            issueInputParameters.setProjectId(IssueConstants.PROJECT_ID.numId);
            issueInputParameters.setIssueTypeId(IssueConstants.ISSUE_TYPE_REQUEST_WALK_ID.id);
            issueInputParameters.setSummary("Owner: " + findFullName(requestWalk.getClientId())
                    + " Pet: " + findDogNameAndBreed(requestWalk.getPetId()));

            String walkDuration = Double.toString(requestWalk.getWalkDuration());

            try {
                issueInputParameters.addCustomFieldValue("customfield_10116",
                        requestWalk.getRequestPlace());
            } catch (Exception ex) {
                throw new Exception("   реквест пласе  " + ex + "   ");
            }

            // '2021-07-01T14:23'
            try {

//                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
//                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
//                LocalDate date2 = LocalDate.parse(requestWalk.getTimeWalk(), inputFormatter);
//                String formattedDate = outputFormatter.format(date2);
//                System.out.println(formattedDate); // prints 10-04-2018

                // надо бы поправить
                issueInputParameters.addCustomFieldValue("customfield_10117", dateFormat.format(new Date()));

            } catch (Exception ex) {
                throw new Exception("ошибка с временем датой    " + ex + "  " + ex.getMessage() + "  " + requestWalk.getTimeWalk() + "  " );
            }

            try {
                issueInputParameters.addCustomFieldValue("customfield_10118",
                        findDogNameAndBreed(requestWalk.getPetId()));
            } catch (Exception ex) {
                throw new Exception("   гет пет айди  " + ex + "   ");
            }


            try {
                issueInputParameters.addCustomFieldValue("customfield_10200",
                        walkDuration);
            } catch (Exception ex) {
                throw new Exception("ошибка там с продолжительностью" + ex + ex.getMessage());
            }

            try {
                issueInputParameters.addCustomFieldValue("customfield_10120",
                        requestWalk.getRequestWalkStatus().getDescription()); // возможно ошибка

                issueInputParameters.addCustomFieldValue("customfield_10121",
                        findFullName(requestWalk.getClientId()));

                issueInputParameters.addCustomFieldValue("customfield_10122",
                        findDogWalker(requestWalk.getDogWalkerId()));
            } catch (Exception ex) {
                throw new Exception("в конце ошикбка  " +  ex + ex.getMessage());
            }


            IssueService.CreateValidationResult createValidationResult = issueService
                    .validateCreate(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            issueInputParameters);

            if (createValidationResult.isValid()) {
                return issueService.create(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                        createValidationResult).getIssue();
            } else {
                throw new Exception(createValidationResult.getErrorCollection().toString() +
                        createValidationResult.getWarningCollection().toString());
            }


        } catch (Exception ex) {
            throw new Exception(" ////// Здесь где-то ошибка  " + ex + " :::::: " + " :::::: ////// ");
        }
    }
}
