package com.zera.bot.handlers.wildberries;

import com.zera.bot.handlers.HandlerState;
import com.zera.bot.handlers.IHandler;
import com.zera.bot.handlers.request.RequestCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class DemandForecastWbHandler implements IHandler {

    WildberriesHandler wildberriesHandler;

    @Override
    public HandlerState getName() {
        return HandlerState.WB_DEMAND_FORECAST;
    }

    public List<BotApiMethodMessage> handle(RequestCommand requestCommand) {
        SendMessage message = new SendMessage();
        message.setChatId(requestCommand.getChatId());
        setCustomKeyboard(message);
        return Collections.singletonList(message);
    }

    public SendMessage setCustomKeyboard(SendMessage message) {
        message.setText("Выбери действие");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> column = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Прогноз поставок");
        button1.setCallbackData(HandlerState.WB_GET_DEMAND_FORECAST.getCommand());
        column.add(Collections.singletonList(button1));

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Настроить автоматическую отправку");
        button2.setCallbackData("/wb-set-auto-demand");
        column.add(Collections.singletonList(button2));

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Назад");
        button3.setCallbackData(HandlerState.WB.getCommand());
        column.add(Collections.singletonList(button3));
        keyboardMarkup.setKeyboard(Collections.unmodifiableList(column));
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
