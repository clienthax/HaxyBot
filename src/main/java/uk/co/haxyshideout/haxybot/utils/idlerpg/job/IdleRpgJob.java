package uk.co.haxyshideout.haxybot.utils.idlerpg.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import uk.co.haxyshideout.haxybot.HaxyBot;

/**
 * Created by clienthax on 9/1/2015.
 */
public class IdleRpgJob implements Job {

	public void execute(final JobExecutionContext context) throws JobExecutionException {

		HaxyBot bot = (HaxyBot) context.getJobDetail().getJobDataMap().get("bot");
		String channel = (String) context.getJobDetail().getJobDataMap().get("channel");

		bot.sendMessage(channel, ">");
	}

}
