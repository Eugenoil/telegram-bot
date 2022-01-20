import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

import static org.apache.tools.ant.dispatch.DispatchUtils.execute;


public class TelegramBotApplication {

    @SneakyThrows
    public static void main(String[] args) {

        TelegramBotMain telegramBotMain = new TelegramBotMain();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBotMain);

//        String chat_id = null;
//
//        while (chat_id == null) {
//            chat_id = telegramBotMain.getChat_id();
//        }
//        String finalChat_id = chat_id;
//
//
//        new Thread(() -> {
//            try {
//                Connections connections = new Connections("serviceRouter");
//
//                String les = connections.listen();
//                while (true) {
//
//                    execute(SendMessage.builder()
//                            .chatId("436889471")
//                            .text("update.getMessage().getText()")
//                            .build());
//                    System.exit(0);
//
//
//
//                    execute(SendMessage.builder()
//                            .chatId(finalChat_id)
//                            .text(les)
//                            .build());
//
//                    connections.sendResponse("Петя");
//                }
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }).start();
    }

}
