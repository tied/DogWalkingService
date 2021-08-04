package com.sorokin.dogWalkingService.myPlugin.services;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.sorokin.dogWalkingService.myPlugin.constants.CustomFieldConstants;
import com.sorokin.dogWalkingService.myPlugin.managers.DogWalkerManager;
import com.sorokin.dogWalkingService.myPlugin.models.DogWalker;
import com.sorokin.dogWalkingService.myPlugin.models.enums.DogWalkerStatus;
import com.sorokin.dogWalkingService.myPlugin.services.jira.DogWalkerIssueService;
import com.sorokin.dogWalkingService.myPlugin.utils.CustomFieldUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DogWalkerService {

    private final ActiveObjects ao;
    private final DogWalkerManager dogWalkerManager;
    private final DogWalkerIssueService dogWalkerIssueService;

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

    public void createDogWalker(DogWalker model) throws Exception{
        model.setIssueId(dogWalkerIssueService.create(model).getId().toString());
        dogWalkerManager.save(model);
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

    public void updateDogWalker(DogWalker model) {
        dogWalkerManager.update(model);
        dogWalkerIssueService.changeIssueStatus(model);
    }

    public void UpdateDogWalkerFromListener (Issue issue) throws Exception {
        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        DogWalker dogWalkerByIssueId = dogWalkerManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        DogWalker model = new DogWalker();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object birthDate = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_BIRTH_DATE_CF_ID));
        Object phoneNumber = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_PHONE_NUMBER_CF_ID));
        Object dogWalkerStatusNew = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_STATUS_CF_ID));

        String name = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_NAME_CF_ID);
        String lastName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_LAST_NAME_CF_ID);
        String middleName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_MIDDLE_NAME_CF_ID);
        String email = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_EMAIL_CF_ID);

        model.setName(name);
        model.setLastName(lastName);
        model.setMiddleName(middleName);
        model.setBirthDate((Timestamp) birthDate);
        model.setPhoneNumber((Double) phoneNumber);
        model.setEmail(email);

        if (dogWalkerStatusNew.toString().equals("FREE")) {
            model.setDogWalkerStatus(DogWalkerStatus.FREE);
        } else if (dogWalkerStatusNew.toString().equals("BUSY")) {
            model.setDogWalkerStatus(DogWalkerStatus.BUSY);
        }

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setUniqueId(dogWalkerByIssueId.getUniqueId());
        model.setIssueId(dogWalkerByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        dogWalkerManager.allUpdate(model);

    }

    public void allUpdateDogWalker(DogWalker model) throws Exception{

        DogWalkerManager manager = DogWalkerManager.create(ao);
        DogWalker dogWalkerByUniqueId = manager.getByUniqueId(model.getUniqueId());
        MutableIssue issue = ComponentAccessor.getIssueManager()
                .getIssueObject(Long.parseLong(dogWalkerByUniqueId.getIssueId())); // получили id нужного тикета

        // поля нашей issue
        CustomField name =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_NAME_CF_ID);
        CustomField lastName =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_LAST_NAME_CF_ID);
        CustomField middleName =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_MIDDLE_NAME_CF_ID);
        CustomField birthDate =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_BIRTH_DATE_CF_ID);
        CustomField phoneNumber =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_PHONE_NUMBER_CF_ID);
        CustomField email =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_EMAIL_CF_ID);
        CustomField dogWalkerStatus =ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_WALKER_STATUS_CF_ID);

        // старое значение полей из БД
        String customFieldValueBirthDate = issue.getCustomFieldValue(birthDate).toString();
        String customFieldValuePhoneNumber = issue.getCustomFieldValue(phoneNumber).toString();
        String customFieldValueDogWalkerStatus = issue.getCustomFieldValue(dogWalkerStatus).toString();

        String customFieldValueName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_NAME_CF_ID);
        String customFieldValueLastName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_LAST_NAME_CF_ID);
        String customFieldValueMiddleName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_MIDDLE_NAME_CF_ID);
        String customFieldValueEmail = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_WALKER_EMAIL_CF_ID);

        ArrayList<CustomField> listCustomField = new ArrayList<>();
        listCustomField.add(name);
        listCustomField.add(lastName);
        listCustomField.add(middleName);
        listCustomField.add(birthDate);
        listCustomField.add(phoneNumber);
        listCustomField.add(email);
        listCustomField.add(dogWalkerStatus);

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

        FieldConfig relevantConfig = dogWalkerStatus.getRelevantConfig(issue);
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
