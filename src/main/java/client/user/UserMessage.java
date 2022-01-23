package client.user;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.File;
import java.io.FileReader;

public class UserMessage {


    public SendMessage helpMsg(SendMessage sendMessage, String chat_id) {
        sendMessage.setChatId(chat_id);
        String ADMIN_helpMessage = "src/main/resources/messages/user/UserHelpMessage.txt";
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

}
