package com.dvoryanchikov.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.RequestWalkManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.RequestWalk;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.RequestWalkIssueService;

public class RequestWalkService {
    private ActiveObjects ao;
    private final RequestWalkManager requestWalkManager;
    private final RequestWalkIssueService requestWalkIssueService;

    private RequestWalkService(ActiveObjects ao){
        this.ao = ao;
        this.requestWalkManager = new RequestWalkManager(ao);
        this.requestWalkIssueService = new RequestWalkIssueService(ao);
    }

    public static RequestWalkService create(ActiveObjects ao){
        return new RequestWalkService(ao);
    }

    public RequestWalk[] getAllRequestWalks(){
        return requestWalkManager.getAll();
    }

    public RequestWalk getRequestWalkByUniqueId(String uniqueId){
        return requestWalkManager.getByUniqueId(uniqueId);
    }

    public boolean createRequestWalk(RequestWalk model) throws Exception {
        model.setIssueId(requestWalkIssueService.create(model).getId().toString());
        return requestWalkManager.save(model);
    }

    public boolean deleteRequestWalkByUniqueId(String uniqueId){
        return requestWalkManager.deleteByUniqueId(uniqueId);
    }

    public Boolean updateRequestWalk(RequestWalk model){
        requestWalkManager.update(model);
        requestWalkIssueService.updateIssue(model);
        return requestWalkIssueService.changeIssueStatus(model);
    }
}
