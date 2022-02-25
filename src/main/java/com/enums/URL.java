package com.enums;

public enum URL {

    BOARDS("boards"),
    CREATE_LIST("boards/4filFDhN/lists"),
    CARDS("cards"),
    LABELS("labels"),
    LISTS("lists"),
    CLOSED("closed"),
    CHECKLISTS("checklists"),
    CHECKITEMS("checkitems");

    private String urls;

    URL(String urls) {
        this.urls = urls;
    }

    public String getUrl() {
        return urls;
    }

}
