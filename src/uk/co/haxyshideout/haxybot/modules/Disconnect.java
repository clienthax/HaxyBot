package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

/**
 * Created by clienthax on 9/1/2015.
 */
public class Disconnect implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"dc","disconnect","quit"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.OWNER;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.disconnect();
	}
}
