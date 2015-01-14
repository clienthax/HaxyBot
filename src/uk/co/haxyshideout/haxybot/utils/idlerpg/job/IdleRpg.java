package uk.co.haxyshideout.haxybot.utils.idlerpg.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.haxyshideout.haxybot.HaxyBot;

import java.util.Date;

/**
 * Created by clienthax on 9/1/2015.
 */
public class IdleRpg implements Runnable {

	private final HaxyBot bot;
	private final String channel;
	private final Date runtime;

	public IdleRpg(HaxyBot bot, String channel, Date runtime) {
		this.bot = bot;
		this.channel = channel;
		this.runtime = runtime;
	}

	public void run() {

		try {

		final Logger log = LoggerFactory.getLogger((Class) IdleRpg.class);
		log.info("------- Initializing ----------------------");
		final SchedulerFactory sf = new StdSchedulerFactory();
		final Scheduler sched = sf.getScheduler();

		@SuppressWarnings("unchecked") final JobDetail job = JobBuilder.newJob((Class) IdleRpgJob.class).withIdentity("job1", "group1").build();
		job.getJobDataMap().put("bot", bot);
		job.getJobDataMap().put("channel", channel);

		final Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runtime).build();
		sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + runtime);
		sched.start();

			//TODO make sched turn off after its run

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
