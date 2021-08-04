package com.sorokin.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.entities.IDogWalker;
import com.sorokin.dogWalkingService.myPlugin.models.DogWalker;
import net.java.ao.Query;

public class DogWalkerManager {
    private ActiveObjects ao;

    private DogWalkerManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public static DogWalkerManager create(ActiveObjects ao) {
        return new DogWalkerManager(ao);
    }

    public boolean save(DogWalker model) {
        try {
            IDogWalker entity = ao.create(IDogWalker.class);
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
            IDogWalker[] entities = ao.find(IDogWalker.class, query);
            if (entities != null && entities.length > 0) {
                ao.delete(entities);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

    public DogWalker getByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IDogWalker[] entities = ao.find(IDogWalker.class, query);

            if (entities != null && entities.length > 0) {

                return DogWalker.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }


    public DogWalker getByIssueId (String issueId) {

        try {
            Query query = Query.select().where("ISSUE_ID = '" + issueId + "'");
            IDogWalker[] entities = ao.find(IDogWalker.class, query);
            if (entities != null && entities.length > 0) {
                return DogWalker.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;

    }



    public DogWalker[] getAll(){
        try{
            Query query = Query.select();
            IDogWalker[] entities = ao.find(IDogWalker.class,query);

            if(entities != null && entities.length>0){
                DogWalker[] dogWalkers = new DogWalker[entities.length];

                for (int i = 0; i < entities.length; i++) {

                    dogWalkers[i] = DogWalker.fromEntity(entities[i]);
                }
                return dogWalkers;
            }
        }catch (Exception ex){
            String exs = ex.getMessage();
        }
        return null;
    }

    public IDogWalker getEntityByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IDogWalker[] entities = ao.find(IDogWalker.class, query);
            if (entities != null && entities.length > 0) {
                return entities[0];
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    public boolean allUpdate(DogWalker model) {
        try {
            if (model != null) {
                IDogWalker entity = getEntityByUniqueId(model.getUniqueId());
                DogWalker dogWalker = getByUniqueId(model.getUniqueId());
                model.setIssueId(dogWalker.getIssueId());

                if (entity != null) {
                    model.toEntity(entity);
                    entity.save();
                    return true;
                }
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }

    public boolean update(DogWalker model) {
        try {
            if (model != null) {
                IDogWalker entity = getEntityByUniqueId(model.getUniqueId());

                if (entity != null) {
                    DogWalker dogWalker = DogWalker.fromEntity(entity);

                    dogWalker.setDogWalkerStatus(model.getDogWalkerStatus());

                    dogWalker.toEntity(entity);
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
