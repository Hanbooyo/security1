package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Security가 /login 요청을 받아서 로그인을 진행함
//로그인이 완료되면 session에 넣어주기위해 (Security ContextHolder)
//오브젝트 => authentication 타입 객체
//Authentication 안에 User 정보가 있어야됨.
//User오브젝트 타입 => UserDetails 타입 객체

//Security Session => Authentication 객체 => UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails {
    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
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
}
