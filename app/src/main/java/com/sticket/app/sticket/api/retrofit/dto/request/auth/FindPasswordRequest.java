package com.sticket.app.sticket.api.retrofit.dto.request.auth;

public class FindPasswordRequest {
    private String email;

    public FindPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
