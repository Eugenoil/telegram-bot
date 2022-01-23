package client.admin.messages;

import client.model.User;
import client.roles.Roles;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Data {
    void setUsersCurrentBotState(long chat_id, Roles botState);
    Roles getUsersCurrentBotState(long chat_id);
    SendMessage createTeam(Update update);
    List<User> addUserToTeam(Update update);
    void deleteUserFromTeam(Update update, String nameUser);
    void deleteTeam(Update update, String nameTeam);
    void renameTeam(Update update, String nameTeam);
    void deleteUser(Update update, String nameUser);
    void activateUser(Update update, String nameUser);
    void changeRole();
}
