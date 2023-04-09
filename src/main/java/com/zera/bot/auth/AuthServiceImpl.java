package com.zera.bot.auth;

import com.zera.bot.auth.model.Account;
import com.zera.bot.auth.model.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private AccountRepository accountRepository;

    @Override
    public Account getAccount(Long telegramId) {
        Optional<Account> byTelegramId = accountRepository.findByTelegramId(telegramId);

        if (byTelegramId.isPresent())
            return byTelegramId.get();

        return byTelegramId.
                orElse(registerAccount(telegramId));
    }

    private Account registerAccount(Long telegramId) {
        Account account = new Account();
        account.setTelegramId(telegramId);
        return accountRepository.save(account);
    }
}
