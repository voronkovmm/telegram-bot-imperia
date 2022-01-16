package telegram.bot.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.bot.telegrambot.bot.MyBot;

import java.io.File;
import java.util.Locale;

/** Позволяет отправлять сообщения пользователю */
@Service
public class Messages {

    @Lazy
    @Autowired
    MyBot myBot;

    private final MessageSource messageSource;

    public Messages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public SendMessage sendTemplateMessage(long chatId, String templateMessage) {
        return new SendMessage(String.valueOf(chatId), getTemplateMessage(templateMessage));
    }

    public SendMessage sendTemplateMessage(long chatId, String templateMessage, Emoji smile) {
        return new SendMessage(String.valueOf(chatId), getTemplateMessage(templateMessage, smile));
    }

    public SendMessage sendMessage(long chatId, String message) {
        return new SendMessage(String.valueOf(chatId), message);
    }

    public void executeMessage(long chatId, String text) {
        try {
            myBot.execute(new SendMessage(String.valueOf(chatId), text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeAnimation(long userId, Gif gif) {

        try {
            myBot.execute(new SendAnimation(String.valueOf(userId), new InputFile(gif.toString())));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getTemplateMessage(String templateMessage) {
        return messageSource.getMessage(templateMessage, null, Locale.forLanguageTag("ru-RU"));
    }

    private String getTemplateMessage(String templateMessage, Object... args) {
        return messageSource.getMessage(templateMessage, args, Locale.forLanguageTag("ru-RU"));
    }

}