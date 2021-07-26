package com.dvoryanchikov.dogWalkingService.myPlugin.controller.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.common.StatusResponse;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.ClientService;

public class ClientImpl {
    private ActiveObjects ao;
    private ClientService clientService;

    private ClientImpl(ActiveObjects ao) {
        this.ao = ao;
        this.clientService = clientService.create(ao);
    }

    public static ClientImpl create(ActiveObjects ao) {
        return new ClientImpl(ao);
    }

    public Client getClientByUniqueId(String uniqueId) {
        return clientService.getClientByUniqueId(uniqueId);
    }

    public Client[] getAllClients() {
        return clientService.getAllClients();
    }

    public StatusResponse createClient(Client model) {
        try {
            clientService.createClient(model);
            return StatusResponse.createSuccess("Client with name: " + model.getName() + " " + model.getLastName());

        } catch (Exception ex) {
            return StatusResponse.createFail(ex.getMessage());
        }

    }

    public StatusResponse deleteClient(String uniqueId) {
        try{
            clientService.deleteClientByUniqueId(uniqueId);
            return StatusResponse.deleteSuccess("Client: " + uniqueId);

        }catch (Exception ex){
            return StatusResponse.deleteFail(ex.getMessage());
        }

    }

    public StatusResponse updateClient(Client model) {
        try{
            clientService.updateClient(model);
            return StatusResponse.updateSuccess("Client: " + model.getUniqueId());

        }catch (Exception ex){
            return StatusResponse.updateFail(ex.getMessage() + "hi in in in");
        }
    }
}
