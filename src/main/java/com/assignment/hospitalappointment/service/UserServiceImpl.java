package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.domain.User;
import com.assignment.hospitalappointment.repository.UserRepository;
import com.assignment.hospitalappointment.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<ResponseDto> createUser(UserCreationDto userCreationDto) {
        // check if no missed parameter
        if(userCreationDto.getFirst_name().isEmpty() || userCreationDto.getLast_name().isEmpty() || userCreationDto.getPassword().isEmpty() || userCreationDto.getUsername().isEmpty()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Missing parameters"),HttpStatus.BAD_REQUEST);
        }
        // check if username is not already taken
        Optional<User> usernameExist=userRepository.findByUsername(userCreationDto.getUsername());
        if(usernameExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"username already taken"),HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setFirstName(userCreationDto.getFirst_name());
        user.setLastName(userCreationDto.getLast_name());
        user.setUsername(userCreationDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity(new ResponseDto(HttpStatus.CREATED,"user created successful"),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDto> login(LoginDto loginDto) {
        try{
            // check if username and password are not  missed
            if(loginDto.getUsername().isEmpty() || loginDto.getPassword().isEmpty()){
                return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Missed parameter"),HttpStatus.BAD_REQUEST);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
            final UserDetails userDetails =loadUserByUsername(loginDto.getUsername());
            final String token = jwtUtil.generateToken(userDetails);

            //Extract user information
            User loggedInUser = userRepository.findByUsername(loginDto.getUsername()).get();
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK,"User logged in successful",new LoginResponseDto(loggedInUser.getId(),token,loggedInUser.getFirstName(),loggedInUser.getLastName())), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
        catch (DisabledException e) {
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> exist=userRepository.findByUsername(username);
        if(!exist.isPresent()){
            throw new UsernameNotFoundException("user does not exist");
        }
        User user=new User();
        user=exist.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }
}
