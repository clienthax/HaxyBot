package uk.co.haxyshideout.haxybot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


/**
 * Created by clienthax on 14/1/2015.
 */
public class Config {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static BotConfig config = null;
	private static final String configFile = "./resources/config.json";

	public static void main(String[] args)
	{
		new Config();
	}
	public Config() {
		getConfig();

	}

	public static BotConfig getConfig() {
		if(config == null)
			loadConfig();
		saveConfig();
		return config;
	}

	private static void loadConfig() {
		try {
			String jsonString = IOUtils.toString(new FileReader(configFile));
			config = gson.fromJson(jsonString, BotConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveConfig() {
		try {
			FileWriter fileWriter = new FileWriter(configFile);
			IOUtils.write(gson.toJson(config), fileWriter);
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class BotConfig {
		public String server;
		public String username;
		public String password;
		public Set<String> channels;
	}



}
