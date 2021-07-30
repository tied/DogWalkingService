package com.dvoryanchikov.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IClient;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IDog;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IRequestWalk;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Dog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.RequestWalk;
import net.java.ao.Query;

public class RequestWalkManager {
    private ActiveObjects ao;

    public RequestWalkManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public Boolean save(RequestWalk model) {
        try {
            IRequestWalk entity = ao.create(IRequestWalk.class);
            model.toEntity(entity);
            entity.save();
            return true;
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }

    public void deleteByUniqueId(String uniqueId) throws Exception{
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                ao.delete(entities);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RequestWalk[] getAll(){
        try{
            Query query = Query.select();
            IRequestWalk[] entities = ao.find(IRequestWalk.class,query);

            if(entities != null && entities.length > 0){
                RequestWalk [] requestWalks = new RequestWalk[entities.length];

                for (int i = 0; i < entities.length; i++) {
                    requestWalks[i] = RequestWalk.fromEntity(entities[i]);
                }
                return requestWalks;
            }
        }catch (Exception ex){
            String exs = ex.getMessage();
        }
        return null;
    }

    public RequestWalk getByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                return RequestWalk.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }



    public RequestWalk getByIssueId (String issueId) {

        try {
            Query query = Query.select().where("ISSUE_ID = '" + issueId + "'");
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                return RequestWalk.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;

    }



    public IRequestWalk getEntityByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IRequestWalk[] entities = ao.find(IRequestWalk.class, query);
            if (entities != null && entities.length > 0) {
                return entities[0];
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    public void fullUpdate (RequestWalk model) {

        try {
            if (model != null) {
                IRequestWalk entity = getEntityByUniqueId(model.getUniqueId());
                RequestWalk requestWalk = getByUniqueId(model.getUniqueId());
                model.setIssueId(requestWalk.getIssueId());

                if (entity != null) {
                    model.toEntity(entity);
                    entity.save();
//                    return true;
                }
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }

    }

    public boolean update(RequestWalk model) {
        try {
            if (model != null) {
                IRequestWalk entity = getEntityByUniqueId(model.getUniqueId());

                if (entity != null) {
                    RequestWalk requestWalk = RequestWalk.fromEntity(entity);

                    if(requestWalk.getDogWalkerId().equals("No dog walker")){
                        requestWalk.setDogWalkerId(model.getDogWalkerId());
                    }
                    requestWalk.setRequestWalkStatus(model.getRequestWalkStatus());

                    requestWalk.toEntity(entity);
                    entity.save();
                    return true;
                }
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }
}
