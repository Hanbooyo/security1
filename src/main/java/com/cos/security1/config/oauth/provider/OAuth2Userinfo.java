package com.cos.security1.config.oauth.provider;

public interface OAuth2Userinfo {
    String getName();
    String getEmail();
    String getProviderId();
    String getProvider();
}
