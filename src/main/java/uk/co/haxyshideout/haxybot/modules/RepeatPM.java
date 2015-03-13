package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

/**
 * Created by clienthax on 13/3/2015.
 */
public class RepeatPM implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"repeatpm"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.OWNER;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		String[] parts = substring.split(" ");
		String user = parts[0];
		String tosend = substring.substring(substring.indexOf(" ")+1);
		bot.sendMessage(user, tosend);
	}
}
