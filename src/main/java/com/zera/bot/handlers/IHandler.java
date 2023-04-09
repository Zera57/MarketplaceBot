package com.zera.bot.handlers;

import com.zera.bot.handlers.request.ARequest;
import com.zera.bot.handlers.request.RequestCommand;
import com.zera.bot.handlers.request.RequestMessage;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

public interface IHandler {
    HandlerState getName();
    default List<BotApiMethodMessage> handle(RequestCommand requestCommand) {
        SendMessage message = new SendMessage();
        message.setChatId(requestCommand.getChatId());
        message.setText("Пока фича не работает(");
        return Collections.singletonList(message);
    }
    default List<BotApiMethodMessage> handle(RequestMessage requestMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(requestMessage.getChatId());
        message.setText("Я не понял зачем ты это пункнул так, что давай по хорошему. Воспользуйся /menu");
        return Collections.singletonList(message);
    }

    default List<BotApiMethodMessage> handle(ARequest request) {
        if (request instanceof RequestMessage) {
            return this.handle((RequestMessage) request);
        } else {
            return this.handle((RequestCommand) request);
        }
    }
}
