package com.example.beaware.Models;

public class userModel {
    private String userId;
    private String email;
    private String phone;
    private String password;
    private String userType;
    private Boolean isActive;
    private Boolean isCvUploaded;
    private String AcceptedStatus;
    private String Name ;
    private String cvUrl;

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public userModel(String userId, String email, String phone, String password, String userType, boolean active, boolean cvUploaded , String acceptedStatus ,String name,String cvUrl) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
        this.isActive = active;
        this.isCvUploaded = cvUploaded;
        this.AcceptedStatus = acceptedStatus;
        this.Name = name;
        this.cvUrl = cvUrl;
    }

    public userModel() {
    }

    public String getAcceptedStatus() {
        return AcceptedStatus;
    }

    public void setAcceptedStatus(String acceptedStatus) {
        AcceptedStatus = acceptedStatus;
    }

    public Boolean getCvUploaded() {
        return isCvUploaded;
    }

    public void setCvUploaded(Boolean cvUploaded) {
        isCvUploaded = cvUploaded;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
