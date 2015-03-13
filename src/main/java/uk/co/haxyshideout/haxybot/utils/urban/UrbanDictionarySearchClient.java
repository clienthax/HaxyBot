package uk.co.haxyshideout.haxybot.utils.urban;


import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class UrbanDictionarySearchClient {

	private static final Gson gson = new Gson();
	private static final String API_DEFINE = "http://api.urbandictionary.com/v0/define?term=";

	public static Response getDefinition(String input) {
		try {
			return gson.fromJson(IOUtils.toString(define(input)), Response.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static InputStream define(String input) throws Exception {
		return new URL(API_DEFINE + URLEncoder.encode(input, "UTF-8")).openConnection().getInputStream();
	}
}