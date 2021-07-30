package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IDog;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.ILog;
import net.java.ao.schema.StringLength;
import org.codehaus.jackson.annotate.JsonProperty;

public class Log {

    @JsonProperty("log")
    private String log;

    public String getLog() {
        return log;
    }


    public void setLog(String log) {
        this.log = log;
    }

    public void toEntity(ILog entity) {
        entity.setLog(this.getLog());
    }

    public static Log fromEntity(ILog entity) {
        Log log = new Log();
        log.setLog(entity.getLog());
        return log;
    }

}
