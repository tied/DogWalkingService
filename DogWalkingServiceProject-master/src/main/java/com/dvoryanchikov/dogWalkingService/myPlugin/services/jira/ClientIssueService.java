package com.dvoryanchikov.dogWalkingService.myPlugin.services.jira;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.johnson.event.Event;
import com.dvoryanchikov.dogWalkingService.myPlugin.controller.DogWalkServiceController;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.ClientManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


public class ClientIssueService {

    private final ActiveObjects ao;
    private final ClientManager clientManager;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy", Locale.ENGLISH);


    public ClientIssueService(ActiveObjects ao){
        this.ao = ao;
        clientManager = ClientManager.create(ao);
    }

    private String findIssue(String clientId) {
        Client client = clientManager.getByUniqueId(clientId);
        return client.getIssueId();
    }

    public void updateIssue(Map<CustomField, Object> map, MutableIssue issue) throws Exception {

        ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        for (Map.Entry<CustomField, Object> entry: map.entrySet()) {
            try {
                issue.setCustomFieldValue(entry.getKey(), entry.getValue());
            } catch (Exception ex) {
                throw new Exception("from update issue method " + ex);
            }
        }

        ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false);



//
//        try {
//            ClientManager manager = ClientManager.create(ao);
//            Client byUniqueId = manager.getByUniqueId(client.getUniqueId());
//            ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
//            MutableIssue issue = ComponentAccessor.getIssueManager().getIssueObject(Long.parseLong(byUniqueId.getIssueId())); // byUniqueId.getIssueId()
//
//            // Initialize custom fields ----------------------------------
//            try {
//
//                CustomField Name = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10108L);
//                CustomField LastName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10109L);
//                CustomField MiddleName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10110L);
//                CustomField BirthDate = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10111L);
//                CustomField PhoneNumber = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10112L);
//                CustomField Email = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10113L);
//                CustomField Address = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10114L);
//
//                // set field values -------------------------------
//                try {
//
//                    try {
//                        String customFieldValueName = (String) issue.getCustomFieldValue(Name);
//                        if (customFieldValueName.equals(client.getName()) )
//                        issue.setCustomFieldValue(Name, client.getName());
//                    } catch (Exception ex) {
//                        throw new Exception("самый первый сэт " +
//                                client.getName() +
//                                "  " + ex +
//                                "  " + Name +
//                                "  " + issue +
//                                "  " + byUniqueId.getIssueId() +
//                                "  " + user +
//                                "  " + client.getUniqueId());
//                    }
//
//                    issue.setCustomFieldValue(LastName, client.getLastName());
//                    issue.setCustomFieldValue(MiddleName, client.getMiddleName());
//
//                    try {
//                        issue.setCustomFieldValue(BirthDate, (new Timestamp(Calendar.getInstance().getTime().getTime()))); //преобразовать в Data dateFormat.format(client.getBirthDate())
//
//                    } catch (Exception ex) {
//                        throw new Exception("привет из даты " + ex);
//                    }
//
//
//                    try {
//                        issue.setCustomFieldValue(PhoneNumber, Double.parseDouble(client.getPhoneNumber()));
//                    } catch (Exception ex) {
//                        throw new Exception("привет из телефона");
//                    }
//
//                    issue.setCustomFieldValue(Email, client.getEmail());
//                    issue.setCustomFieldValue(Address, client.getAddress());
//
//                } catch (Exception ex) {
//                    throw new Exception(ex.getMessage() + "сетим значения");
//                }
//
//
//            } catch (Exception ex) {
//                throw new Exception(ex.getMessage() + "Hello from update method/ 3333333");
//            }
//
//            try {
//                // apply changes to Jira -----------------------
//                ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false);
//
//            } catch (Exception ex) {
//                throw new Exception("Hello from update method/ 4444444 " + ex + "   ");
//            }
//
//
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage() + "Hello from update method");
//        }



//        IssueService issueService = ComponentAccessor.getIssueService();
//        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
//
//        Client updatedClient = clientManager.getByUniqueId(client.getUniqueId());
//
//        issueInputParameters.setSummary(updatedClient.getName() + " " + updatedClient.getLastName());
//
//        issueInputParameters.addCustomFieldValue("customfield_10108", updatedClient.getName());
//        issueInputParameters.addCustomFieldValue("customfield_10109", updatedClient.getLastName());
//        issueInputParameters.addCustomFieldValue("customfield_10110", updatedClient.getMiddleName());
//        issueInputParameters.addCustomFieldValue("customfield_10111", dateFormat.format(updatedClient.getBirthDate()));
//        issueInputParameters.addCustomFieldValue("customfield_10112", updatedClient.getPhoneNumber());
//        issueInputParameters.addCustomFieldValue("customfield_10113", updatedClient.getEmail());
//        issueInputParameters.addCustomFieldValue("customfield_10114", updatedClient.getAddress());
//
//        IssueService.UpdateValidationResult updateValidationResult = issueService
//                .validateUpdate(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
//                        Long.parseLong(updatedClient.getIssueId()),
//                        issueInputParameters);
//
////        IssueService.UpdateValidationResult updateValidationResult = ComponentAccessor
////                .getIssueService().validateUpdate(
////                        ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
////                        Long.parseLong(updatedClient.getIssueId()), issueInputParameters);
//
//        if(updateValidationResult.isValid()) {
//            ComponentAccessor.getIssueService().update(ComponentAccessor
//                    .getJiraAuthenticationContext().getLoggedInUser(),
//                    updateValidationResult);
//            return true;
//        } else {
//            throw new Exception(updateValidationResult.getErrorCollection().toString() +
//                    updateValidationResult.getWarningCollection().toString() +
//                    "//////     " + dateFormat.format(updatedClient.getBirthDate()) +  "       //////");
//        }
    }

    public boolean deleteIssue(String clientId) {
        try {
            IssueService.DeleteValidationResult deleteValidationResult = ComponentAccessor
                    .getIssueService().validateDelete(
                            ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                            Long.parseLong(findIssue(clientId)));

            if (deleteValidationResult.isValid()) {
                ComponentAccessor.getIssueService().delete(
                        ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                        deleteValidationResult);
            }
            return true;

        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }

    public Issue create(Client client) throws Exception{

        IssueService issueService = ComponentAccessor.getIssueService();
        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();

        issueInputParameters.setProjectId(IssueConstants.PROJECT_ID.numId);
        issueInputParameters.setIssueTypeId(IssueConstants.ISSUE_TYPE_CLIENT_ID.id);
        issueInputParameters.setSummary(client.getName() + " " + client.getLastName());

        issueInputParameters.addCustomFieldValue("customfield_10108", client.getName());
        issueInputParameters.addCustomFieldValue("customfield_10109", client.getLastName());
        issueInputParameters.addCustomFieldValue("customfield_10110", client.getMiddleName());
        issueInputParameters.addCustomFieldValue("customfield_10111", dateFormat.format(client.getBirthDate()));
        issueInputParameters.addCustomFieldValue("customfield_10112", client.getPhoneNumber().toString());
        issueInputParameters.addCustomFieldValue("customfield_10113", client.getEmail());
        issueInputParameters.addCustomFieldValue("customfield_10114", client.getAddress());

        IssueService.CreateValidationResult createValidationResult = issueService
                .validateCreate(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                        issueInputParameters);

        if (createValidationResult.isValid()) {
            return issueService.create(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                    createValidationResult).getIssue();
        } else {
            throw new Exception(createValidationResult.getErrorCollection().toString() +
                    createValidationResult.getWarningCollection().toString() +
                    "//////     " + dateFormat.format(client.getBirthDate()) +  "       //////");
        }
    }
}
