package com.zera.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Application implements CommandLineRunner {

	static final int RECONNECT_PAUSE = 10000;

	@Autowired
	private MarketplaceBot marketplaceBot;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

		try {
			telegramBotsApi.registerBot(marketplaceBot);
		} catch (TelegramApiRequestException e) {
			System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
			try {
				Thread.sleep(RECONNECT_PAUSE);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				return;
			}
		}
	}
}
