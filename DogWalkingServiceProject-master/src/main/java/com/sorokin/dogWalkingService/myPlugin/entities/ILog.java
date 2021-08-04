package com.sorokin.dogWalkingService.myPlugin.entities;

import net.java.ao.Entity;

public interface ILog extends Entity {

//    @StringLength(value=StringLength.UNLIMITED)
    String getLog();

//    @StringLength(value=StringLength.UNLIMITED)
    void setLog(String log);

}
