package com.dvoryanchikov.dogWalkingService.myPlugin.controller.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.History;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.RequestWalk;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.common.StatusResponse;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.HistoryService;

import java.util.ArrayList;

public class HistoryImpl {
    private ActiveObjects ao;
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
