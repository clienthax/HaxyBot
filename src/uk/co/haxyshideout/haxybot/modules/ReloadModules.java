package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

/**
 * Created by clienthax on 9/1/2015.
 */
public class ReloadModules implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"reload"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.OWNER;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.loadModules();
		bot.sendMessage(channel, "Reloaded all modules, total " + bot.taskRunners.size());
		bot.sendMessage(channel, "Modules: "+bot.loadedModuleList);
	}
}
