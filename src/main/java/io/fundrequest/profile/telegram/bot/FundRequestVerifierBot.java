package io.fundrequest.profile.telegram.bot;

import io.fundrequest.profile.telegram.service.TelegramVerificationService;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.api.objects.ChatMember;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class FundRequestVerifierBot extends AbilityBot {

    private final TelegramVerificationService telegramVerificationService;

    public FundRequestVerifierBot(final String botToken,
                                  final String botUsername,
                                  final TelegramVerificationService telegramVerificationService
    ) {
        super(botToken, botUsername);
        this.telegramVerificationService = telegramVerificationService;
    }

    @Override
    public int creatorId() {
        return 302351791;
    }

    public Ability verify() {
        return Ability.builder()
                .name("verify")
                .info("verifies your connection with FundRequest")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    final GetChatMember getChatMember = new GetChatMember();
                    getChatMember.setChatId("-247444321");
                    getChatMember.setUserId(ctx.user().id());
                    try {
                        ChatMember execute = this.execute(getChatMember);
                        if (execute.getStatus().equalsIgnoreCase("member")) {

                            if (ctx.arguments().length > 0) {
                                if (telegramVerificationService.verify(ctx.user().username(), ctx.firstArg())) {
                                    silent.send("Nice, " + ctx.user().username() + "! We've successfully verified you as member of our community!", ctx.chatId());
                                } else {
                                    silent.send("We're sorry, " + ctx.user().username() + "! We found you as a member of our community, but your secret appears to be wrong!", ctx.chatId());
                                }
                            } else {
                                silent.send("We're sorry, " + ctx.user().username() + "! We found you as a member of our community, but you did not add your secret! ex. /verify mySecret", ctx.chatId());

                            }
                        } else {
                            silent.send("We're sorry, " + ctx.user().username() + "! You were not found as a member of the fundrequest channel. (" + execute.getStatus() + ")", ctx.chatId());
                        }
                    } catch (final Exception ex) {
                        silent.send("We're sorry, " + ctx.user().username() + "! You were not found as a member of the fundrequest channel.", ctx.chatId());
                    }
                })
                .build();
    }
}
