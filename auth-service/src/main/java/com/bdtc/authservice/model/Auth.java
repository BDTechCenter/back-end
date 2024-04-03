package com.bdtc.authservice.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    private String network_name;
    private String username;
    private ArrayList<String> roles;
}
