package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;
    private UserService userSerivce;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env,UserService userSerivce) {

        this.env = env;
        this.userSerivce = userSerivce;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT "+
                " local.server.port = "+env.getProperty("local.server.port")+
                " gateway.ip = "+env.getProperty("gateway.ip")+
                " token.secret = "+env.getProperty("token.secret")+
                " token.expiration_time = "+env.getProperty("token.expiration_time")
        );
    }
    @GetMapping("/welcome")
        public String welcome() {
            return greeting.getMessage();
        }

    @PostMapping("/users")
    public ResponseEntity createUsr(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user,UserDto.class);
        userSerivce.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto,ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userSerivce.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add(new ModelMapper().map(v,ResponseUser.class));
        });


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUserId(@PathVariable("userId")String userId){
        UserDto userDto = userSerivce.getUserByUserId(userId);
        ResponseUser returnValue =new ModelMapper().map(userDto,ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
