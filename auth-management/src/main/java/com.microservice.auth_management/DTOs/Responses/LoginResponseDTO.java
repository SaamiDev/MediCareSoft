package com.microservice.auth_management.DTOs.Responses;

public class LoginResponseDTO {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private String role;


    public LoginResponseDTO(String role, String accessToken, Long expiresIn) {
        this.role = role;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Token { ")
                .append("TokenType = ").append(tokenType)
                .append("Expires In = ").append(expiresIn)
                .append("Role = ").append(role);
        return sb.toString();
    }

}


