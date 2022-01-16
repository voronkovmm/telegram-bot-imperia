package telegram.bot.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.telegrambot.bot.MyBot;

@RestController
public class Controller {

    @Autowired
    private MyBot myBot;

    @PostMapping("/")
    public BotApiMethod<?> handlerMessages(@RequestBody Update update) {
        return myBot.onWebhookUpdateReceived(update);
    }
}
