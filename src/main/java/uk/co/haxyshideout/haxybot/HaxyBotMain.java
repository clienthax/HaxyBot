package uk.co.haxyshideout.haxybot;


import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import uk.co.haxyshideout.haxybot.config.Config;

/**
 * Created by clienthax on 6/11/2014.
 */
public class HaxyBotMain {

	public static void main(String[] args) throws Exception {
		Config.BotConfig config = Config.getConfig();

		Configuration.Builder builder = new Configuration.Builder()
				.setName(config.username)
				.addServer(config.server)
				.setNickservPassword(config.password)
				.setIdentServerEnabled(false)
				.setAutoReconnect(true)
				.addListener(new HaxyBot());
		for(String channel : config.channels)
			builder = builder.addAutoJoinChannel(channel);

		PircBotX bot = new PircBotX(builder.buildConfiguration());
		bot.startBot();

	}
}
