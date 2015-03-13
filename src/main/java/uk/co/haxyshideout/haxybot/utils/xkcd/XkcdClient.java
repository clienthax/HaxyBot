package uk.co.haxyshideout.haxybot.utils.xkcd;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by clienthax on 11/1/2015.
 */
public class XkcdClient {

	private final static Gson gson = new Gson();

	public static String findComic(String searchTerm) throws Exception {

		String apiUrl = "http://relevant-xkcd.appspot.com/api?inputString="+ URLEncoder.encode(searchTerm, "UTF-8")+"&lowerBound=0&upperBound=1";

		String jsonString = IOUtils.toString(new URL(apiUrl));
		XKCDResponse response = gson.fromJson(jsonString, XKCDResponse.class);
		if(response.outputMulti.size() == 0)
			throw new Exception("Nothing returned from search");
		String comicNum = response.outputMulti.get(0).label.substring(7);

		return "http://xkcd.com/"+comicNum;
	}

	class XKCDResponse {
		String input;
		List<ComicInfo> outputMulti;
	}

	class ComicInfo {
		String score;
		String label;
	}


}
