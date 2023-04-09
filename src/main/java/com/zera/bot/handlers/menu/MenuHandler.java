package com.zera.bot.handlers.menu;

import com.zera.bot.handlers.HandlerState;
import com.zera.bot.handlers.IHandler;
import com.zera.bot.handlers.request.RequestCommand;
import com.zera.bot.handlers.request.RequestMessage;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MenuHandler implements IHandler {

    public static final HandlerState HANDLER = HandlerState.MENU;

    @Override
    public HandlerState getName() {
        return HANDLER;
    }

    public List<BotApiMethodMessage> handle(RequestCommand requestCommand) {
        SendMessage message = new SendMessage();
        message.setChatId(requestCommand.getChatId());
        setCustomKeyboard(message);
        return Collections.singletonList(message);
    }

    public List<BotApiMethodMessage> handle(RequestMessage requestMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(requestMessage.getChatId());
        setCustomKeyboard(message);
        return Collections.singletonList(message);
    }

    public SendMessage setCustomKeyboard(SendMessage message) {
        message.setText("Выбери маркетплейс");

        // Create ReplyKeyboardMarkup object
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<List<InlineKeyboardButton>> column = new ArrayList<>();

        InlineKeyboardButton wb = new InlineKeyboardButton();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        wb.setText("Wildberries");
        wb.setCallbackData(HandlerState.WB.getCommand());
        // Add the first row to the keyboard
        column.add(Collections.singletonList(wb));
        // Create another keyboard row
        InlineKeyboardButton ozon = new InlineKeyboardButton();
        // Set each button for the second line
        ozon.setText("Ozon");
        ozon.setCallbackData("/ozon");
        // Add the second row to the keyboard
        column.add(Collections.singletonList(ozon));
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(Collections.unmodifiableList(column));
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
