package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

/**
 * Created by clienthax on 9/1/2015.
 */
public class Slap implements ITaskRunner {

	private final Random random = new Random();
	private List<String> slapsList = null;

	public Slap() {
		loadSlap();
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"slap"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.sendMessage(channel, randomSlap(sender, substring));
	}

	private String randomSlap(String sender, String target) {
		int index = random.nextInt(slapsList.size());
		if(target.equalsIgnoreCase("clienthax"))
		{
			target = sender;
			sender = "HaxyBot";
		}

		return sender+" slaps "+target+" with "+slapsList.get(index);
	}

	private void loadSlap() {
		try {
			slapsList = Files.readAllLines(new File("./resources/slaps.txt").toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
