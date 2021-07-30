package com.dvoryanchikov.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.dvoryanchikov.dogWalkingService.myPlugin.constants.CustomFieldConstants;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.ClientManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.DogManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.ClientIssueService;
import com.dvoryanchikov.dogWalkingService.myPlugin.utils.CustomFieldUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientService {
    private ActiveObjects ao;
    private final ClientManager clientManager;
    private final ClientIssueService clientIssueService;

    private final DogService dogService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy", Locale.ENGLISH);


    private ClientService(ActiveObjects ao) {
        this.ao = ao;
        clientManager = ClientManager.create(ao);
        clientIssueService = new ClientIssueService(ao);
        dogService = DogService.create(ao);
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



    public void createClient(Client model) throws Exception{
        Issue issue = clientIssueService.create(model);
        model.setIssueId(issue.getId().toString());
        clientManager.save(model);
    }

    public void deleteClientFromListener (Issue issue) throws Exception{
        // получили из базы модель клиента, которая соответствует удаляемому issue с jira
        Client clientByIssueId = clientManager.getByIssueId(issue.getId().toString());

        String uniqueIdClient = clientByIssueId.getUniqueId();

        clientManager.deleteByUniqueId(uniqueIdClient);

        try {
            deletePetsByOwnerId(uniqueIdClient);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }


    }

    public void deleteClientByUniqueId(String uniqueId) throws Exception{
        clientIssueService.deleteIssue(uniqueId); // удалям из issue
        clientManager.deleteByUniqueId(uniqueId); // удаляем из бд
        deletePetsByOwnerId(uniqueId);
    }

    public void deletePetsByOwnerId (String ownerId) throws Exception {
        try {
            dogService.deleteAllPetsByOwnerId(ownerId);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }



    public void UpdateClientFromListener (Issue issue) throws Exception {

        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        Client ClientByIssueId = clientManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        Client model = new Client();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

//        Object Name = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10108L));
        String name = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_NAME_CF_ID);
        Object LastName = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10109L));
        Object MiddleName = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10110L));
        Object BirthDate = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10111L));
        Object PhoneNumber = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10112L));
        Object Email = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10113L));
        Object Address = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10114L));


        model.setName(name);
        model.setLastName((String) LastName);
        model.setMiddleName((String) MiddleName);
        model.setBirthDate((Timestamp) BirthDate);
        model.setPhoneNumber((Double) PhoneNumber);
        model.setEmail((String) Email);
        model.setAddress((String) Address);

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setUniqueId(ClientByIssueId.getUniqueId());
        model.setIssueId(ClientByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        clientManager.update(model);

    }




    public void updateClient(Client model/*, Issue issue*/) throws Exception {
        // проверку на изменения полей сделать здесь. если не изменились, то ничего не делать.
        // Если изменилось, то собрать список изменённых полей, передать их дальше в updateIssue
        // и обновить только их
        // в базу обновить всё (всю модельку) в таком случае

        ClientManager manager = ClientManager.create(ao);
        Client clientByUniqueId = manager.getByUniqueId(model.getUniqueId()); // получили клиента из базы по uniqueId
        MutableIssue issue = ComponentAccessor.getIssueManager()
                .getIssueObject(Long.parseLong(clientByUniqueId.getIssueId())); // получили id нужного тикета

        // кастом филды
        CustomField Name = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10108L);
        CustomField LastName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10109L);
        CustomField MiddleName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10110L);
        CustomField BirthDate = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10111L);
        CustomField PhoneNumber = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10112L);
        CustomField Email = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10113L);
        CustomField Address = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10114L);

        // значения из issue (не из бд)
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
