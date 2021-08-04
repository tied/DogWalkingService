package com.sorokin.dogWalkingService.myPlugin.entities;

import net.java.ao.Entity;

public interface IHistory extends Entity {

     String getOwnerUniqueId();

     void setOwnerUniqueId(String ownerUniqueId);

     String getDogUniqueId();

     void setDogUniqueId(String dogUniqueId);

     String getWalkerUniqueId();

     void setWalkerUniqueId(String walkerUniqueId);

}
