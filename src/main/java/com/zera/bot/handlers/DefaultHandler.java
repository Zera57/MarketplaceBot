
package com.zera.bot.handlers;

import com.zera.bot.handlers.request.RequestMessage;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;

@Service
public class DefaultHandler implements IHandler {

    @Override
    public HandlerState getName() {
        return HandlerState.DEFAULT;
    }

    @Override
    public BotApiMethodMessage handle(RequestMessage requestMessage) {
        requestMessage.getSession().setAttribute("state", HandlerState.MENU.getCommand());
        return IHandler.super.handle(requestMessage);
    }
}
