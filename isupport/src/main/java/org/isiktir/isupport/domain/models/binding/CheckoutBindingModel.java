package org.isiktir.isupport.domain.models.binding;

import java.math.BigDecimal;

public class CheckoutBindingModel {
    private String id;
    private BigDecimal money;

    public CheckoutBindingModel() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
