package com.dvoryanchikov.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.user.ApplicationUser;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.ClientManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.common.StatusResponse;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.ClientIssueService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientService {
    private ActiveObjects ao;
    private final ClientManager clientManager;
    private final ClientIssueService clientIssueService;


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy", Locale.ENGLISH);


    private ClientService(ActiveObjects ao) {
        this.ao = ao;
        clientManager = ClientManager.create(ao);
        clientIssueService = new ClientIssueService(ao);
    }

    public static ClientService create(ActiveObjects ao) {
        return new ClientService(ao);
    }

    public Client getClientByUniqueId(String uniqueId) {
        return clientManager.getByUniqueId(uniqueId);
    }

    public Client[] getAllClients() {
        return clientManager.getAll();
    }



    public boolean createClient(Client model) throws Exception{
        Issue issue = clientIssueService.create(model);
        model.setIssueId(issue.getId().toString());
        return clientManager.save(model);
    }

    public Boolean deleteClientByUniqueId(String uniqueId) {
        return clientIssueService.deleteIssue(uniqueId) && clientManager.deleteByUniqueId(uniqueId);
    }



    public void updateClient(Client model) throws Exception {
        // проверку на изменения полей сделать здесь. если не изменились, то ничего не делать.
        // Если изменилось, то собрать список изменённых полей, передать их дальше в updateIssue
        // и обновить только их
        // в базу обновить всё (всю модельку) в таком случае

        ClientManager manager = ClientManager.create(ao);
        Client clientByUniqueId = manager.getByUniqueId(model.getUniqueId()); // получили клиента из базы по uniqueId
        MutableIssue issue = ComponentAccessor.getIssueManager()
                .getIssueObject(Long.parseLong(clientByUniqueId.getIssueId())); // получили id нужного тикета

        // столбцы в бд
        CustomField Name = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10108L);
        CustomField LastName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10109L);
        CustomField MiddleName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10110L);
        CustomField BirthDate = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10111L);
        CustomField PhoneNumber = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10112L);
        CustomField Email = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10113L);
        CustomField Address = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10114L);

        // старое значение из столбца в бд
        // была проблема в том, что мы считывали значения из бд, а типы знычений не все были стринг.
        // надо было явно преобразовать
        String customFieldValueName = (String) issue.getCustomFieldValue(Name);
        String customFieldValueLastName = (String) issue.getCustomFieldValue(LastName);
        String customFieldValueMiddleName = (String) issue.getCustomFieldValue(MiddleName);

        String customFieldValueBirthDate = issue.getCustomFieldValue(BirthDate).toString();

        String customFieldValuePhoneNumber = issue.getCustomFieldValue(PhoneNumber).toString();
        String customFieldValueEmail = (String) issue.getCustomFieldValue(Email);
        String customFieldValueAddress = (String) issue.getCustomFieldValue(Address);

        ArrayList<CustomField> listCustomFields = new ArrayList<>();
        listCustomFields.add(Name);
        listCustomFields.add(LastName);
        listCustomFields.add(MiddleName);
        listCustomFields.add(BirthDate);
        listCustomFields.add(PhoneNumber);
        listCustomFields.add(Email);
        listCustomFields.add(Address);

        ArrayList<String> listOldValue = new ArrayList<>();
        listOldValue.add(customFieldValueName);
        listOldValue.add(customFieldValueLastName);
        listOldValue.add(customFieldValueMiddleName);
        listOldValue.add(customFieldValueBirthDate);
        listOldValue.add(customFieldValuePhoneNumber);
        listOldValue.add(customFieldValueEmail);
        listOldValue.add(customFieldValueAddress);

        ArrayList<Object> listNewValue = new ArrayList<>();
        listNewValue.add(model.getName());
        listNewValue.add(model.getLastName());
        listNewValue.add(model.getMiddleName());
        try {
            listNewValue.add(model.getBirthDate());
        } catch (Exception ex) {
            throw new Exception("from updateClient method " + ex);
        }
        listNewValue.add(model.getPhoneNumber());
        listNewValue.add(model.getEmail());
        listNewValue.add(model.getAddress());

        Map<CustomField, Object> map = new HashMap<>();
        for (int i = 0; i < listOldValue.size(); i++) {
            if (!(listOldValue.get(i).equals(listNewValue.get(i).toString()))) {
                map.put(listCustomFields.get(i), listNewValue.get(i));
            }
        }

        if (!map.isEmpty()) {
            clientIssueService.updateIssue(map, issue);
            clientManager.update(model);
        } else {
            throw new Exception("пустая мапа");
        }
    }
}
