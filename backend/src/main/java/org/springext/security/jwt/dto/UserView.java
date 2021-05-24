package org.springext.security.jwt.dto;

public class UserView {

    private final String userKey;

    private final String userName;

    public UserView(String userKey, String userName) {
        this.userKey = userKey;
        this.userName = userName;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getUserName() {
        return userName;
    }
}
