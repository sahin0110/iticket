package az.vtb.iticket.configuration;

import az.vtb.iticket.exception.TelegramException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static az.vtb.iticket.exception.ErrorMessage.TELEGRAM_REGISTER_BOT_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotInitializer {
    private final TelegramBot telegramBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            throw new TelegramException(TELEGRAM_REGISTER_BOT_FAILED.getCode());
        }
    }
}