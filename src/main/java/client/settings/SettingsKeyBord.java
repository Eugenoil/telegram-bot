package client.settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class SettingsKeyBord {
    public void keyBoardMarkupSettings(ReplyKeyboardMarkup keyboardMarkup) {
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
    }
}
