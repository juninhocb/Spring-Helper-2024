package com.carlosjr.jdbctenant;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserBuilder userBuilder = User.builder();

        switch (username){
            case "john":
                userBuilder.username("john");
                userBuilder.password(passwordEncoder.encode("j123"));
                userBuilder.authorities("ocli", "user");
                break;
            case "anne":
                userBuilder.username("anne");
                userBuilder.password(passwordEncoder.encode("a123"));
                userBuilder.authorities("tcli", "user");
                break;
            default:
                throw  new UsernameNotFoundException("Username not found: " + username);
        }

        return userBuilder.build();
    }
}
