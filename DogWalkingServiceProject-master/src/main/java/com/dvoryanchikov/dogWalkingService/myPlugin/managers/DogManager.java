package com.dvoryanchikov.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IDog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Dog;
import net.java.ao.Query;

public class DogManager {
    private ActiveObjects ao;

    private DogManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public static DogManager create (ActiveObjects ao) {
        return new DogManager(ao);
    }

    public boolean save(Dog model) {
        try {
            IDog entity = ao.create(IDog.class);
            model.toEntity(entity);
            entity.save();

            return true;
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }

    public boolean deleteByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IDog[] entities = ao.find(IDog.class, query);

            if (entities != null && entities.length > 0) {
                ao.delete(entities);
            }

        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return false;
    }

    public Dog[] getByOwnerId(String ownerId){
        try{
            Query query = Query.select().where("OWNER_ID = '" + ownerId + "'");
            IDog[] entities = ao.find(IDog.class,query);

            if(entities != null && entities.length>0){
                Dog [] dogs = new Dog[entities.length];

                for (int i = 0; i < entities.length; i++) {
                    dogs[i] = Dog.fromEntity(entities[i]);
                }
                return dogs;
            }
        }catch (Exception ex){
            String exs = ex.getMessage();
        }
        return null;
    }

    public Dog getByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IDog[] entities = ao.find(IDog.class, query);

            if (entities != null && entities.length > 0) {
                return Dog.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    public Dog[] getAll() {
        try {
            Query query = Query.select();
            IDog[] entities = ao.find(IDog.class, query);

            if (entities != null && entities.length > 0) {
                Dog[] dogs = new Dog[entities.length];

                for (int i = 0; i < entities.length; i++) {

                    dogs[i] = Dog.fromEntity(entities[i]);
                }
                return dogs;
            }

        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    public IDog getEntityByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IDog[] entities = ao.find(IDog.class, query);

            if (entities != null && entities.length > 0) {
                return entities[0];
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    //Update using all field of getting model
    public void fullUpdate(Dog model){
        try {
            if (model != null) {
                IDog entity = getEntityByUniqueId(model.getUniqueId());
                Dog dog = getByUniqueId(model.getUniqueId());
                model.setIssueId(dog.getIssueId());

                if (entity != null) {
                    model.toEntity(entity);
                    entity.save();
//                    return true;
                }
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
//        return false;
    }

    public boolean update(Dog model) {
        try {
            if (model != null) {
                IDog entity = getEntityByUniqueId(model.getUniqueId());

                if (entity != null) {
                    Dog dog = Dog.fromEntity(entity);

                    dog.setDogStatus(model.getDogStatus());

                    dog.toEntity(entity);
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
