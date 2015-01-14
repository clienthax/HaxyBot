package uk.co.haxyshideout.haxybot;


import uk.co.haxyshideout.haxybot.config.Config;

/**
 * Created by clienthax on 6/11/2014.
 */
public class HaxyBotMain {

	public static void main(String[] args) throws Exception {
		Config.BotConfig config = Config.getConfig();
		new HaxyBot(config);
	}
}
