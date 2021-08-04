package com.sorokin.dogWalkingService.myPlugin.controller.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;
import com.sorokin.dogWalkingService.myPlugin.services.HistoryService;

import java.util.ArrayList;

public class HistoryImpl {
    private final ActiveObjects ao;
    private HistoryService historyService;

    private HistoryImpl(ActiveObjects ao){
        this.ao = ao;
        this.historyService = historyService.create(ao);
    }

    public static HistoryImpl create(ActiveObjects ao){
        return new HistoryImpl(ao);
    }

    public ArrayList<RequestWalk> getHistoryWalks (String owner, String dog, String walker) {

            return historyService.getHistoryWalks(owner, dog, walker);

    }
}
