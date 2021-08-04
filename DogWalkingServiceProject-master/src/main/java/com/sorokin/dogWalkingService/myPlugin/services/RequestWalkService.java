package com.sorokin.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.sorokin.dogWalkingService.myPlugin.constants.CustomFieldConstants;
import com.sorokin.dogWalkingService.myPlugin.managers.RequestWalkManager;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;
import com.sorokin.dogWalkingService.myPlugin.models.enums.RequestWalkStatus;
import com.sorokin.dogWalkingService.myPlugin.services.jira.RequestWalkIssueService;
import com.sorokin.dogWalkingService.myPlugin.utils.CustomFieldUtils;

public class RequestWalkService {
    private final ActiveObjects ao;
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

    public void createRequestWalk(RequestWalk model) throws Exception {
        model.setIssueId(requestWalkIssueService.create(model).getId().toString());
        requestWalkManager.save(model);
    }

    public void deleteRequestWalkFromListener (Issue issue) throws Exception {
        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        RequestWalk requestWalkByIssueId = requestWalkManager.getByIssueId(issue.getId().toString());

        requestWalkManager.deleteByUniqueId(requestWalkByIssueId.getUniqueId());

    }

    public void deleteRequestWalkByUniqueId(String uniqueId) throws Exception {
        requestWalkManager.deleteByUniqueId(uniqueId);
    }

    public void UpdateRequestWalkFromListener (Issue issue) throws Exception {

        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        RequestWalk requestWalkByIssueId = requestWalkManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        RequestWalk model = new RequestWalk();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object timeWalk = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.REQUEST_WALK_TIME_WALK_CF_ID));
        Object walkDuration = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.REQUEST_WALK_WALK_DURATION_CF_ID));
        Object requestWalkStatusNew = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.REQUEST_WALK_STATUS_CF_ID));
        String requestPlace = CustomFieldUtils.
                getCustomFieldString(issue, CustomFieldConstants.REQUEST_WALK_REQUEST_PLACE_CF_ID);

        model.setRequestPlace(requestPlace);

        model.setTimeWalk(timeWalk.toString());
        model.setWalkDuration((Double) walkDuration);


        switch (requestWalkStatusNew.toString()) {
            case "SEARCHING_WALKER":
                model.setRequestWalkStatus(RequestWalkStatus.SEARCHING_WALKER);
                break;
            case "WALKING":
                model.setRequestWalkStatus(RequestWalkStatus.WALKING);
                break;
            case "COMPLETED":
                model.setRequestWalkStatus(RequestWalkStatus.COMPLETED);
                break;
        }

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        // (потому что на стороне jira не хранятся id-шники)
        model.setDogWalkerId(requestWalkByIssueId.getDogWalkerId());
        model.setPetId(requestWalkByIssueId.getPetId());
        model.setClientId(requestWalkByIssueId.getClientId());
        model.setUniqueId(requestWalkByIssueId.getUniqueId());
        model.setIssueId(requestWalkByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        requestWalkManager.fullUpdate(model);

    }

    public void updateRequestWalk(RequestWalk model){
        requestWalkManager.update(model);
        requestWalkIssueService.updateIssue(model);
        requestWalkIssueService.changeIssueStatus(model);
    }
}
