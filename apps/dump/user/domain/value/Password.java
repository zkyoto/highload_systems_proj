package ru.ifmo.cs.api_gateway.user.domain.value;

import lombok.Value;

@Value
public class Password {
    String value;

    private Password(String value){
        this.value = value;
    }

    public static Password of(String password){
        if (password == null || password.isEmpty()){
            throw new IllegalArgumentException("Password is null or empty");
        }
        return new Password(password);
    }
}

