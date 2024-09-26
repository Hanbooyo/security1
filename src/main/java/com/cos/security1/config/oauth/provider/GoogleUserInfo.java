package com.cos.security1.config.oauth.provider;


import java.util.HashMap;
import java.util.Map;

public class GoogleUserInfo implements OAuth2Userinfo {
    
    private Map<String, Object> attributes; // getAttributes()를 받을 예정

    public GoogleUserInfo(Map<String, Object> attributes) {
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
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "Google";
    }
}
