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
import com.atlassian.renderer.v2.MutableRenderer;
import com.dvoryanchikov.dogWalkingService.myPlugin.managers.DogManager;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Client;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.Dog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.Gender;
import com.dvoryanchikov.dogWalkingService.myPlugin.services.jira.DogIssueService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DogService {
    private ActiveObjects ao;
    private DogManager dogManager;
    private DogIssueService dogIssueService;

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

    public boolean createDog(Dog model) throws Exception{
        model.setIssueId(dogIssueService.create(model).getId().toString());
        return dogManager.save(model);
    }

    public void deleteAllPetsByOwnerId (String ownerId) throws Exception{

        try {
            dogIssueService.deleteAllByOwnerId(ownerId);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

//        dogManager.deleteAllByOwnerId(ownerId);

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
    public boolean updateDog(Dog model) {
        dogManager.update(model);
        return dogIssueService.updateChangeStatusIssue(model);
    }

//    List<Option> getOption (Issue issue,CustomField customField, List<String> optionList) {
//        FieldConfig relevantConfig = customField.getRelevantConfig(issue);
//        Options options = ComponentAccessor.getOptionsManager().getOptions(relevantConfig);
//        options.getOptionById()
//    }


    public void UpdateDogFromListener (Issue issue) throws Exception {

        // получили из базы модель клиента, которая соответствует обновляемому issue с jira
        Dog DogByIssueId = dogManager.getByIssueId(issue.getId().toString());

        // используя issue, которая пришла от Jira собрали модельку клиента
        Dog model = new Dog();

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

        Object DogName = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10101L));
        Object GenderNew = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10100L));
        Object DogBirthDate = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10102L));
        Object Breed = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10103L));
        Object Color = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10104L));
        Object DogCharacter = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10105L));
        Object OwnerName = issue.getCustomFieldValue(customFieldManager.getCustomFieldObject(10121L));

        model.setDogName((String) DogName);

        if (GenderNew.toString().equals("boy")) {
            model.setGender(Gender.boy);
        } else if (GenderNew.toString().equals("girl")) {
            model.setGender(Gender.girl);
        }

        model.setDogBirthDate((Timestamp) DogBirthDate);
        model.setBreed((String) Breed);
        model.setColor((String) Color);
        model.setDogCharacter((String) DogCharacter);

        model.setOwnerId(DogByIssueId.getOwnerId());

//        model.setOwnerId((String) OwnerName);

        // с помощью модельки клиента из базы дополнили новую модель айдишниками
        model.setUniqueId(DogByIssueId.getUniqueId());
        model.setIssueId(DogByIssueId.getIssueId());

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
        CustomField DogName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10101L);
        CustomField Gender = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10100L);
        CustomField DogBirthDate = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10102L);
        CustomField Breed = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10103L);
        CustomField Color = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10104L);
        CustomField DogCharacter = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10105L);
//        CustomField DogStatus = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10106L);
        CustomField OwnerName = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(10121L);

        // старое значение полей из БД
        String customFieldValueDogName = (String) issue.getCustomFieldValue(DogName);
        String customFieldValueGender = issue.getCustomFieldValue(Gender).toString();
        String customFieldValueDogBirthDate = issue.getCustomFieldValue(DogBirthDate).toString();
        String customFieldValueBreed = (String) issue.getCustomFieldValue(Breed);
        String customFieldValueColor = (String) issue.getCustomFieldValue(Color);
        String customFieldValueDogCharacter = (String) issue.getCustomFieldValue(DogCharacter);
        String customFieldValueOwnerName = (String) issue.getCustomFieldValue(OwnerName);

        ArrayList<CustomField> listCustomField = new ArrayList<>();
        listCustomField.add(DogName);
        listCustomField.add(Gender);
        listCustomField.add(DogBirthDate);
        listCustomField.add(Breed);
        listCustomField.add(Color);
        listCustomField.add(DogCharacter);
        listCustomField.add(OwnerName);

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

        FieldConfig relevantConfig = Gender.getRelevantConfig(issue);
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
