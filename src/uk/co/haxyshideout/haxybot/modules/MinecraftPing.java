package uk.co.haxyshideout.haxybot.modules;

import org.jibble.pircbot.Colors;
import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.minecraft.MinecraftServerPingClient;
import uk.co.haxyshideout.haxybot.utils.SimpleFunctions;

/**
 * Created by clienthax on 9/1/2015.
 */
public class MinecraftPing implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"pingmc"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		String server = message.substring(message.indexOf(" ", message.indexOf(" ") + 1)+1);
		if(!server.contains(":"))
			server = server+":25565";
		String[] parts = server.split(":");

		MinecraftServerPingClient minecraftServer = new MinecraftServerPingClient(parts[0], Integer.parseInt(parts[1]));
		try {
			MinecraftServerPingClient.IServerStatus statusResponse = minecraftServer.ping();

			bot.sendMessage(channel, "Version: "+statusResponse.getVersion().getName()+" Players: "+statusResponse.getPlayers().getOnline()+"/"+statusResponse.getPlayers().getMax()+" Description: "+convertToIrcColours(statusResponse.getDescription()));

		} catch (Exception e) {
			bot.sendMessage(channel, "welp, that didnt work: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private String convertToIrcColours(String description) {
		//§m§l--§r§8§m§l[-§r §7§lThe§b§lNode §c§lPixelmon §e§l3.3.8 §8§m§l-]§4§m§l--§r §n§4              §l(§n!§r§4§l) §6Happy Holidays from TheNodeMC! §4§l(§n!§r§4§l)

		System.out.println(description);

		description = description.replace("§0", Colors.BLACK);
		description = description.replace("§1", SimpleFunctions.combineColours(Colors.BLACK, Colors.DARK_BLUE));
		description = description.replace("§2", SimpleFunctions.combineColours(Colors.BLACK, Colors.DARK_GREEN));
		description = description.replace("§3", SimpleFunctions.combineColours(Colors.BLACK, Colors.CYAN));
		description = description.replace("§4", SimpleFunctions.combineColours(Colors.BLACK, Colors.RED));
		description = description.replace("§5", SimpleFunctions.combineColours(Colors.BLACK, Colors.PURPLE));
		description = description.replace("§6", SimpleFunctions.combineColours(Colors.BLACK, Colors.YELLOW));
		description = description.replace("§7", SimpleFunctions.combineColours(Colors.BLACK, Colors.LIGHT_GRAY));
		description = description.replace("§8", SimpleFunctions.combineColours(Colors.BLACK, Colors.DARK_GRAY));
		description = description.replace("§9", SimpleFunctions.combineColours(Colors.BLACK, Colors.BLUE));

		description = description.replace("§a", SimpleFunctions.combineColours(Colors.BLACK, Colors.GREEN));
		description = description.replace("§b", SimpleFunctions.combineColours(Colors.BLACK, Colors.CYAN));
		description = description.replace("§c", SimpleFunctions.combineColours(Colors.BLACK, Colors.RED));
		description = description.replace("§d", SimpleFunctions.combineColours(Colors.BLACK, Colors.PURPLE));
		description = description.replace("§e", SimpleFunctions.combineColours(Colors.BLACK, Colors.YELLOW));
		description = description.replace("§f", SimpleFunctions.combineColours(Colors.BLACK, Colors.WHITE));


		description = description.replace("§k", "");//obfuscated
		description = description.replace("§l", Colors.BOLD);
		description = description.replace("§m", "");//strikethrough
		description = description.replace("§n", Colors.UNDERLINE);
		description = description.replace("§o", "");//italic
		description = description.replace("§r", Colors.NORMAL);

		System.out.println(description);
		return description;
	}


}
