package com.dvoryanchikov.dogWalkingService.myPlugin.listener;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.LogManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Log;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.ClientService;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.DogService;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.DogWalkerService;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.RequestWalkService;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.IssueConstants;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


public class Listener implements InitializingBean, DisposableBean {

    private final EventPublisher eventPublisher;
    private final ActiveObjects ao;

    private final ClientService clientService;
    private final DogService dogService;
    private final DogWalkerService dogWalkerService;
    private final RequestWalkService requestWalkService;

    private final LogManager logManager;


    public Listener(ActiveObjects ao, EventPublisher eventPublisher) {
        this.ao = ao;
        this.eventPublisher = eventPublisher;

        clientService = ClientService.create(ao);
        dogService = DogService.create(ao);
        dogWalkerService = DogWalkerService.create(ao);
        requestWalkService = RequestWalkService.create(ao);

        logManager = LogManager.create(ao);

    }

    @Override
    public void destroy() {
        eventPublisher.unregister(this);
    }

    @Override
    public void afterPropertiesSet() {
        eventPublisher.register(this);
    }

    @EventListener
    public void onIssueEvent(IssueEvent issueEvent){
        Long eventTypeId = issueEvent.getEventTypeId();
        Issue issue = issueEvent.getIssue();

        Log logModel = new Log();

        if (eventTypeId.equals(EventType.ISSUE_UPDATED_ID)) {

            if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_CLIENT_ID.getId())) {

                try {
                    clientService.UpdateClientFromListener(issue);
                    logModel.setLog("the client was updated by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from update ClientService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            } else if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_DOG_ID.getId())) {

                try {
                    dogService.UpdateDogFromListener(issue);
                    logModel.setLog("the dog was updated by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from update dogService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            } else if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_DOG_WALKER_ID.getId())) {

                try {
                    dogWalkerService.UpdateDogWalkerFromListener(issue);
                    logModel.setLog("the walker was updated by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from update dogWalkerService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            } else if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_REQUEST_WALK_ID.getId())) {

                try {
                    requestWalkService.UpdateRequestWalkFromListener(issue);
                    logModel.setLog("the request was updated by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from update requestWalkService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            }

        } else if (eventTypeId.equals(EventType.ISSUE_DELETED_ID)) {

            if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_CLIENT_ID.getId())) {

                try {
                    clientService.deleteClientFromListener(issue);
                    logModel.setLog("the client was deleted by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from delete clientService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            } else if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_DOG_ID.getId())) {

                try {
                    dogService.deleteDogFromListener(issue);
                    logModel.setLog("the dog was deleted by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from delete dogService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            } else if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_DOG_WALKER_ID.getId())) {

                try {
                    dogWalkerService.deleteDogWalkerFromListener(issue);
                    logModel.setLog("the walker was deleted by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from delete dogWalkerService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }



            } else if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_REQUEST_WALK_ID.getId())) {

                try {
                    requestWalkService.deleteRequestWalkFromListener(issue);
                    logModel.setLog("the request was deleted by jira!");
                    logManager.save(logModel);
                } catch (Exception ex) {
                    logModel.setLog("error from delete dogWalkerService!    =    " + ex.getMessage());
                    logManager.save(logModel);
                }

            }

        } else if (eventTypeId.equals(EventType.ISSUE_CREATED_ID)){

            if (issue.getIssueTypeId().equals(IssueConstants.ISSUE_TYPE_CLIENT_ID.getId())) {
                logModel.setLog("client A!");
                logManager.save(logModel);
            } else {
                logModel.setLog("client B!");
                logManager.save(logModel);
            }
        } else {
            logModel.setLog("create C!");
            logManager.save(logModel);
        }

    }

}
