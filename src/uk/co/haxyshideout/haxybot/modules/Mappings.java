package uk.co.haxyshideout.haxybot.modules;

import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by clienthax on 9/1/2015.
 */
public class Mappings implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"getmappings"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		bot.sendMessage(channel, getMappings());
	}

	private String getMappings() {
		String mappings = "";
		try {
			String baseUrl = "http://export.mcpbot.bspk.rs/snapshot/";
			String[] versions = new String[]{"1.7.10", "1.8"};

			for (String version : versions) {
				URLConnection connection = new URL(baseUrl + version+"/").openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				connection.connect();

				BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = r.readLine()) != null) {
					sb.append(line);
				}
				String data = sb.toString();
				String subData = data.substring(data.indexOf("mappings = '"));
				subData = subData.substring(0, subData.indexOf("<br/>"));
				mappings = mappings +"Version: "+version+" "+subData+" ";
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mappings;
	}
}
