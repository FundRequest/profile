package io.fundrequest.profile.telegram.config;

import io.fundrequest.profile.telegram.bot.FundRequestVerifierBot;
import io.fundrequest.profile.telegram.service.TelegramVerificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Configuration
public class TelegramConfiguration {

    @Bean
    public TelegramBotsApi provideTelegramBotsApi(@Value("${io.fundrequest.telegram.bot.name}")
                                                          String botName,
                                                  @Value("${io.fundrequest.telegram.bot.apikey}")
                                                          String botApiKey,
                                                  TelegramVerificationService telegramVerificationService) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(new FundRequestVerifierBot(botApiKey, botName, telegramVerificationService));
        return telegramBotsApi;
    }
}
