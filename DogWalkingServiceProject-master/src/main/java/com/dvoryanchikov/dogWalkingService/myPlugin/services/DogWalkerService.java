package com.dvoryanchikov.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.DogWalkerManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Dog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.DogWalker;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.DogWalkerStatus;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.DogWalkerIssueService;
import org.checkerframework.checker.units.qual.A;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DogWalkerService {

    private ActiveObjects ao;
    private DogWalkerManager dogWalkerManager;
    private DogWalkerIssueService dogWalkerIssueService;

    private DogWalkerService(ActiveObjects ao) {
        this.ao = ao;
        dogWalkerManager = DogWalkerManager.create(ao);
        dogWalkerIssueService = new DogWalkerIssueService(ao);
    }

    public static DogWalkerService create(ActiveObjects ao) {
        return new DogWalkerService(ao);
    }

    public DogWalker getDogWalkerByUniqueId(String uniqueId) {
        return dogWalkerManager.getByUniqueId(uniqueId);
    }

    public DogWalker[] getAllDogWalkers(){
        return dogWalkerManager.getAll();
    }

    public boolean createDogWalker(DogWalker model) throws Exception{
        model.setIssueId(dogWalkerIssueService.create(model).getId().toString());
        return dogWalkerManager.save(model);
    }

    public void deleteDogWalkerFromListener (Issue issue) throws Exception{
        // получили из базы модель клиента, которая соответствует удаляемому issue с jira
        DogWalker dogWalkerByIssueId = dogWalkerManager.getByIssueId(issue.getId().toString());

        dogWalkerManager.deleteByUniqueId(dogWalkerByIssueId.getUniqueId());

    }

    public void deleteDogWalkerByUniqueId(String uniqueId) throws Exception{
        dogWalkerIssueService.deleteIssue(uniqueId);
        dogWalkerManager.deleteByUniqueId(uniqueId);
    }

    public boolean updateDogWalker(DogWalker model) {
        dogWalkerManager.update(model);
        return dogWalkerIssueService.changeIssueStatus(model);
    }



    public void UpdateDogWalkerFromListener (Issue issue) throws Exception {

        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        DogWalker DogWalkerByIssueId = dogWalkerManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        DogWalker model = new DogWalker();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object Name = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10108L));
        Object LastName = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10109L));
        Object MiddleName = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10110L));
        Object BirthDate = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10111L));
        Object PhoneNumber = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10112L));
        Object Email = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10113L));
        Object DogWalkerStatusNew = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10115L));

        model.setName((String) Name);
        model.setLastName((String) LastName);
        model.setMiddleName((String) MiddleName);
        model.setBirthDate((Timestamp) BirthDate);
        model.setPhoneNumber((Double) PhoneNumber);
        model.setEmail((String) Email);

        if (DogWalkerStatusNew.toString().equals("FREE")) {
            model.setDogWalkerStatus(DogWalkerStatus.FREE);
        } else if (DogWalkerStatusNew.toString().equals("BUSY")) {
            model.setDogWalkerStatus(DogWalkerStatus.BUSY);
        }

//        model.setDogWalkerStatus((com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.DogWalkerStatus) DogWalkerStatus);


        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setUniqueId(DogWalkerByIssueId.getUniqueId());
        model.setIssueId(DogWalkerByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        dogWalkerManager.allUpdate(model);

    }

    public void allUpdateDogWalker(DogWalker model) throws Exception{

        DogWalkerManager manager = DogWalkerManager.create(ao);
        DogWalker dogWalkerByUniqueId = manager.getByUniqueId(model.getUniqueId());
        MutableIssue issue = ComponentAccessor.getIssueManager()
                .getIssueObject(Long.parseLong(dogWalkerByUniqueId.getIssueId())); // получили id нужного тикета

        // поля нашей issue
        CustomField Name =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10108L);
        CustomField LastName =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10109L);
        CustomField MiddleName =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10110L);
        CustomField BirthDate =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10111L);
        CustomField PhoneNumber =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10112L);
        CustomField Email =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10113L);
        CustomField DogWalkerStatus =ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10115L);

        // старое значение полей из БД
        String customFieldValueName = (String) issue.getCustomFieldValue(Name);
        String customFieldValueLastName = (String) issue.getCustomFieldValue(LastName);
        String customFieldValueMiddleName = (String) issue.getCustomFieldValue(MiddleName);
        String customFieldValueBirthDate = issue.getCustomFieldValue(BirthDate).toString();
        String customFieldValuePhoneNumber = issue.getCustomFieldValue(PhoneNumber).toString();
        String customFieldValueEmail = (String) issue.getCustomFieldValue(Email);
        String customFieldValueDogWalkerStatus = issue.getCustomFieldValue(DogWalkerStatus).toString();

        ArrayList<CustomField> listCustomField = new ArrayList<>();
        listCustomField.add(Name);
        listCustomField.add(LastName);
        listCustomField.add(MiddleName);
        listCustomField.add(BirthDate);
        listCustomField.add(PhoneNumber);
        listCustomField.add(Email);
        listCustomField.add(DogWalkerStatus);

        ArrayList<String> listOldValue = new ArrayList<>();
        listOldValue.add(customFieldValueName);
        listOldValue.add(customFieldValueLastName);
        listOldValue.add(customFieldValueMiddleName);
        listOldValue.add(customFieldValueBirthDate);
        listOldValue.add(customFieldValuePhoneNumber);
        listOldValue.add(customFieldValueEmail);
        listOldValue.add(customFieldValueDogWalkerStatus);

        ArrayList<Object> listNewValue = new ArrayList<>();
        listNewValue.add(model.getName());
        listNewValue.add(model.getLastName());
        listNewValue.add(model.getMiddleName());
        listNewValue.add(model.getBirthDate());
        listNewValue.add(model.getPhoneNumber());
        listNewValue.add(model.getEmail());

        FieldConfig relevantConfig = DogWalkerStatus.getRelevantConfig(issue);
        Options options = ComponentAccessor.getOptionsManager().getOptions(relevantConfig);
        Option optionStatusById = options.getOptionById(Long.parseLong(model.getDogWalkerStatus().getDescription()));

        listNewValue.add(optionStatusById);

        Map<CustomField, Object> map = new HashMap<>();
        for (int i = 0; i < listOldValue.size(); i++) {
            if (!(listOldValue.get(i).equals(listNewValue.get(i).toString()))) {
                map.put(listCustomField.get(i), listNewValue.get(i));
            }
        }

        if (!map.isEmpty()) {
            dogWalkerIssueService.updateIssue(map, issue);
            dogWalkerManager.allUpdate(model);
        } else {
            throw new Exception("пустая мапа");
        }

    }
}
