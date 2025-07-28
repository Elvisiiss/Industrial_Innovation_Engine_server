package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private UserInfo user;

    @Data
    public static class UserInfo {
        private Long id;
        private String name;
        private String role;

        public UserInfo(Long id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }
    }

    public LoginResponse(String token, UserInfo user) {
        this.token = token;
        this.user = user;
    }

    public static LoginResponse success(String token, Long userId, String userName, String userRole) {
        return new LoginResponse(token, new UserInfo(userId, userName, userRole));
    }

    public static LoginResponse error(String message) {
        return new LoginResponse(null, null);
    }
}
