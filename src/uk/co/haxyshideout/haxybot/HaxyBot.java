package uk.co.haxyshideout.haxybot;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;
import org.pircbotx.output.OutputIRC;
import org.reflections.Reflections;
import uk.co.haxyshideout.haxybot.config.Config;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.idlerpg.job.ShockyHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by clienthax on 6/11/2014.
 */
public class HaxyBot extends ListenerAdapter {

	public final List<ITaskRunner> taskRunners = new ArrayList<>();
	private final List<String> owners = new ArrayList<>();
	public String loadedModuleList = null;

	public HaxyBot() throws Exception {
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");

		loadModules();

		owners.add("clienthax");
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

	OutputIRC outputIRC = null;
	public PircBotX bot = null;

	@Override
	public void onConnect(ConnectEvent event) throws Exception {
		super.onConnect(event);
		outputIRC = event.getBot().sendIRC();
		bot = event.getBot();

		for(String owner : owners) {
			outputIRC.message(owner, "Loaded " + taskRunners.size() + " modules!");
			outputIRC.message(owner, loadedModuleList);
		}
	}

	@Override
	public void onIncomingFileTransfer(IncomingFileTransferEvent event) throws IOException {
		if(!event.getSafeFilename().endsWith(".mkv") || !System.getProperty("os.arch").equals("arm")) {
			event.respond("Transfer Denied");
			return;
		}
		File saveFile = new File("/mnt/nas/XDCC", event.getSafeFilename());

		event.acceptAndTransfer(saveFile);
	}

	@Override
	public void onInvite(InviteEvent event) {
		Config.getConfig().channels.add(event.getChannel());
		Config.saveConfig();
		outputIRC.joinChannel(event.getChannel());
	}

	@Override
	public void onNotice(NoticeEvent event) {
		if(event.getUser() != null && event.getUser().getNick().startsWith("Shocky")) {
			ShockyHandler.handleShocky(this, event.getMessage());
		}
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent event) {
		if(!event.getMessage().startsWith("^"))
			outputIRC.message("clienthax", event.getUser() + ">: " + event.getMessage());
		onMessage(event.getUser().getNick(), event.getUser().getNick(), event.getMessage());
	}

	@Override
	public void onMessage(MessageEvent event) {
		onMessage(event.getChannel().getName(), event.getUser().getNick(), event.getMessage());
	}

	public void onMessage(String channel, String sender, String message) {
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
						outputIRC.message(channel, sender+": Your access level is "+AccessLevel.PUBLIC.name()+" You need "+taskRunner.accessLevelRequired().name()+" to use this command");
				else
				if(taskRunner.accessLevelRequired() == AccessLevel.CHANNEL_OPERATOR) {
					if(owners.contains(sender))
						taskRunner.processTask(this, channel, sender, message, substring);
					else
					for(User user : bot.getUserChannelDao().getChannel(channel).getOps())
						if(user.equals(sender))
							taskRunner.processTask(this, channel, sender, message, substring);
				}

			}

		}
	}

	@Deprecated
	public void sendMessage(String channel, String s) {
		outputIRC.message(channel, s);
	}

	@Deprecated
	public void joinChannel(String joinChannel) {
		outputIRC.joinChannel(joinChannel);
	}

	@Deprecated
	public void sendAction(String channel, String s) {
		outputIRC.action(channel, s);
	}

	@Deprecated
	public void partChannel(String channel) {
	}
}
