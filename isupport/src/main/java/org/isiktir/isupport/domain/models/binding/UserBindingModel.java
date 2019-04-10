package org.isiktir.isupport.domain.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private boolean needSupport;

    public UserBindingModel() {
    }

    @NotNull
    @Size(min = 3,max = 10)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(min = 3,max = 10)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotNull
    @Size(min = 3,max = 10)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull
    @Size(min = 3,max = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNeedSupport() {
        return needSupport;
    }

    public void setNeedSupport(boolean needSupport) {
        this.needSupport = needSupport;
    }
}
