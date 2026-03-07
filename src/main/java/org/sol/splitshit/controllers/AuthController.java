package org.sol.splitshit.controllers;

import org.jspecify.annotations.NonNull;
import org.sol.splitshit.auth.JwtService;
import org.sol.splitshit.models.SUser;
import org.sol.splitshit.repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
class AuthController {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepo userRepo,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull UserToken> register(@RequestParam String username,
                                                       @RequestBody PlainText password) {
        username = username.trim();
        if(username.isEmpty()) return ResponseEntity.badRequest().build();
        if(userRepo.existsById(username)) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        SUser user = SUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password.text()))
                .build();
        userRepo.save(user);
        return ResponseEntity.ok(new UserToken(username, jwtService.generate(username)));
    }

    @PostMapping("/login")
    public ResponseEntity<@NonNull UserToken> login(@RequestParam String username,
                                                    @RequestBody PlainText password) {
        var optUser = userRepo.findById(username);
        if(optUser.isEmpty()) return ResponseEntity.notFound().build();
        SUser user = optUser.get();
        if(!passwordEncoder.matches(password.text(), user.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(new UserToken(username, jwtService.generate(username)));
    }
}
