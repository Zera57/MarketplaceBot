
package com.zera.bot.handlers;

import com.zera.bot.handlers.request.RequestMessage;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;

import java.util.List;

@Service
public class DefaultHandler implements IHandler {

    @Override
    public HandlerState getName() {
        return HandlerState.DEFAULT;
    }

    @Override
    public List<BotApiMethodMessage> handle(RequestMessage requestMessage) {
        requestMessage.getSession().setAttribute("state", HandlerState.MENU.getCommand());
        return IHandler.super.handle(requestMessage);
    }
}
