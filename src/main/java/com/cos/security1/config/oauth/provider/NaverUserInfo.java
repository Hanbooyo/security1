package com.cos.security1.config.oauth.provider;


import java.util.Map;

public class NaverUserInfo implements OAuth2Userinfo {

    private Map<String, Object> attributes; // getAttributes()를 받을 예정

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "Naver";
    }
}
