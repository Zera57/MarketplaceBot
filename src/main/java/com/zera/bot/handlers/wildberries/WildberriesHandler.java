package com.zera.bot.handlers.wildberries;

import com.zera.bot.handlers.HandlerState;
import com.zera.bot.handlers.IHandler;
import com.zera.bot.handlers.menu.MenuHandler;
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
public class WildberriesHandler implements IHandler {

    MenuHandler menuHandler;

    @Override
    public HandlerState getName() {
        return HandlerState.WB;
    }

    public List<BotApiMethodMessage> handle(RequestCommand requestCommand) {
        SendMessage message = new SendMessage();
        message.setChatId(requestCommand.getChatId());
        setCustomKeyboard(message);

        return Collections.singletonList(message);
    }

    public SendMessage setCustomKeyboard(SendMessage message) {
        message.setText("Выбери действие");

        // Create ReplyKeyboardMarkup object
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<List<InlineKeyboardButton>> column = new ArrayList<>();

        InlineKeyboardButton wbToken = new InlineKeyboardButton();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        wbToken.setText("Изменить токен");
        wbToken.setCallbackData(HandlerState.WB_ADD_TOKEN.getCommand());
        // Add the first row to the keyboard
        column.add(Collections.singletonList(wbToken));

        InlineKeyboardButton wbCount = new InlineKeyboardButton();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        wbCount.setText("Прогноз поставок");
        wbCount.setCallbackData(HandlerState.WB_DEMAND_FORECAST.getCommand());
        // Add the first row to the keyboard
        column.add(Collections.singletonList(wbCount));

        // Create another keyboard row
        InlineKeyboardButton back = new InlineKeyboardButton();
        // Set each button for the second line
        back.setText("Назад");
        back.setCallbackData(HandlerState.MENU.getCommand());
        // Add the second row to the keyboard
        column.add(Collections.singletonList(back));
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(Collections.unmodifiableList(column));
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
