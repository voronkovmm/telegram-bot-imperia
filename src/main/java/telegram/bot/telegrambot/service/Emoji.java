package telegram.bot.telegrambot.service;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emoji {
    ENVELOPE(EmojiParser.parseToUnicode(":envelope:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
