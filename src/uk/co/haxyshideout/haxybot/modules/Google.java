package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.google.GoogleSearchClient;

/**
 * Created by clienthax on 9/1/2015.
 */
public class Google implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"g","google"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		if(message.length() < 8)
			return;

		try {
			String[] resultLines = GoogleSearchClient.search(substring);
			bot.sendMessage(channel, resultLines[0].replaceAll("<[^>]*>", ""));
			bot.sendMessage(channel, resultLines[1].replaceAll("<[^>]*>", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
