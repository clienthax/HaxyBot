package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.urban.Response;
import uk.co.haxyshideout.haxybot.utils.urban.UrbanDictionarySearchClient;

/**
 * Created by clienthax on 9/1/2015.
 */
public class UrbanDictionary implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"ud","urban"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		try {
			Response response = UrbanDictionarySearchClient.getDefinition(message.substring(message.indexOf(" ", message.indexOf(" ") + 1)));
			bot.sendMessage(channel, response.getDefinitions().get(0).getDefinition());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
