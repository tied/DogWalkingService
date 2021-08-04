package com.sorokin.dogWalkingService.myPlugin.utils;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;

public class CustomFieldUtils {

    public static String getCustomFieldString (Issue issue, Long id) {

        return (String) issue.getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject(id));

    }



}
