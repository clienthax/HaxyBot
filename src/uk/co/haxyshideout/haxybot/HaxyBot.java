package uk.co.haxyshideout.haxybot;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;
import org.reflections.Reflections;
import uk.co.haxyshideout.haxybot.config.Config;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.idlerpg.job.ShockyHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by clienthax on 6/11/2014.
 */
public class HaxyBot extends PircBot {

	public final List<ITaskRunner> taskRunners = new ArrayList<>();
	private final List<String> owners = new ArrayList<>();
	public String loadedModuleList = null;

	public HaxyBot(Config.BotConfig config) throws IrcException, IOException {
		this.setName(config.username);
		this.setVerbose(true);
		this.startIdentServer();
		this.connect(config.server);
		this.identify(config.password);
		for(String channel : config.channels)
			this.joinChannel(channel);

		loadModules();

		owners.add("clienthax");

		for(String owner : owners) {
			sendMessage(owner, "Loaded " + taskRunners.size() + " modules!");
			sendMessage(owner, loadedModuleList);
		}
	}

	public void loadModules() {
		taskRunners.clear();
		loadedModuleList = "";

		Reflections reflections = new Reflections("uk.co.haxyshideout.haxybot.modules");
		Set<Class<? extends ITaskRunner>> modules = reflections.getSubTypesOf(ITaskRunner.class);
		for(Class runnerClass : modules) {
			try {
				taskRunners.add((ITaskRunner) runnerClass.newInstance());
				loadedModuleList = loadedModuleList +runnerClass.getSimpleName() +" ";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {
		Config.getConfig().channels.add(channel);
		Config.saveConfig();
		joinChannel(channel);
	}

	@Override
	public void onNotice(String sender, String login, String hostname, String target, String message) {
		if(sender.startsWith("Shocky")) {
			ShockyHandler.handleShocky(this, message);
		}
	}

	@Override
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		onMessage(sender, sender, login, hostname, message);
	}

	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(!message.startsWith("^"))
			return;

		String task = message.split(" ")[0].substring(1);
		String substring = message.substring(message.indexOf(" ")+1);

		for(ITaskRunner taskRunner : taskRunners)
		{
			if(Arrays.asList(taskRunner.getCommandAliases()).contains(task))
			{
				if(taskRunner.accessLevelRequired() == AccessLevel.PUBLIC)
					taskRunner.processTask(this, channel, sender, message, substring);
				else
				if(taskRunner.accessLevelRequired() == AccessLevel.OWNER)
					if(owners.contains(sender)) {
						taskRunner.processTask(this, channel, sender, message, substring);
						return;
					} else
						sendMessage(channel, sender+": Your access level is "+AccessLevel.PUBLIC.name()+" You need "+taskRunner.accessLevelRequired().name()+" to use this command");
				else
				if(taskRunner.accessLevelRequired() == AccessLevel.CHANNEL_OPERATOR) {
					if(owners.contains(sender))
						taskRunner.processTask(this, channel, sender, message, substring);
					else
					for(User user : getUsers(channel))
						if(user.equals(sender))
							if(user.isOp())
								taskRunner.processTask(this, channel, sender, message, substring);
							else
								sendMessage(channel, sender+": Your access level is "+AccessLevel.PUBLIC.name()+" You need "+taskRunner.accessLevelRequired().name()+" to use this command");
				}

			}

		}
	}




}
