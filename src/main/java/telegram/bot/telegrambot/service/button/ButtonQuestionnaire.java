package telegram.bot.telegrambot.service.button;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class ButtonQuestionnaire {

    // Ряд кнопок Hello
    public InlineKeyboardMarkup rowHello() {

        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
        InlineKeyboardButton btnYes = new InlineKeyboardButton("Да");
        InlineKeyboardButton btnNo = new InlineKeyboardButton("Нет");

        // Позывные от кнопок
        btnYes.setCallbackData("btnYes");
        btnNo.setCallbackData("btnNo");

        // Ряд кнопок
        List<InlineKeyboardButton> btnRow = new ArrayList<>();
        btnRow.add(btnYes);
        btnRow.add(btnNo);

        List<List<InlineKeyboardButton>> btnList = new ArrayList<>();
        btnList.add(btnRow);

        ikm.setKeyboard(btnList);

        return ikm;
    }

    // Ряд кнопок Gender
    public InlineKeyboardMarkup rowGender() {
        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
        InlineKeyboardButton btnMan = new InlineKeyboardButton("М");
        InlineKeyboardButton btnWoman = new InlineKeyboardButton("Ж");

        // Позывные от кнопок
        btnMan.setCallbackData("btnMan");
        btnWoman.setCallbackData("btnWoman");

        // Ряд кнопок
        List<InlineKeyboardButton> btnRow = new ArrayList<>();
        btnRow.add(btnMan);
        btnRow.add(btnWoman);

        List<List<InlineKeyboardButton>> btnList = new ArrayList<>();
        btnList.add(btnRow);

        ikm.setKeyboard(btnList);

        return ikm;

    }

    // Всплывающее уведомление
    public AnswerCallbackQuery popUpNotification(String text, boolean transparent, CallbackQuery cbQuery) {
        AnswerCallbackQuery acb = new AnswerCallbackQuery();
        acb.setCallbackQueryId(cbQuery.getId());
        acb.setShowAlert(transparent);
        acb.setText(text);
        return acb;
    }
}
