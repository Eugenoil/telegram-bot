package client;

import client.admin.messages.AdminHandler;
import client.model.User;
import client.roles.Roles;
import client.settings.SettingsKeyBord;
import client.user.UserMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.management.relation.Role;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class TelegramBotMain extends TelegramLongPollingBot {
    private final SettingsKeyBord settingsKeyBord = new SettingsKeyBord();
    private Map<Long, Roles> userMap = new TreeMap<>();

    private String text;
    private final String botUserName;
    private final String botToken;
    private final String httpBot;

    AdminHandler adminHandler = new AdminHandler();
    //    AdminHandler adminMsg = new AdminHandler();
    UserMessage userMsg = new UserMessage();


    @SneakyThrows
    TelegramBotMain() {
        File appConfigPath = new File("src/main/resources/app.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));
        botUserName = properties.getProperty("botUserName");
        botToken = properties.getProperty("botToken");
        httpBot = properties.getProperty("httpBot");
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
        handlerMessage(update, update.getMessage());
    }

    private void handlerMessage(Update update, Message message) throws TelegramApiException {
        Long chat_id = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        Roles rolesState;

        if (message.getText().equals("/start")) {
            execute(selectRoles(sendMessage, String.valueOf(chat_id)));
        }

        if (update.hasMessage()) {
            if (message.getText().equals("ADMIN")) {


                userMap.put(chat_id,Roles.ADMIN);


                execute(SendMessage.builder()
                        .chatId(String.valueOf(chat_id))
                        .text("Теперь вы админ \uD83D\uDC24")
                        .build());

                execute(SendMessage.builder()
                        .chatId(String.valueOf(chat_id))
                        .text(String.valueOf(adminHandler.helpMsg(sendMessage, chat_id)))
                        .build());
            }

        }
        if (update.getMessage().getText().equals("/createTeam") && update.getMessage().getChatId().equals(userMap.get(chat_id))) {
            execute(SendMessage.builder()
                    .chatId(String.valueOf(chat_id))
                    .text("Введите имя команды")
                    .build());
            execute(adminHandler.createTeam(update));
        }


    }

    private SendMessage selectRoles(SendMessage sendMessage, String chat_id) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        settingsKeyBord.keyBoardMarkupSettings(keyboardMarkup);

        for (Roles roles : Roles.values()) {
            row.add(roles.name());
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);


        sendMessage.setChatId(chat_id);
        sendMessage.setText("Выбери роль");
        sendMessage.setReplyMarkup(keyboardMarkup);

        return sendMessage;
    }

    @SneakyThrows
    public void createTeam(Update update, String chat_id) {
        SendMessage create = new SendMessage();
        create.setChatId(chat_id);
        create.setText("Введите название команды");
        execute(create);
        /*
        Логика создания
         */
        SendMessage team = new SendMessage();
        Update update1 = new Update();
        String teams = update1.getMessage().getText();
        team.setChatId(chat_id);
        team.setText("Команда " + teams + " Создана");
        execute(team);

        SendMessage addUsers = new SendMessage();
        addUsers.setChatId(chat_id);
        addUsers.setText("Введите ники участников через пробел в формате @ivan");
        execute(addUsers);
        /*
        Логика создания
         */
        SendMessage sendMessage = new SendMessage();
        Update update2 = new Update();
        String teamUser = update2.getMessage().getText();
        sendMessage.setChatId(chat_id);
        sendMessage.setText("Команда " + teams + " с учасниками " + teamUser + " создана");
        execute(sendMessage);

    }


}



