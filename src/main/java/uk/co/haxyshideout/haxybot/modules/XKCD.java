package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.xkcd.XkcdClient;

/**
 * Created by clienthax on 11/1/2015.
 */
public class XKCD implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"xkcd"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		try {
			String response = XkcdClient.findComic(substring);
			bot.sendMessage(channel, response);
		} catch (Exception e) {
			bot.sendMessage(channel, "welp, that didnt work: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
