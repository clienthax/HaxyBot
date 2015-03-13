package uk.co.haxyshideout.haxybot.modules;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.config.Config;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by clienthax on 3/3/2015.
 */
public class MemeGen implements ITaskRunner {
	@Override
	public String[] getCommandAliases() {
		return new String[]{"meme"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		try {

			String[] values = StringUtils.substringsBetween(substring, "\"", "\"");
			if(values == null || values.length < 3)
				return;
			String memeImageName = values[0];
			String topText = values[1];
			String bottomText = values[2];

			int memeTemplateID = 1;
			try {
				memeTemplateID = Integer.parseInt(values[0]);
			}catch (Exception e){
				switch (memeImageName) {
					case "doesnotsimply":
						memeTemplateID = 61579;
						break;
					default:
						bot.sendMessage(channel, "use a id from here! https://api.imgflip.com/popular_meme_ids");
				}
			}


			String webResponse = IOUtils.toString(define(memeTemplateID, topText, bottomText));
			Response response = gson.fromJson(webResponse, Response.class);
			bot.sendMessage(channel, response.data.url);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final Gson gson = new Gson();
	private static final String API_DEFINE = "http://api.imgflip.com/caption_image?template_id=%TEMPLATEID%&text0=%TEXTTOP%&text1=%TEXTBOTTOM%&username="+ Config.getConfig().imgflipUsername +"&password="+Config.getConfig().imgflipPassword;

	private static InputStream define(int templateID, String topText, String bottomText) throws Exception {
		String url = API_DEFINE.replace("%TEMPLATEID%", ""+templateID).replace("%TEXTTOP%", URLEncoder.encode(topText, "UTF-8")).replace("%TEXTBOTTOM%", URLEncoder.encode(bottomText, "UTF-8"));
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		return connection.getInputStream();
	}

	class Response {
		boolean success;
		URLResponse data;
	}

	class URLResponse {
		String url;
		String page_url;
	}
}
