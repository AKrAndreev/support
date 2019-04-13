package org.isiktir.isupport.domain.entities;

public enum Status {
    UNDER_REVIEW("Under review"),
    APPROVED("Approved"),
    CANCELED("Canceled"),
    FINISHED("Finished");

    private String value;

    Status(String val){
        this.value=val;
    }

    public String getValue(){
        return value;
    }

    public static Status[] getValues(){
        return Status.values();
    }
}
