package com.sticket.app.sticket.retrofit.dto.request.auth;

public class UpdatePasswordRequest {
    private String newPassword;

    public UpdatePasswordRequest(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
