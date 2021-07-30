package com.dvoryanchikov.dogWalkingService.myPlugin.managers;

import com.atlassian.activeobjects.external.ActiveObjects;

import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IClient;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.common.StatusResponse;
import net.java.ao.Query;

import java.util.Arrays;

public class ClientManager {

    private ActiveObjects ao;

    private ClientManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public static ClientManager create(ActiveObjects ao) {
        return new ClientManager(ao);
    }

    public void save(Client model) throws Exception{
        try {
            IClient entity = ao.create(IClient.class);
            model.toEntity(entity);
            entity.save();

//            return true;

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
//            String exs = ex.getMessage();
        }
//        return false;
    }

    public void deleteByUniqueId(String uniqueId) throws Exception{
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IClient[] entities = ao.find(IClient.class, query);
            if (entities != null && entities.length > 0) {
                ao.delete(entities[0]);
            }

        } catch (Exception ex) {

            String exs = ex.getMessage();
            throw new Exception(ex.getMessage());
        }

    }

    public Client getByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IClient[] entities = ao.find(IClient.class, query);
            if (entities != null && entities.length > 0) {
                return Client.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }


    public Client getByIssueId (String issueId) {

        try {
            Query query = Query.select().where("ISSUE_ID = '" + issueId + "'");
            IClient[] entities = ao.find(IClient.class, query);
            if (entities != null && entities.length > 0) {
                return Client.fromEntity(entities[0]);
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;

    }



    public Client[] getAll() {
        try {
            Query query = Query.select();
            IClient[] entities = ao.find(IClient.class, query);

            if (entities != null && entities.length > 0) {

                Client[] clients = new Client[entities.length];

                for (int i = 0; i < entities.length; i++) {

                    clients[i] = Client.fromEntity(entities[i]);
                }
                return clients;
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    public IClient getEntityByUniqueId(String uniqueId) {
        try {
            Query query = Query.select().where("UNIQUE_ID = '" + uniqueId + "'");
            IClient[] entities = ao.find(IClient.class, query);
            if (entities != null && entities.length > 0) {
                return entities[0];
            }
        } catch (Exception ex) {
            String exs = ex.getMessage();
        }
        return null;
    }

    public void update(Client model) throws Exception{

//        if (model != null) {
//            IClient entity = getEntityByUniqueId(model.getUniqueId());
//            Client client = getByUniqueId(model.getUniqueId());
//            model.setIssueId(client.getIssueId());
//
//            if (entity != null) {
//                model.toEntity(entity);
//                entity.save();
////                    return true;
//            }
//        } else {
//            throw new Exception("error in manager");
//        }

        try {
            if (model != null) {
                IClient entity = getEntityByUniqueId(model.getUniqueId());
                Client client = getByUniqueId(model.getUniqueId());
                model.setIssueId(client.getIssueId());

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

}
