package com.br.ProductController.controller;

import com.br.ProductController.domain.user.AuthenticationDTO;
import com.br.ProductController.domain.user.LoginResponseDTO;
import com.br.ProductController.domain.user.RegisterDTO;
import com.br.ProductController.domain.user.User;
import com.br.ProductController.infra.security.TokenService;
import com.br.ProductController.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    ResponseEntity login(@RequestBody  AuthenticationDTO authenticationDTO){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return  ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO){
        if (this.userRepository.findByLogin(registerDTO.login())!=null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassord = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User (registerDTO.login(),encryptedPassord,registerDTO.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();

    }
}
