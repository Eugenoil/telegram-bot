package client.admin.messages;

import client.model.User;
import client.roles.Roles;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminHandler implements Data {
    private Map<Long, Roles> botStates = new HashMap<>();

    public SendMessage helpMsg(SendMessage sendMessage, long chat_id) {
        sendMessage.setChatId(String.valueOf(chat_id));
        String ADMIN_helpMessage = "src/main/resources/messages/admin/AdminHelpMessage.txt";
        sendMessage.setText(sendFileMessage(new File(ADMIN_helpMessage)));
        return sendMessage;
    }

    @SneakyThrows
    public @NonNull String sendFileMessage(File fileMessage) {
        int line;
        FileReader fileReader = new FileReader(String.valueOf(fileMessage));
        StringBuilder sb = new StringBuilder();
        while ((line = fileReader.read()) != -1) {
            sb.append((char) line);
        }
        fileReader.close();
        return String.valueOf(sb);
    }

    @Override
    public void setUsersCurrentBotState(long chat_id, Roles botState) {
        botStates.put(chat_id, botState);
    }

    @Override
    public Roles getUsersCurrentBotState(long chat_id) {
        Roles botState = botStates.get(chat_id);
        if (botState == null) {
            botState = Roles.ADMIN;
        }
        return botState;
    }


    @Override
    public SendMessage createTeam(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Команда создана");
        return sendMessage;
    }

    @Override
    public List<User> addUserToTeam(Update update) {
        return null;
    }

    @Override
    public void deleteUserFromTeam(Update update, String nameUser) {

    }

    @Override
    public void deleteTeam(Update update, String nameTeam) {

    }

    @Override
    public void renameTeam(Update update, String nameTeam) {

    }

    @Override
    public void deleteUser(Update update, String nameUser) {

    }

    @Override
    public void activateUser(Update update, String nameUser) {

    }

    @Override
    public void changeRole() {

    }
}
