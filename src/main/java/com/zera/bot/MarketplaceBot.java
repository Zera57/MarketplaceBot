package com.zera.bot;

import com.zera.bot.handlers.HandlerState;
import com.zera.bot.handlers.IHandler;
import com.zera.bot.handlers.request.ARequest;
import com.zera.bot.handlers.request.RequestCommand;
import com.zera.bot.handlers.request.RequestMessage;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.session.TelegramLongPollingSessionBot;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MarketplaceBot extends TelegramLongPollingSessionBot {
    public static final String NAME_BOT = "MarketplaceBot";
//    private final AuthService authService;
    Map<HandlerState, IHandler> handlerMap;

    public MarketplaceBot(@Value("5995938284:AAFjkkDj5r3CHHTe-mEUEhGsKI14JOSS_AM") String botToken,
//                          AuthService authService,
                          ApplicationContext applicationContext) {
        super(botToken);
//        this.authService = authService;

        // Собираем все IHandler
        Map<String, IHandler> beansOfType = applicationContext.getBeansOfType(IHandler.class);
        handlerMap = beansOfType.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getValue().getName(), Map.Entry::getValue));
    }

    @Override
    public String getBotUsername() {
        return NAME_BOT;
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onUpdateReceived(Update update, Optional<Session> botSession) {
        Session session = botSession.get();

        ARequest requestCommand = getRequest(update, session);

        session.setAttribute("state", requestCommand.getHandlerState().getCommand());
        IHandler iHandler = handlerMap.get(requestCommand.getHandlerState());

        try {
            execute(iHandler.handle(requestCommand));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


        System.out.println("State " + session.getAttribute("state"));
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }

    ARequest getRequest(Update update, Session session) {
        ARequest request;
        Long telegramId;

        if (update.hasMessage()) {
            request = new RequestMessage();
            telegramId = update.getMessage().getFrom().getId();

//            request.setAccount(authService.getAccount(telegramId));
            request.setChatId(update.getMessage().getChatId());

            request.setHandlerState(HandlerState.findCommand(String.valueOf(session.getAttribute("state"))));

            HandlerState handlerState = request.getHandlerState();
            request.setHandlerState(handlerState.equals(HandlerState.DEFAULT) ? HandlerState.MENU : handlerState);

            ((RequestMessage) request).setMessage(update.getMessage().getText());

        } else if (update.hasCallbackQuery()) {
            request = new RequestCommand();
            telegramId = update.getCallbackQuery().getFrom().getId();

//            request.setAccount(authService.getAccount(telegramId));
            request.setChatId(update.getCallbackQuery().getMessage().getChatId());

            String data = update.getCallbackQuery().getData();
            request.setHandlerState(HandlerState.findCommand(data));
        } else {
            throw new RuntimeException("Запрос не верный");
        }
        request.setSession(session);
        return request;
    }
}
