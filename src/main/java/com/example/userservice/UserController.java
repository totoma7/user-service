package com.example.userservice;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return "It's Working in User Service";
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



}
