package telegram.bot.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import telegram.bot.telegrambot.user.User;

@Repository
public interface Users extends CrudRepository<User, Long> {


}
