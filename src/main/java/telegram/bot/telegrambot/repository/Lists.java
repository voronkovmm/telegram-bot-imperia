package telegram.bot.telegrambot.repository;

import lombok.*;
import org.springframework.context.annotation.Configuration;
import telegram.bot.telegrambot.user.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Configuration
public class Lists {

    private Map<Long, User> usersList = new HashMap<>();
    private Map<Long, BotState> statusList = new HashMap<>();
}
