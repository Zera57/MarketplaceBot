package com.zera.bot.handlers.request;

import com.zera.bot.auth.model.Account;
import com.zera.bot.handlers.HandlerState;
import lombok.Data;
import org.apache.shiro.session.Session;

@Data
public abstract class ARequest {
    Session session;
    Long chatId;
    Account account;
    HandlerState handlerState;
}
