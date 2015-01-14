package uk.co.haxyshideout.haxybot.utils.idlerpg.job;

import org.apache.commons.lang3.time.DateUtils;
import uk.co.haxyshideout.haxybot.HaxyBot;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by clienthax on 9/1/2015.
 */
public class ShockyHandler {
	public static void handleShocky(HaxyBot bot, String message) {
		String[] split = message.split(Pattern.quote(" | "));
		String timesCombined = split[1].replace(" until level up", "");
		System.out.println("idlerpg timer = "+timesCombined);

		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;

		String[] timesSplit = timesCombined.split(" ");
		for(String timeString : timesSplit) {
			if(timeString.endsWith("d"))
				days = Integer.parseInt(timeString.substring(0, timeString.length() - 1));
			if(timeString.endsWith("m"))
				minutes = Integer.parseInt(timeString.substring(0, timeString.length() - 1));
			if(timeString.endsWith("h"))
				hours = Integer.parseInt(timeString.substring(0, timeString.length() - 1));
			if(timeString.endsWith("s"))
				seconds = Integer.parseInt(timeString.substring(0, timeString.length() - 1));

		}

		Date date = new Date();
		date = DateUtils.addDays(date, days);
		date = DateUtils.addHours(date, hours);
		date = DateUtils.addMinutes(date, minutes);
		date = DateUtils.addSeconds(date, seconds + 10);
		System.out.println("should type > at "+date.toString());

		IdleRpg idleRpg = new IdleRpg(bot, "#pixelmonmod", date);
		try {
			new Thread(idleRpg).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
