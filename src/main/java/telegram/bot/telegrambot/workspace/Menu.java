package telegram.bot.telegrambot.workspace;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.telegrambot.repository.BotState;
import telegram.bot.telegrambot.repository.Lists;
import telegram.bot.telegrambot.service.Emoji;
import telegram.bot.telegrambot.service.Gif;
import telegram.bot.telegrambot.service.Messages;
import telegram.bot.telegrambot.service.button.ButtonMenu;
import telegram.bot.telegrambot.service.button.ButtonQuestionnaire;
import telegram.bot.telegrambot.user.User;

@NoArgsConstructor
@Service
public class Menu {

    @Autowired
    Messages messages;
    @Autowired
    Lists lists;
    @Autowired
    ButtonMenu btnMenu;

    public BotApiMethod<?> handlerMenu(long userId, Update update) {

        if (update.hasMessage()) {
            switch (update.getMessage().getText()) {
                case "click":
                    return caseClick(userId);
                case "\ud83e\ude96 Мои данные":
                    return caseMyData(userId);
                case "\ud83d\udd4a Обратная связь":
                    return caseOs(userId);
                default:
                    SendMessage message = messages.sendMessage(userId, "Воспользуйся главным меню");
                    message.setReplyMarkup(btnMenu.rowMenu());
                    return message;
            }
        }

        if (update.hasCallbackQuery()) {
            switch (update.getCallbackQuery().getData()) {
                case "btnChangeProfile":
                    return caseChangeProfile(userId);
                case "btnDeleteProfile":
                    return caseDeleteProfile(userId);
                case "btnChangeName":
                    return messages.sendMessage(userId, "Введи новое имя");
            }
        }

        return null;
    }

    private BotApiMethod<?> caseClick(long userId) {
        messages.executeAnimation(userId, Gif.WILLIAM_WALLACE_HEAD);
        return messages.sendMessage(userId, "Эта кнопка могла бы реализовать твой функционал");
    }

    private BotApiMethod<?> caseMyData(long userId) {
        User user = lists.getUsersList().get(userId);

        SendMessage message = messages.sendMessage(userId, user.toString());
        message.setReplyMarkup(btnMenu.rowChangeProfile());
        System.out.println("сообщение отправляется");
        return message;
    }

    private BotApiMethod<?> caseOs(long userId) {
        messages.executeAnimation(userId, Gif.PIGEON);
        return messages.sendMessage(userId, "instagram: voronkovmm \n" + "telegram: voronkovmm");
    }

    private BotApiMethod<?> caseChangeProfile(long userId) {
        lists.getUsersList().put(userId, new User());
        lists.getStatusList().put(userId, BotState.ASK_AGE);
        return messages.sendTemplateMessage(userId, "questionnaire.askName");
    }

    private BotApiMethod<?> caseDeleteProfile(long userId) {
        lists.getStatusList().remove(userId);
        lists.getUsersList().remove(userId);
        return messages.sendMessage(userId, "Данные были сброшены");
    }


}
