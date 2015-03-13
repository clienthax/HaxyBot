package uk.co.haxyshideout.haxybot.modules;

import org.pircbotx.Colors;
import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

/**
 * Created by clienthax on 9/1/2015.
 */
public class CommandHelp implements ITaskRunner {

	@Override
	public String[] getCommandAliases() {
		return new String[]{"help","commands","halp"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		StringBuilder msg = new StringBuilder();
		for(ITaskRunner taskRunner : bot.taskRunners) {
			msg.append(Colors.BOLD).append(taskRunner.getClass().getSimpleName()).append(": ").append(Colors.NORMAL);
			for(String command : taskRunner.getCommandAliases())
				msg.append(command).append(" ");
		}
		bot.sendMessage(channel, msg.toString());
	}
}
