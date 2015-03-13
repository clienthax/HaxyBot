package uk.co.haxyshideout.haxybot.modules;

import com.sun.management.OperatingSystemMXBean;
import uk.co.haxyshideout.haxybot.HaxyBot;
import uk.co.haxyshideout.haxybot.modules.interfaces.AccessLevel;
import uk.co.haxyshideout.haxybot.modules.interfaces.ITaskRunner;
import uk.co.haxyshideout.haxybot.utils.SimpleFunctions;

import java.lang.management.ManagementFactory;

/**
 * Created by clienthax on 9/1/2015.
 */
public class ListSpecs implements ITaskRunner {

	@Override
	public String[] getCommandAliases() {
		return new String[]{"specs"};
	}

	@Override
	public AccessLevel accessLevelRequired() {
		return AccessLevel.PUBLIC;
	}

	@Override
	public void processTask(HaxyBot bot, String channel, String sender, String message, String substring) {
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

		String osName = operatingSystemMXBean.getName();
		String osVersion = operatingSystemMXBean.getVersion();
		String osArch = operatingSystemMXBean.getArch();
		String osLine = "os("+osName+" ("+osVersion+" "+osArch+"))";

		long totalRam = operatingSystemMXBean.getTotalPhysicalMemorySize();
		long freeRam = operatingSystemMXBean.getFreePhysicalMemorySize();
		String ramLine = "ram(Free: "+ SimpleFunctions.readableFileSize(freeRam)+" Total: "+SimpleFunctions.readableFileSize(totalRam)+")";

		String specLine = osLine+" "+ramLine;
		bot.sendMessage(channel, specLine);
	}
}
