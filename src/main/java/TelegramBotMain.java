import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

@EqualsAndHashCode(callSuper = true)
@Data
public class TelegramBotMain extends TelegramLongPollingBot {

    private final String botUserName;
    private final String botToken;

    @SneakyThrows
    TelegramBotMain() {
//        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        File appConfigPath = new File( "src/main/resources/app.properties");
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


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage sendMessage = new SendMessage();

            String message_text = update.getMessage().getText();
            String chat_id = String.valueOf(update.getMessage().getChatId());

            switch (message_text) {
                default:{

                    execute(SendMessage.builder()
                            .chatId(chat_id)
                            .text(update.getMessage().getText())
                            .build());
                    break;
                }


            }
        }
    }
}