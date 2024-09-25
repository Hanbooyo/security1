package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

//Security가 /login 요청을 받아서 로그인을 진행함
//로그인이 완료되면 session에 넣어주기위해 (Security ContextHolder)
//오브젝트 => authentication 타입 객체
//Authentication 안에 User 정보가 있어야됨.
//User오브젝트 타입 => UserDetails 타입 객체

//Security Session => Authentication 객체 => UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user; // 콤포지션
    private Map<String, Object> attributes;

    //일반 로그인용
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //OAuth 로그인용
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttribute(String name) {
        return attributes;
    }

    //해당 User의 권한을 리턴하는 함수
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return attributes.get("sub").toString();
    }
}
