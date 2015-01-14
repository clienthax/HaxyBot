package uk.co.haxyshideout.haxybot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.DecimalFormat;

/**
 * Created by clienthax on 9/1/2015.
 */
public class SimpleFunctions {

	public static String readableFileSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public static String combineColours(String background, String foreground) {
		//String color = background+","+foreground.substring(1);
		return foreground+","+background.substring(1);
	}

	public static String wrapQuotes(String input) {
		return "\""+input+"\"";
	}

	public static String prettifyJson(String json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		return gson.toJson(element);
	}
}
