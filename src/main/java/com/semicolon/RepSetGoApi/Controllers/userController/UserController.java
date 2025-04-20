package com.semicolon.RepSetGoApi.Controllers.userController;

import com.semicolon.RepSetGoApi.Services.userServices.UserService;
import com.semicolon.RepSetGoApi.users.CreateUserRequestDo;
import com.semicolon.RepSetGoApi.users.LoginUserDto;
import com.semicolon.RepSetGoApi.users.LoginUserRequestDo;
import com.semicolon.RepSetGoApi.users.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController()
@RequestMapping("/rep-set-go/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequestDo userRequestDo) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(userRequestDo,UserDTO.class);
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }



}
