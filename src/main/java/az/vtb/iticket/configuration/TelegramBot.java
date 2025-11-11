package az.vtb.iticket.configuration;

import az.vtb.iticket.exception.TelegramException;
import az.vtb.iticket.model.request.CreateEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static az.vtb.iticket.exception.ErrorMessage.TELEGRAM_SEND_MESSAGE_FAILED;
import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfig config;
    private static final String COMMAND_CHAT_ID = "/chatId";

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!hasTestMessage(update)) return;

        var messageText = update.getMessage().getText().trim();
        long chatId = update.getMessage().getChatId();
        log.info("Received message: {} from chat: {}", messageText, chatId);
        handleIncomingCommand(chatId, messageText);
    }

    public void sendEventNotification(CreateEventRequest eventRequest) {
        var message = formatEventNotification(eventRequest);
        sendMessage(Long.parseLong(config.getChatId()), message);
    }

    private boolean hasTestMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private void handleIncomingCommand(long chatId, String messageText) {
        if (COMMAND_CHAT_ID.equalsIgnoreCase(messageText)) {
            sendMessage(chatId, "Your Chat ID: " + chatId);
        } else {
            sendMessage(chatId, "Unknown command. Try /chatId");
        }
    }

    private String formatEventNotification(CreateEventRequest request) {
        return String.format(
                """
                        *New Event*
                        📌 *Name:* %s
                        📝 *Description:* %s
                        📍 *Location:* %s
                        🎉 *Category:* %s
                        🕐 *Start Time:* %s
                        """,
                request.getName(),
                request.getDescription(),
                request.getLocation(),
                request.getCategory(),
                request.getStartTime().format(ofPattern(DATE_TIME_PATTERN))
        );
    }

    private void sendMessage(Long chatId, String text) {
        var message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .parseMode("Markdown")
                .build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new TelegramException(TELEGRAM_SEND_MESSAGE_FAILED.getCode());
        }
    }
}