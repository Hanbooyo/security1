package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD함수를 JpaRepository가 들고있음
//@repository 어노테이션 없어도 IoC됨, 이유는 상속이라서
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 규칙 -> Username 문법
    // select * from user where username = ?
    User findByUsername(String username);

}
