package com.sticket.app.sticket.retrofit.dto.request.auth;

public class SignInRequest {
    public static final String GRANT_TYPE = "password";

    private String username;
    private String password;
    private String grantType = GRANT_TYPE;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
