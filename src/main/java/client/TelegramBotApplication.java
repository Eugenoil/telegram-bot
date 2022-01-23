package client;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class TelegramBotApplication {

    @SneakyThrows
    public static void main(String[] args) {

        TelegramBotMain telegramBotMain = new TelegramBotMain();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBotMain);

    }

}
