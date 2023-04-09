package com.zera.bot.auth;

import com.zera.bot.auth.model.Account;

public interface AuthService {

    Account getAccount(Long telegramId);
}
