package org.isiktir.isupport.domain.models.view;

public class CheckoutModel {
    private String id;
    private String unit;

    public CheckoutModel(String id, String unit) {
        this.id = id;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
