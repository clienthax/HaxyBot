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
public class BofhExcuse implements ITaskRunner {

	private final Random random = new Random();
	private List<String> bofhList = null;

	public BofhExcuse() {
		loadBofh();
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"bofh","bofhexcuse"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.sendMessage(channel, randomBofh());
	}

	private String randomBofh() {
		int index = random.nextInt(bofhList.size());
		return bofhList.get(index);
	}

	private void loadBofh() {
		try {
			bofhList = Files.readAllLines(new File("./resources/bofh.txt").toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
