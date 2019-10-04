package com.sticket.app.sticket.api.retrofit.dto.request.auth;

public class FindEmailRequest {
    private String email;

    public FindEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
