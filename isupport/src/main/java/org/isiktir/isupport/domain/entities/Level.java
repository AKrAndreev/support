package org.isiktir.isupport.domain.entities;

public enum Level {
    PROFESSIONAL("Professional")
    ,AMATEUR("Amateur");

    private String value;
    Level(String val){
        this.value=val;
    }

    public String getValue(){
        return value;
    }

    public static Level[] getValues(){
        return Level.values();
    }
}
