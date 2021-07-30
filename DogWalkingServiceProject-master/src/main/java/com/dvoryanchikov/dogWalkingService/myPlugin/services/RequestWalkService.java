package com.dvoryanchikov.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.RequestWalkManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Dog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.RequestWalk;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.RequestWalkStatus;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.RequestWalkIssueService;

import java.sql.Timestamp;

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
        RequestWalk RequestWalkByIssueId = requestWalkManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        RequestWalk model = new RequestWalk();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object RequestPlace = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10116L));
        Object TimeWalk = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10117L));
        Object PetId = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10118L));
        Object WalkDuration = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10200L));
        Object RequestWalkStatusNew = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10120L));
        Object ClientId = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10121L));
        Object DogWalkerId = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10122L));

        model.setRequestPlace((String) RequestPlace);
        model.setTimeWalk(TimeWalk.toString());
        model.setPetId((String) PetId);
        model.setWalkDuration((Double) WalkDuration);


        switch (RequestWalkStatusNew.toString()) {
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

//        model.setRequestWalkStatus((com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.RequestWalkStatus) RequestWalkStatus);
        model.setClientId((String) ClientId);
        model.setDogWalkerId((String) DogWalkerId);

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setUniqueId(RequestWalkByIssueId.getUniqueId());
        model.setIssueId(RequestWalkByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        requestWalkManager.fullUpdate(model);

    }

    public Boolean updateRequestWalk(RequestWalk model){
        requestWalkManager.update(model);
        requestWalkIssueService.updateIssue(model);
        return requestWalkIssueService.changeIssueStatus(model);
    }
}
