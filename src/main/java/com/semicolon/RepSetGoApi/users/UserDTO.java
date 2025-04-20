package com.semicolon.RepSetGoApi.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private String userId;
    private String email;
    private String pwd_hash;
    private LocalDateTime createdDate;
}
