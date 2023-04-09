package com.zera.bot.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum HandlerState {
    MENU("/menu"),
    WB("/wb"),
    WB_ADD_TOKEN("/wb-add-token"),
    WB_DEMAND_FORECAST("/wb-demand-forecast"),
    WB_GET_DEMAND_FORECAST("/wb-get-demand-forecast"),
    DEFAULT("/default");

    private String command;

    public static HandlerState findCommand(String string) {
        return Arrays.stream(HandlerState.values())
                .filter(handler -> handler.getCommand().compareTo(string) == 0)
                .findFirst().orElse(DEFAULT);
    }
}
