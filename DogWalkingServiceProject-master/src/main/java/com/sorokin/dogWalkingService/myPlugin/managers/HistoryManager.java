package com.sorokin.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.entities.IRequestWalk;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;
import net.java.ao.Query;

import java.util.ArrayList;

public class HistoryManager {
    private final ActiveObjects ao;

    public HistoryManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public ArrayList<RequestWalk> getHistory(String owner, String dog, String walker) {

        Query query = Query.select();
        boolean flag = false;
        String str = "";
        if (!owner.equals("default")) {
            str = str.concat("CLIENT_ID = '" + owner + "'");
            flag = true;
        }
        if (!dog.equals("default")) {
            if (flag) {
                str = str.concat(" AND ");
                flag = false;
            }
            str = str.concat("PET_ID = '" + dog + "'");
            flag = true;
        }
        if (!walker.equals("default")) {
            if (flag) {
                str = str.concat(" AND ");
            }
            str = str.concat("DOG_WALKER_ID = '" + walker + "'");
        }
        if (!str.equals("")) {
            query.where(str);
        }

        IRequestWalk[] entities = ao.find(IRequestWalk.class,query);

        if (entities != null && entities.length > 0) {
            ArrayList<RequestWalk> history = new ArrayList<>();
            for (IRequestWalk entity : entities) {
                history.add(RequestWalk.fromEntity(entity));
            }
            return history;
        }

        return null;

    }
}
