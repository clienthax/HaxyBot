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
public class Dance implements ITaskRunner {

	private final Random random = new Random();
	private List<String> danceList = null;

	public Dance() {
		loadDance();
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"dance"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.sendAction(channel, "dances around "+substring+" throwing "+randomDance());
	}

	private String randomDance() {
		int index = random.nextInt(danceList.size());
		return danceList.get(index);
	}

	private void loadDance() {
		try {
			danceList = Files.readAllLines(new File("./resources/dances.txt").toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
