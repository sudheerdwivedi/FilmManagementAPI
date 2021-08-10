package com.film.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.film.websocket.dtos.UserDto;

import javax.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="USER_NAME",unique = true)
    private String username;

    @Column(name ="USER_PASSWORD")
    private String password;
    
    @Column(name ="AUTHORITY")
    private String authority;

    public User(){
    }

    public User(UserDto dto, PasswordEncoder encoder){
        username = dto.getUsername();
        password = encoder.encode(dto.getPassword());
        authority = "ROLE_USER";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
