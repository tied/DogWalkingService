package com.sorokin.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.sorokin.dogWalkingService.myPlugin.constants.CustomFieldConstants;
import com.sorokin.dogWalkingService.myPlugin.managers.ClientManager;
import com.sorokin.dogWalkingService.myPlugin.models.Client;
import com.sorokin.dogWalkingService.myPlugin.services.jira.ClientIssueService;
import com.sorokin.dogWalkingService.myPlugin.utils.CustomFieldUtils;

import java.sql.Timestamp;
import java.util.*;

public class ClientService {
    private final ActiveObjects ao;
    private final ClientManager clientManager;
    private final ClientIssueService clientIssueService;

    private final DogService dogService;

    private ClientService(ActiveObjects ao) {
        this.ao = ao;
        clientManager = ClientManager.create(ao);
        clientIssueService = new ClientIssueService(ao);
        dogService = DogService.create(ao);
    }

    public static ClientService create(ActiveObjects ao) {
        return new ClientService(ao);
    }

    public Client getClientByUniqueId(String uniqueId) throws Exception {
        return clientManager.getByUniqueId(uniqueId);
    }

    public Client[] getAllClients() throws Exception{
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

        deletePetsByOwnerId(uniqueIdClient);

    }

    public void deleteClientByUniqueId(String uniqueId) throws Exception{
        clientIssueService.deleteIssue(uniqueId); // удалям из issue
        clientManager.deleteByUniqueId(uniqueId); // удаляем из бд
        deletePetsByOwnerId(uniqueId);
    }

    public void deletePetsByOwnerId (String ownerId) throws Exception {
            dogService.deleteAllPetsByOwnerId(ownerId);
    }

    public void UpdateClientFromListener (Issue issue) throws Exception {

        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        Client clientByIssueId = clientManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        Client model = new Client();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object birthDate = issue.
                getCustomFieldValue(customFieldManager.
                        getCustomFieldObject(CustomFieldConstants.CLIENT_BIRTH_DATE_CF_ID));
        Object phoneNumber = issue.
                getCustomFieldValue(customFieldManager.
                        getCustomFieldObject(CustomFieldConstants.CLIENT_PHONE_NUMBER_CF_ID));


        String name = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_NAME_CF_ID);
        String lastName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_LAST_NAME_CF_ID);
        String middleName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_MIDDLE_NAME_CF_ID);
        String email = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_EMAIL_CF_ID);
        String address = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_ADDRESS_CF_ID);

        model.setName(name);
        model.setLastName(lastName);
        model.setMiddleName(middleName);
        model.setBirthDate((Timestamp) birthDate);
        model.setPhoneNumber((Double) phoneNumber);
        model.setEmail(email);
        model.setAddress(address);

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setUniqueId(clientByIssueId.getUniqueId());
        model.setIssueId(clientByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        clientManager.update(model);

    }


    public void updateClient(Client model) throws Exception {
        ClientManager manager = ClientManager.create(ao);
        Client clientByUniqueId = manager.getByUniqueId(model.getUniqueId()); // получили клиента из базы по uniqueId
        MutableIssue issue = ComponentAccessor.getIssueManager()
                .getIssueObject(Long.parseLong(clientByUniqueId.getIssueId())); // получили id нужного тикета

        // кастом филды
        CustomField name = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_NAME_CF_ID);
        CustomField lastName = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_LAST_NAME_CF_ID);
        CustomField middleName = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_MIDDLE_NAME_CF_ID);
        CustomField birthDate = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_BIRTH_DATE_CF_ID);
        CustomField phoneNumber = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_PHONE_NUMBER_CF_ID);
        CustomField email = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_EMAIL_CF_ID);
        CustomField address = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.CLIENT_ADDRESS_CF_ID);

        // значения из issue (не из бд)
        String customFieldValueBirthDate = issue.getCustomFieldValue(birthDate).toString();
        String customFieldValuePhoneNumber = issue.getCustomFieldValue(phoneNumber).toString();

        String customFieldValueName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_NAME_CF_ID);
        String customFieldValueLastName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_LAST_NAME_CF_ID);
        String customFieldValueMiddleName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_MIDDLE_NAME_CF_ID);

        String customFieldValueEmail = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_EMAIL_CF_ID);
        String customFieldValueAddress = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.CLIENT_ADDRESS_CF_ID);


        ArrayList<CustomField> listCustomFields = new ArrayList<>();
        listCustomFields.add(name);
        listCustomFields.add(lastName);
        listCustomFields.add(middleName);
        listCustomFields.add(birthDate);
        listCustomFields.add(phoneNumber);
        listCustomFields.add(email);
        listCustomFields.add(address);

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
