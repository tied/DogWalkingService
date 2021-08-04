package com.sorokin.dogWalkingService.myPlugin.services.jira;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.user.ApplicationUser;
import com.sorokin.dogWalkingService.myPlugin.managers.ClientManager;
import com.sorokin.dogWalkingService.myPlugin.models.Client;

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

    }

    public void deleteIssue(String clientId) throws Exception{
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


        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

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
