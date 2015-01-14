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
public class Lover implements ITaskRunner {

	private final Random random = new Random();
	private List<String> loverList = null;

	public Lover() {
		loadLover();
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"lover"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.sendMessage(channel, randomLover(sender, substring));
	}

	private String randomLover(String sender, String target) {
		int index = random.nextInt(loverList.size());
		return sender+" cheers up "+target+" with "+loverList.get(index);
	}

	private void loadLover() {
		try {
			loverList = Files.readAllLines(new File("./resources/lover.txt").toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
