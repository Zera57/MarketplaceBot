package com.zera.bot.handlers.wildberries;

import com.zera.bot.auth.AccountService;
import com.zera.bot.handlers.HandlerState;
import com.zera.bot.handlers.IHandler;
import com.zera.bot.handlers.request.RequestCommand;
import com.zera.bot.handlers.request.RequestMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class AddTokenWbHandler implements IHandler {

    AccountService accountService;

    WildberriesHandler wildberriesHandler;

    @Override
    public HandlerState getName() {
        return HandlerState.WB_TOKEN;
    }

    public List<BotApiMethodMessage> handle(RequestCommand requestCommand) {
        SendMessage message = new SendMessage();
        message.setChatId(requestCommand.getChatId());
        message.setText("Отправь мне свой токен для WildBerries:");
        return Collections.singletonList(message);
    }

    public List<BotApiMethodMessage> handle(RequestMessage requestMessage) {
        // Добавляем токен
        accountService.addWbToken(
                requestMessage.getAccount().getTelegramId(),
                requestMessage.getMessage()
        );
        requestMessage.getAccount().setWbToken(requestMessage.getMessage());

        // Подготавливаем сообщение
        SendMessage message = new SendMessage();
        message.setChatId(requestMessage.getChatId());
        message.setText("Токен сохранен");

        // Делаем запрос в другой обработчик
        RequestCommand rq = new RequestCommand();
        rq.setChatId(requestMessage.getChatId());
        rq.setSession(requestMessage.getSession());

        // Обновляем статус клиенту
        rq.getSession().setAttribute("state", HandlerState.WB.getCommand());

        return List.of(message, wildberriesHandler.handle(rq).get(0));
    }
}
