package com.semicolon.RepSetGoApi.Services.userServices;

import com.semicolon.RepSetGoApi.users.LoginUserDto;
import com.semicolon.RepSetGoApi.users.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDetails);

    UserDTO getUserDetailsByEmail(String email);

}
