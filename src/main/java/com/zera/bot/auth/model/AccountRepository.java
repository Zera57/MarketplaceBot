package com.zera.bot.auth.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByTelegramId(Long telegramId);

    @Modifying
    @Query("update Account a set a.wbToken = :wbToken where a.telegramId = :telegramId")
    void addWbToken(@Param("telegramId") Long telegramId,
                    @Param("wbToken") String wbToken);
}
