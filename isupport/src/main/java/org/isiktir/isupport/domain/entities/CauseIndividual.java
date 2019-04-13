package org.isiktir.isupport.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "cause_individual")
public class CauseIndividual extends BaseEntity {
    private String name;
    private String description;
    private String cause;
    private String imgUrl;
    private BigDecimal neededMoney;
    private BigDecimal collectedMoney;
    private LocalDate byWhen;
    private Category category;
    private User user;
    private Level level;
    private Status status;

    public CauseIndividual() {
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Column(name = "name")
    @NotNull
    @Size(min = 3,max = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description",columnDefinition = "TEXT")
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "cause",columnDefinition = "TEXT")
    @NotNull
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Column(name = "image_url")
    @NotNull
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "needed_money",columnDefinition = "decimal(10,2) default '0'")
    @NotNull
    public BigDecimal getNeededMoney() {
        return neededMoney;
    }

    public void setNeededMoney(BigDecimal neededMoney) {
        this.neededMoney = neededMoney;
    }

    @Column( name = "colected_money",columnDefinition = "decimal(10,2) default '0.00'")
    public BigDecimal getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(BigDecimal collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    @NotNull
    @Column(name = "by_when")
    public LocalDate getByWhen() {
        return byWhen;
    }

    public void setByWhen(LocalDate byWhen) {
        this.byWhen = byWhen;
    }

    @NotNull
    @ManyToOne(targetEntity = Category.class)
    @JoinTable(name = "individual_cause_category",
            joinColumns = @JoinColumn(name = "cause_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "id"))
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @NotNull
    @ManyToOne
    @JoinTable(name = "individual_cause_user",
            joinColumns = @JoinColumn(name = "cause_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "id"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
