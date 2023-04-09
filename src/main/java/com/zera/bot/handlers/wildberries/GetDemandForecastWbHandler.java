package com.zera.bot.handlers.wildberries;

import com.zera.bot.handlers.HandlerState;
import com.zera.bot.handlers.IHandler;
import com.zera.bot.handlers.request.RequestCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class GetDemandForecastWbHandler implements IHandler {

    WildberriesHandler wildberriesHandler;
    AddTokenWbHandler addTokenWbHandler;

    @Override
    public HandlerState getName() {
        return HandlerState.WB_GET_DEMAND_FORECAST;
    }

    public List<BotApiMethodMessage> handle(RequestCommand requestCommand) {
        if (requestCommand.getAccount().getWbToken() == null) {
            return responseWithoutToken(requestCommand);
        }

        SendMessage message = new SendMessage();
        message.setChatId(requestCommand.getChatId());
        message.setText("Отправляем файл с токеном " + requestCommand.getAccount().getWbToken());

        requestCommand.getSession().setAttribute("state", HandlerState.MENU.getCommand());
        return Collections.singletonList(message);
    }

    private List<BotApiMethodMessage> responseWithoutToken(RequestCommand requestCommand) {
        requestCommand.getSession().setAttribute("state", HandlerState.WB_ADD_TOKEN.getCommand());
        return addTokenWbHandler.handle(requestCommand);
    }
}
