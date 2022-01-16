package telegram.bot.telegrambot.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gif {
    WILLIAM_WALLACE_HEAD("https://media.giphy.com/media/ToMjGpnsDvb5S4Pl23S/giphy-downsized-large.gif"),
    PIGEON("https://media.giphy.com/media/8YRG6XneeNRgrgxsMg/giphy.gif");

    private final String link;

    @Override
    public String toString() {
        return link;
    }
}
