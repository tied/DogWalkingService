package com.sorokin.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.entities.IRequestWalk;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;
import net.java.ao.Query;

public class RequestWalkManager {

    private final ActiveObjects ao;

    private RequestWalkManager (ActiveObjects ao) {this.ao = ao;};

    public static RequestWalkManager create (ActiveObjects ao) {
        return new RequestWalkManager(ao);
    }

    public void save(RequestWalk model) {
//        try {
            IRequestWalk entity = ao.create(IRequestWalk.class);
            model.toEntity(entity);
            entity.save();
//            return true;
//        } catch (Exception ex) {
//            String exs = ex.getMessage();
//        }
//        return false;
    }

    public void deleteByUniqueId(String uniqueId) throws Exception{
        try {
//            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            Query query = Query.select().where("UNIQUE_ID = ?",uniqueId);
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                ao.delete(entities);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RequestWalk[] getAll(){
//        try{
            Query query = Query.select();
            IRequestWalk[] entities = ao.find(IRequestWalk.class,query);

            if(entities != null && entities.length > 0){
                RequestWalk [] requestWalks = new RequestWalk[entities.length];

                for (int i = 0; i < entities.length; i++) {
                    requestWalks[i] = RequestWalk.fromEntity(entities[i]);
                }
                return requestWalks;
            } else {
                return null;
            }
//        }catch (Exception ex){
//            String exs = ex.getMessage();
//        }

    }

    public RequestWalk getByUniqueId(String uniqueId) {
//        try {
//            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            Query query = Query.select().where("UNIQUE_ID = ?",uniqueId);
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                return RequestWalk.fromEntity(entities[0]);
            } else {
                return null;
            }
//        } catch (Exception ex) {
//            String exs = ex.getMessage();
//        }

    }



    public RequestWalk getByIssueId (String issueId) {
//        try {
//            Query query = Query.select().where("ISSUE_ID = '" + issueId + "'");
            Query query = Query.select().where("ISSUE_ID = ?",issueId);
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                return RequestWalk.fromEntity(entities[0]);
            } else {
                return null;
            }
//        } catch (Exception ex) {
//            String exs = ex.getMessage();
//        }
    }



    public IRequestWalk getEntityByUniqueId(String uniqueId) {
//        try {
//            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            Query query = Query.select().where("UNIQUE_ID = ?",uniqueId);
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                return entities[0];
            } else {
                return null;
            }
//        } catch (Exception ex) {
//            String exs = ex.getMessage();
//        }

    }

    public void fullUpdate (RequestWalk model) throws Exception{

        try {
            if (model != null) {
                IRequestWalk entity = getEntityByUniqueId(model.getUniqueId());
                RequestWalk requestWalk = getByUniqueId(model.getUniqueId());
                model.setIssueId(requestWalk.getIssueId());

                if (entity != null) {
                    model.toEntity(entity);
                    entity.save();
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

    public void update(RequestWalk model) throws Exception{
        try {
            if (model != null) {
                IRequestWalk entity = getEntityByUniqueId(model.getUniqueId());

                if (entity != null) {
                    RequestWalk requestWalk = RequestWalk.fromEntity(entity);

                    if(requestWalk.getDogWalkerId().equals("No dog walker")){
                        requestWalk.setDogWalkerId(model.getDogWalkerId()); // доавили Id выгульщика
                    }
                    requestWalk.setRequestWalkStatus(model.getRequestWalkStatus()); // поменяли статус

                    requestWalk.toEntity(entity);
                    entity.save();
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
