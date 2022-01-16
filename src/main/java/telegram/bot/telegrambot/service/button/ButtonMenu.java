package telegram.bot.telegrambot.service.button;

import com.vdurmont.emoji.EmojiParser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.bot.telegrambot.service.Emoji;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class ButtonMenu {

    // Основное Меню
    public ReplyKeyboardMarkup rowMenu() {

        ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
        rkm.setSelective(true);
        rkm.setResizeKeyboard(true);
        rkm.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row1.add("click");
        row2.add("\ud83e\ude96 Мои данные");
        row3.add("\ud83d\udd4a Обратная связь");

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        rkm.setKeyboard(keyboard);
        return rkm;
    }

    // Кнопка "изменить данные анкеты"
    public InlineKeyboardMarkup rowChangeProfile() {
        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
        InlineKeyboardButton btnChange = new InlineKeyboardButton("Изменить");
        InlineKeyboardButton btnDelete = new InlineKeyboardButton("Удалить");

        // Установить позывные для кнопок
        btnChange.setCallbackData("btnChangeProfile");
        btnDelete.setCallbackData("btnDeleteProfile");

        // Ряд кнопок
        List<InlineKeyboardButton> btnRow = new ArrayList<>();
        btnRow.add(btnChange);
        btnRow.add(btnDelete);

        List<List<InlineKeyboardButton>> btnList = new ArrayList<>();
        btnList.add(btnRow);

        ikm.setKeyboard(btnList);
        return ikm;
    }
}
