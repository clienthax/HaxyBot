package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

/**
 * Created by clienthax on 9/1/2015.
 */
public class Join implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"j","join"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.OWNER;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		String joinChannel = message.split(" ")[2];
		bot.joinChannel(joinChannel);
	}
}
