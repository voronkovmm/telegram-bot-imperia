package telegram.bot.telegrambot.workspace;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.telegrambot.repository.BotState;
import telegram.bot.telegrambot.repository.Lists;
import telegram.bot.telegrambot.repository.Users;
import telegram.bot.telegrambot.service.Messages;
import telegram.bot.telegrambot.service.button.ButtonMenu;
import telegram.bot.telegrambot.service.button.ButtonQuestionnaire;
import telegram.bot.telegrambot.user.User;

@NoArgsConstructor
@Service
public class Questionnaire {

    @Autowired
    Lists lists;
    @Autowired
    Messages messages;
    @Autowired
    ButtonQuestionnaire btnQuest;
    @Autowired
    ButtonMenu btnMenu;
    @Autowired
    Menu menu;
    @Autowired
    Users users;

    public BotApiMethod<?> fillQuestionnaire(long userId, Update update) {

        if (!lists.getUsersList().containsKey(userId)) {
            lists.getUsersList().put(userId, new User());
            lists.getStatusList().put(userId, BotState.HELLO);
        }

        BotState state = lists.getStatusList().get(userId);
        SendMessage sendMessage;

        switch (state) {
            case HELLO:
                return caseHello(userId);
            case ASK_NAME:
                return caseAskName(userId, update);
            case ASK_AGE:
                return caseAskAge(userId, update);
            case ASK_GENDER:
                return caseAskGender(userId, update);
            case REPLY_GENDER:
                return caseReplyGender(userId, update);
            case FINISHED:
                return menu.handlerMenu(userId, update);
            default:
                sendMessage = messages.sendMessage(userId, "Кажется у нас неполадки");
        }

        return sendMessage;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private SendMessage caseHello(long userId) {
        SendMessage sendMessage = messages.sendTemplateMessage(userId, "questionnaire.Hello");
        sendMessage.setReplyMarkup(btnQuest.rowHello());
        lists.getStatusList().put(userId, BotState.ASK_NAME);
        return sendMessage;
    }

    private BotApiMethod<?> caseAskName(long userId, Update update) {
        SendMessage sendMessage;

        if (update.hasMessage()) {
            sendMessage = messages.sendMessage(userId, "Эй! Ты из новоприбывших ? \nНажми на кнопку, ничего не слышно");
            sendMessage.setReplyMarkup(btnQuest.rowHello());
            return sendMessage;
        }

        if (update.hasCallbackQuery()) {
            if ("btnNo".equals(update.getCallbackQuery().getData())) {
                return btnQuest.popUpNotification("Не трать моё время !", false, update.getCallbackQuery());
            }
            else if ("btnYes".equals(update.getCallbackQuery().getData())) {
                sendMessage = messages.sendTemplateMessage(userId, "questionnaire.askName");
            }
            else {
                sendMessage = messages.sendTemplateMessage(userId, "questionnaire.Hello");
                sendMessage.setReplyMarkup(btnQuest.rowHello());
                return sendMessage;
            }

            lists.getStatusList().put(userId, BotState.ASK_AGE);
            return sendMessage;
        }


        return null;
    }

    private BotApiMethod<?> caseAskAge(long userId, Update update) {
        if (update.hasCallbackQuery())
            return messages.sendMessage(userId, "Не отвлекайся! Я спросил твоё имя");

        lists.getUsersList().get(userId).setName(update.getMessage().getText());
        lists.getStatusList().put(userId, BotState.ASK_GENDER);

        if (isNumeric(update.getMessage().getText())) {
            messages.executeMessage(userId, "Имя из цифр ? Интересно");
        }

        return messages.sendTemplateMessage(userId, "questionnaire.askAge");
    }

    private BotApiMethod<?> caseAskGender(long userId, Update update) {
        if (update.hasCallbackQuery())
            return messages.sendMessage(userId, "Не отвлекайся! Я спросил твой возраст");

        if (!isNumeric(update.getMessage().getText()))
            return messages.sendMessage(userId, "Введи возраст числом!");

        lists.getUsersList().get(userId).setAge(Integer.parseInt(update.getMessage().getText()));
        lists.getStatusList().put(userId, BotState.REPLY_GENDER);

        SendMessage sendMessage = messages.sendTemplateMessage(userId, "questionnaire.askGender");
        sendMessage.setReplyMarkup(btnQuest.rowGender());

        return sendMessage;
    }

    private BotApiMethod<?> caseReplyGender(long userId, Update update) {
        SendMessage sendMessage = null;

        if (update.hasMessage()) {
            sendMessage = messages.sendMessage(userId, "Выбери пол нажав на кнопку" + "\nКакой твой пол ?");
            sendMessage.setReplyMarkup(btnQuest.rowGender());
            return sendMessage;
        }

        String gender = "";
        if (update.hasCallbackQuery()) {
            String button = update.getCallbackQuery().getData();

            if ("btnMan".equals(button)) {
                gender = "Мужской";
                sendMessage = messages.sendMessage(userId,
                        "\n" + lists.getUsersList().get(userId).getName() +
                                ", воспользуйся меню");
                sendMessage.setReplyMarkup(btnMenu.rowMenu());
            }
            else if ("btnWoman".equals(button)) {
                gender = "Женский";
                sendMessage = messages.sendMessage(userId,
                        "\n" + lists.getUsersList().get(userId).getName() +
                                ", воспользуйся меню");
                sendMessage.setReplyMarkup(btnMenu.rowMenu());
            }
            else {
                sendMessage = messages.sendTemplateMessage(userId, "questionnaire.askGender");
                sendMessage.setReplyMarkup(btnQuest.rowGender());
                return sendMessage;
            }
        }

        lists.getUsersList().get(userId).setUserid(userId);
        lists.getUsersList().get(userId).setRealUserName(update.getCallbackQuery().getFrom().getUserName());
        lists.getUsersList().get(userId).setRealFirstName(update.getCallbackQuery().getFrom().getFirstName());
        lists.getUsersList().get(userId).setRealLastName(update.getCallbackQuery().getFrom().getLastName());
        lists.getUsersList().get(userId).setGender(gender);

        lists.getStatusList().put(userId, BotState.FINISHED);

        users.save(lists.getUsersList().get(userId));

        return sendMessage;
    }
}
