package telegram.bot.telegrambot.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String botToken;
    private String botUsername;
    private String botPath;

    @Bean
    public MyBot createMyBot() {
        MyBot myBot = new MyBot();
        myBot.setBotUsername(botUsername);
        myBot.setBotToken(botToken);
        myBot.setBotPath(botPath);
        return myBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
