package com.dvoryanchikov.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.HistoryManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.RequestWalkManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.History;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.RequestWalk;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.RequestWalkIssueService;

import java.util.ArrayList;

public class HistoryService {
    private ActiveObjects ao;
    private final HistoryManager historyManager;
//    private final HistoryIssueService historyIssueService; не нужен, так как нужны лишь запросы в базу
//    и никаких изменений

    private HistoryService(ActiveObjects ao){
        this.ao = ao;
        this.historyManager = new HistoryManager(ao);
//        this.historyIssueService = new HistoryIssueService(ao);
    }

    public static HistoryService create(ActiveObjects ao){
        return new HistoryService(ao);
    }

    public ArrayList<RequestWalk> getHistoryWalks(String owner, String dog, String walker) {
        return historyManager.getHistory(owner, dog, walker);
    }

}
