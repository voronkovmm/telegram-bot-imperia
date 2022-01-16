package telegram.bot.telegrambot.bot;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.telegrambot.repository.BotState;
import telegram.bot.telegrambot.repository.Lists;
import telegram.bot.telegrambot.service.button.ButtonQuestionnaire;
import telegram.bot.telegrambot.workspace.Menu;
import telegram.bot.telegrambot.workspace.Questionnaire;

@Slf4j
@Setter
public class MyBot extends TelegramWebhookBot {

    private String botToken;
    private String botUsername;
    private String botPath;

    @Autowired
    Questionnaire questionnaire;
    @Autowired
    ButtonQuestionnaire btnQuest;
    @Autowired
    Lists lists;
    @Autowired
    Menu menu;

    public MyBot() {
        super();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        CallbackQuery clbQuery;

        if (update.hasCallbackQuery()) {
            log.info("New callbackQuery from User: {}, userId: {}, text: {}",
                    update.getCallbackQuery().getFrom().getUserName(),
                    update.getCallbackQuery().getFrom().getId(),
                    update.getCallbackQuery().getData());

            long userId = update.getCallbackQuery().getFrom().getId();
            if (checkUserState(userId))
                return menu.handlerMenu(userId, update);
            else
                return questionnaire.fillQuestionnaire(userId, update);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText() && update.getCallbackQuery() == null) {
            log.info("New message from User: {}, userId: {}, chatId: {}, text: {}",
                    message.getFrom().getUserName(),
                    message.getFrom().getId(),
                    message.getChatId(),
                    message.getText());

            long userId = message.getFrom().getId();
            if (checkUserState(userId))
                return menu.handlerMenu(userId, update);
            else
                return questionnaire.fillQuestionnaire(userId, update);
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    private boolean checkUserState(long userId) {
        if (lists.getUsersList().containsKey(userId)) {
            BotState state = lists.getStatusList().get(userId);
            return state.equals(BotState.FINISHED);
        }
        return false;
    }
}
