package com.sorokin.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.managers.HistoryManager;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;

import java.util.ArrayList;

public class HistoryService {
    private final ActiveObjects ao;
    private final HistoryManager historyManager;

    private HistoryService(ActiveObjects ao){
        this.ao = ao;
        this.historyManager = new HistoryManager(ao);
    }

    public static HistoryService create(ActiveObjects ao){
        return new HistoryService(ao);
    }

    public ArrayList<RequestWalk> getHistoryWalks(String owner, String dog, String walker) {
        return historyManager.getHistory(owner, dog, walker);
    }

}
