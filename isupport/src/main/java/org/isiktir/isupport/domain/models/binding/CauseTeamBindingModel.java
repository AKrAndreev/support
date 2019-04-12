package org.isiktir.isupport.domain.models.binding;

import org.isiktir.isupport.domain.entities.Category;
import org.isiktir.isupport.domain.entities.Level;
import org.isiktir.isupport.domain.entities.User;
import org.isiktir.isupport.validations.datevalidation.AfterDate;
import org.isiktir.isupport.validations.enumvalidation.ValidateEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CauseTeamBindingModel {

    @NotNull
    @Size(min = 3, max = 30, message = "Invalid name! Name should be long between 3 and 30 characters!")
    private String name;

    @NotNull
    @Size(min = 50, message = "Invalid content! Content should be at least 50 characters!")
    private String description;

    @NotNull
    @Size(min = 50, message = "Invalid content! Content should be at least 50 characters!")
    private String cause;

    @NotNull
    @Min(value = 5,message = "To be considered as a team, at least 5 players should be available!")
    private int players;

    @NotNull
    @Min(value = 1, message = "To be considered as a team, trainer is needed!")
    private int trainers;

    private int staff;

    @NotNull(message = "Image is required!")
    private MultipartFile image;

    @NotNull
    @Min(value = 100,message = "Minimum money for applying for funding is 100 banana!")
    private BigDecimal neededMoney;

    @NotNull
    @AfterDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate byWhen;

    @NotNull(message = "category is a must!")
    private String category;

    @NotNull(message = "User is a must!")
    private User user;

    @NotNull
    @ValidateEnum(targetClassType = Level.class)
    private String level;

    public CauseTeamBindingModel() {
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


    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public BigDecimal getNeededMoney() {
        return neededMoney;
    }

    public void setNeededMoney(BigDecimal neededMoney) {
        this.neededMoney = neededMoney;
    }


    public LocalDate getByWhen() {
        return byWhen;
    }

    public void setByWhen(LocalDate byWhen) {
        this.byWhen = byWhen;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
