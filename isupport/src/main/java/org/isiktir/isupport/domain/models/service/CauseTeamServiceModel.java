package org.isiktir.isupport.domain.models.service;

import org.isiktir.isupport.domain.entities.Category;
import org.isiktir.isupport.domain.entities.Level;
import org.isiktir.isupport.domain.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CauseTeamServiceModel {

    private String id;
    private String name;
    private String description;
    private String cause;
    private int players;
    private int trainers;
    private int staff;
    private String imgUrl;
    private BigDecimal neededMoney;
    private BigDecimal collectedMoney;
    private LocalDate byWhen;
    private Category category;
    private User user;
    private Level level;

    public CauseTeamServiceModel() {
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getTrainers() {
        return trainers;
    }

    public void setTrainers(int trainers) {
        this.trainers = trainers;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getNeededMoney() {
        return neededMoney;
    }

    public void setNeededMoney(BigDecimal neededMoney) {
        this.neededMoney = neededMoney;
    }

    public BigDecimal getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(BigDecimal collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    public LocalDate getByWhen() {
        return byWhen;
    }

    public void setByWhen(LocalDate byWhen) {
        this.byWhen = byWhen;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
