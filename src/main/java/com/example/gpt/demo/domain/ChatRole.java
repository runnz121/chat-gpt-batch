package com.example.gpt.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatRole {

    SYSTEM("system"),

    USER("user"),
    ;

    private final String role;
}
