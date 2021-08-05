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
import com.sorokin.dogWalkingService.myPlugin.managers.*;
import com.sorokin.dogWalkingService.myPlugin.models.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class RequestWalkIssueService {

    private final ActiveObjects ao;
    private final ClientManager clientManager;
    private final DogManager dogManager;
    private final DogWalkerManager dogWalkerManager;
    private final RequestWalkManager requestWalkManager;

    private final LogManager logManager;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy h:mm a", Locale.ENGLISH);

    public RequestWalkIssueService(ActiveObjects ao) {
        this.ao = ao;
        clientManager = ClientManager.create(ao);
        dogManager = DogManager.create(ao);
        dogWalkerManager = DogWalkerManager.create(ao);
        requestWalkManager = RequestWalkManager.create(ao);
        logManager = LogManager.create(ao);
    }

    private String findDogWalker(String id) {
        if (!id.equals("No dog walker")) {
            DogWalker dogWalker = dogWalkerManager.getByUniqueId(id);
            return dogWalker.getLastName() + " " + dogWalker.getName();
        } else {
            return "No dog walker";
        }
    }

    private String findFullName(String id) throws Exception{
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

    public void updateIssue(Map<CustomField, Object> map, MutableIssue issue/*RequestWalk requestWalk*/) throws Exception{

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
//        try{
//            IssueService issueService = ComponentAccessor.getIssueService();
//            IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
//
//            RequestWalk updatedRequestWalk = requestWalkManager.getByUniqueId(requestWalk.getUniqueId());
//
//            issueInputParameters.addCustomFieldValue("customfield_10120",
//                    updatedRequestWalk.getRequestWalkStatus().toString());
//            issueInputParameters.addCustomFieldValue("customfield_10122",
//                    findDogWalker(updatedRequestWalk.getDogWalkerId()));
//
//            IssueService.UpdateValidationResult updateValidationResult = ComponentAccessor
//                    .getIssueService().validateUpdate(
//                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
//                            Long.parseLong(updatedRequestWalk.getIssueId()), issueInputParameters);
//
//            if(updateValidationResult.isValid()) {
//                ComponentAccessor.getIssueService().update(ComponentAccessor
//                                .getJiraAuthenticationContext().getLoggedInUser(),
//                        updateValidationResult);
//            }
//
//        }catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
    }

    public void changeIssueStatus(RequestWalk requestWalk) throws Exception{
        try {

            Log logModel = new Log();

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

                logModel.setLog("from change issue status!");
                logManager.save(logModel);
            }
//                logModel.setLog(transitionValidationResult.getErrorCollection().toString());
//                logManager.save(logModel);
//                logModel.setLog(transitionValidationResult.getWarningCollection().toString());
//                logManager.save(logModel);
//
//            } else {
//                logModel.setLog("error from change issue status!");
//                logManager.save(logModel);
//                logModel.setLog(transitionValidationResult.getErrorCollection().toString());
//                logManager.save(logModel);
//                logModel.setLog(transitionValidationResult.getWarningCollection().toString());
//                logManager.save(logModel);
//            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
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

            try {

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
                        requestWalk.getRequestWalkStatus().getDescription());

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
