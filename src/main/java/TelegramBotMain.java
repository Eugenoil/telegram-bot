import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class TelegramBotMain extends TelegramLongPollingBot {


    long durationInMillis = System.currentTimeMillis();
    long millis = durationInMillis % 1000;
    long second = (durationInMillis / 1000) % 60;
    long minute = (durationInMillis / (1000 * 60)) % 60;
    long hour = ((durationInMillis / (1000 * 60 * 60)) % 24) + 3;


    private final String botUserName;
    private final String botToken;

    @SneakyThrows
    TelegramBotMain() {
        File appConfigPath = new File("src/main/resources/app.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));
        botUserName = properties.getProperty("botUserName");
        botToken = properties.getProperty("botToken");
    }


    @Override
    public String getBotUsername() {

        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private String chat_id;

    public String getChat_id() {
        return chat_id;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        chat_id = String.valueOf(update.getMessage().getChatId());


        if (update.hasMessage() && update.getMessage().hasText()) {
            if (String.valueOf(minute).equals("10")){
                execute(SendMessage.builder()
                        .chatId(chat_id)
                        .text((hour + ":" + minute))
                        .build());
            }

        }

    }

}