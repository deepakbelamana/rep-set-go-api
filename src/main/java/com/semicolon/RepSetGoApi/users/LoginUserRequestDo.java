package com.semicolon.RepSetGoApi.users;

public class LoginUserRequestDo {
    private String email;
    private String pwd_hash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd_hash() {
        return pwd_hash;
    }

    public void setPwd_hash(String pwd_hash) {
        this.pwd_hash = pwd_hash;
    }
}
