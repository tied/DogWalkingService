package com.sorokin.dogWalkingService.myPlugin.services.jira;

public enum IssueConstants {
    PROJECT_ID(10300L),
    ISSUE_TYPE_CLIENT_ID("10101"),
    ISSUE_TYPE_DOG_ID("10102"),
    ISSUE_TYPE_DOG_WALKER_ID("10103"),
    ISSUE_TYPE_REQUEST_WALK_ID("10104");

    final String id;
    final long numId;

    public String getId () {
        return id;
    }

    public long getNumId () {
        return numId;
    }

    IssueConstants(String id){

        this.id = id;
        this.numId = 0;
    }

    IssueConstants(long numId){

        this.numId = numId;
        this.id = "";

    }
}
