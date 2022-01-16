package telegram.bot.telegrambot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "telegram.bot.telegrambot.repository")
public class SpringDataConfig {
}
