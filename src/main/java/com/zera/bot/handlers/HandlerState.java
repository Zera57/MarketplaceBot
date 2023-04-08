package com.zera.bot.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum HandlerState {
    MENU("/menu"),
    WB("/wb"),
    WB_TOKEN("/wb-add-token"),
    DEFAULT("/default");

    private String command;

    public static HandlerState findCommand(String string) {
        return Arrays.stream(HandlerState.values())
                .filter(handler -> handler.getCommand().compareTo(string) == 0)
                .findFirst().orElse(DEFAULT);
    }
}
