package uk.co.haxyshideout.haxybot.modules.interfaces;

import uk.co.haxyshideout.haxybot.HaxyBot;

/**
 * Created by clienthax on 9/1/2015.
 */
public interface ITaskRunner {

	String[] getCommandAliases();

	AccessLevel accessLevelRequired();

	@SuppressWarnings("UnusedParameters")
	void processTask(HaxyBot bot, String channel, String sender, String message, String substring);

}
