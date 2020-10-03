package bot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author DerEingerostete
 * @since 1.0-SNAPSHOT
 */
public class Bot extends ListenerAdapter {
    private final String PREFIX;
    private JDA jda;

    /**
     * Construct a new bot with token and prefix
     *
     * NOTE: To create your own Discord bot, you need a Discord application
     * https://discord.com/developers/applications
     *
     * @param token the discord bot token
     * @param prefix the prefix the bot will listen to
     * @throws LoginException
     */
    public Bot(final String token, final String prefix) throws LoginException {
        this.PREFIX = prefix;
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("people"));
        builder.setAutoReconnect(true);
        builder.addEventListeners(this);

        jda = builder.build();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        TextChannel channel = event.getChannel();
        if (!event.getAuthor().isBot() && message != null
                && message.getContentRaw().startsWith(PREFIX)) return;

        ArrayList<String> arguments =
                new ArrayList<String>(Arrays.asList(message.getContentRaw().split(" ")));
        String command = arguments.get(0).toLowerCase().substring(1);

        switch (command) {
            case "hello" -> channel.sendMessage("Hello my friend").complete();
            case "bye"   -> channel.sendMessage("See you next time!").complete();
            case "alive" -> channel.sendMessage("Its alive!").complete();
        }
    }
}
