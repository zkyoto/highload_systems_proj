package ru.ifmo.cs.api_gateway.user.domain.value;

import lombok.Value;

@Value
public class Username{
    String value;

    private Username(String value){
        this.value = value;
    }

    public static Username of(String username){
        if (username == null || username.isEmpty()){
            throw new IllegalArgumentException("Username is null or empty");
        }
        return new Username(username);
    }
}
