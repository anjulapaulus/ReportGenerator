package com.codebuddy.controllers;

public class Users {

    protected String UsernameEmail,password,role;

    public Users(){}

    public Users(String UsernameEmail, String role){
        this.UsernameEmail = UsernameEmail;
        this.role = role;
    }

    public String getUsernameEmail() {
        return UsernameEmail;
    }

    public void setUsernameEmail(String usernameEmail) {
        this.UsernameEmail = usernameEmail;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public Users produce(String userEmailName, String role) {
        Users dataBean = new Users();
        dataBean.setUsernameEmail(userEmailName);
        dataBean.setRole(role);
        return dataBean;
    }
}
