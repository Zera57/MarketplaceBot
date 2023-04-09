package com.zera.bot.auth;

import com.zera.bot.auth.model.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Override
    public void addWbToken(Long telegramId, String wbToken) {
        accountRepository.addWbToken(telegramId, wbToken);
    }
}
