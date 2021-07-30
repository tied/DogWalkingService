package com.dvoryanchikov.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IDog;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.ILog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Dog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Log;

public class LogManager {

    private final ActiveObjects ao;

    private LogManager(ActiveObjects ao) {this.ao = ao;}

    public static LogManager create (ActiveObjects ao) {
        return new LogManager(ao);
    }

    public boolean save(Log model) {
        try {
            ILog entity = ao.create(ILog.class);
            model.toEntity(entity);
            entity.save();

            return true;
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }


}
