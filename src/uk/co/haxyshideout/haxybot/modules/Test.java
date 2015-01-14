package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

/**
 * Created by clienthax on 9/1/2015.
 */
public class Test implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"test"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.OWNER;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		/*
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, 10);

		IdleRpg idleRpg = new IdleRpg(bot, channel, calendar.getTime());
		try {
			new Thread(idleRpg).start();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		bot.sendMessage(channel, ">");
	}
}
