package com.semicolon.RepSetGoApi.Repositories;

import com.semicolon.RepSetGoApi.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity,String> {
    UserEntity findByEmail(String email);
}
