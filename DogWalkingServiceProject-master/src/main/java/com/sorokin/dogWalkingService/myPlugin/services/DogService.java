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
import com.sorokin.dogWalkingService.myPlugin.managers.DogManager;
import com.sorokin.dogWalkingService.myPlugin.models.Dog;
import com.sorokin.dogWalkingService.myPlugin.models.enums.DogStatus;
import com.sorokin.dogWalkingService.myPlugin.models.enums.Gender;
import com.sorokin.dogWalkingService.myPlugin.services.jira.DogIssueService;
import com.sorokin.dogWalkingService.myPlugin.utils.CustomFieldUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DogService {
    private final ActiveObjects ao;
    private final DogManager dogManager;
    private final DogIssueService dogIssueService;

    private DogService(ActiveObjects ao) {
        this.ao = ao;
        this.dogManager = DogManager.create(ao);
        this.dogIssueService = new DogIssueService(ao);
    }

    public static DogService create(ActiveObjects ao) {
        return new DogService(ao);
    }

    public Dog getDogByUniqueId(String uniqueId) {
        return dogManager.getByUniqueId(uniqueId);
    }

    public Dog[] getDogByOwnerId(String ownerId){
        return dogManager.getByOwnerId(ownerId);
    }

    public Dog[] getAllDogs() {
        return dogManager.getAll();
    }

    public void createDog(Dog model) throws Exception{
        model.setIssueId(dogIssueService.create(model).getId().toString());
        dogManager.save(model);
    }

    public void deleteAllPetsByOwnerId (String ownerId) throws Exception{
            dogIssueService.deleteAllByOwnerId(ownerId);
    }

    public void deleteDogFromListener (Issue issue) throws Exception{
        // получили из базы модель клиента, которая соответствует удаляемому issue с jira
        Dog dogByIssueId = dogManager.getByIssueId(issue.getId().toString());

        dogManager.deleteByUniqueId(dogByIssueId.getUniqueId());

    }

    public void deleteDogByUniqueId(String uniqueId) throws Exception{
        dogIssueService.deleteIssue(uniqueId);
        dogManager.deleteByUniqueId(uniqueId);
    }

    // обновления статуса!
    public void updateDog(Dog model) {
        dogManager.update(model);
        dogIssueService.updateChangeStatusIssue(model);
    }

    public void UpdateDogFromListener (Issue issue) throws Exception {
        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        Dog dogByIssueId = dogManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        Dog model = new Dog();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object genderNew = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.DOG_GENDER_CF_ID));
        Object dogBirthDate = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.DOG_BIRTH_DATE_CF_ID));
        Object dogStatus = issue.getCustomFieldValue(customFieldManager.
                getCustomFieldObject(CustomFieldConstants.DOG_STATUS_CF_ID));

        String dogName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_NAME_CF_ID);
        String breed = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_BREED_CF_ID);
        String color = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_COLOR_CF_ID);
        String dogCharacter = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_CHARACTER_CF_ID);

        model.setDogName(dogName);

        if (genderNew.toString().equals("boy")) {
            model.setGender(Gender.boy);
        } else if (genderNew.toString().equals("girl")) {
            model.setGender(Gender.girl);
        }

        model.setDogBirthDate((Timestamp) dogBirthDate);

        if (dogStatus.toString().equals("WALKING")) {
            model.setDogStatus(DogStatus.WALKING);
        } else if (dogStatus.toString().equals("AT_HOME")) {
            model.setDogStatus(DogStatus.AT_HOME);
        }

        model.setBreed(breed);
        model.setColor(color);
        model.setDogCharacter(dogCharacter);

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setOwnerId(dogByIssueId.getOwnerId());
        model.setUniqueId(dogByIssueId.getUniqueId());
        model.setIssueId(dogByIssueId.getIssueId());

        // обновили модель в базе новой моделькой, собранной из пришедшей issue
        try {
            dogManager.fullUpdate(model);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void fullUpdateDog(Dog model) throws Exception{
        DogManager manager = DogManager.create(ao);
        Dog dogByUniqueId = manager.getByUniqueId(model.getUniqueId()); // получили питомца из базы по uniqueId
        MutableIssue issue = ComponentAccessor.getIssueManager()
                .getIssueObject(Long.parseLong(dogByUniqueId.getIssueId())); // получили id нужного тикета

        // поля нашей issue
        CustomField dogName = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_NAME_CF_ID);
        CustomField gender = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_GENDER_CF_ID);
        CustomField dogBirthDate = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_BIRTH_DATE_CF_ID);
        CustomField breed = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_BREED_CF_ID);
        CustomField color = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_COLOR_CF_ID);
        CustomField dogCharacter = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_CHARACTER_CF_ID);
        CustomField ownerName = ComponentAccessor.getCustomFieldManager().
                getCustomFieldObject(CustomFieldConstants.DOG_OWNER_ID_CF_ID);

        // старое значение полей из БД
        String customFieldValueGender = issue.getCustomFieldValue(gender).toString();
        String customFieldValueDogBirthDate = issue.getCustomFieldValue(dogBirthDate).toString();

        String customFieldValueDogName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_NAME_CF_ID);
        String customFieldValueBreed = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_BREED_CF_ID);
        String customFieldValueColor = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_COLOR_CF_ID);
        String customFieldValueDogCharacter = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_CHARACTER_CF_ID);
        String customFieldValueOwnerName = CustomFieldUtils.getCustomFieldString(issue, CustomFieldConstants.DOG_OWNER_ID_CF_ID);


        ArrayList<CustomField> listCustomField = new ArrayList<>();
        listCustomField.add(dogName);
        listCustomField.add(gender);
        listCustomField.add(dogBirthDate);
        listCustomField.add(breed);
        listCustomField.add(color);
        listCustomField.add(dogCharacter);
        listCustomField.add(ownerName);

        ArrayList<String> listOldValue = new ArrayList<>();
        listOldValue.add(customFieldValueDogName);
        listOldValue.add(customFieldValueGender);
        listOldValue.add(customFieldValueDogBirthDate);
        listOldValue.add(customFieldValueBreed);
        listOldValue.add(customFieldValueColor);
        listOldValue.add(customFieldValueDogCharacter);
        listOldValue.add(customFieldValueOwnerName);

        ArrayList<Object> listNewValue = new ArrayList<>();
        listNewValue.add(model.getDogName());

        FieldConfig relevantConfig = gender.getRelevantConfig(issue);
        Options options = ComponentAccessor.getOptionsManager().getOptions(relevantConfig);
        Option optionGenderById = options.getOptionById(Long.parseLong(model.getGender().getDescription()));


        listNewValue.add(optionGenderById);
        listNewValue.add(model.getDogBirthDate());
        listNewValue.add(model.getBreed());
        listNewValue.add(model.getColor());
        listNewValue.add(model.getDogCharacter());
        listNewValue.add(model.getOwnerId());

        Map<CustomField, Object> map = new HashMap<>();
        for (int i = 0; i < listOldValue.size(); i++) {
            if (!(listOldValue.get(i).equals(listNewValue.get(i).toString()))) {
                map.put(listCustomField.get(i), listNewValue.get(i));
            }
        }

        if (!map.isEmpty()) {
            dogIssueService.updateIssue(map, issue);
            dogManager.fullUpdate(model);
        } else {
            throw new Exception("пуста мапа");
        }
    }
}
