package az.vtb.iticket.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Configuration
@FieldDefaults(level = PRIVATE)
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotConfig {
    String token;
    String username;
    String chatId;
}
