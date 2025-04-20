package com.semicolon.RepSetGoApi.Services.userServices.implementations;

import com.semicolon.RepSetGoApi.Repositories.UserRepository;
import com.semicolon.RepSetGoApi.Services.userServices.UserService;
import com.semicolon.RepSetGoApi.users.LoginUserDto;
import com.semicolon.RepSetGoApi.users.UserDTO;
import com.semicolon.RepSetGoApi.users.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDTO createUser(UserDTO userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        String plainPwd = userDetails.getPwd_hash();
        String pwd_hash = bCryptPasswordEncoder.encode(plainPwd);
        userDetails.setPwd_hash(pwd_hash);
        userDetails.setCreatedDate(LocalDateTime.now());
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        UserDTO createdUser = modelMapper.map(userRepository.save(userEntity), UserDTO.class);
        return createdUser;
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity,UserDTO.class);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException("user not found");
        return new User(userEntity.getEmail(), userEntity.getPwd_hash(),
                true, true, true, true, new ArrayList<>());
    }
}
