package com.push.admin.timer;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public class NewTask extends TimerTask {

	private static boolean isRunning = false;
	private ITask task = TaskFactory.getFirstTask();
	private static final Logger logger = Logger.getLogger(TimerListner.class);
	
	@Override
	public void run() {
		if(!isRunning){
			isRunning = true;
			task.execute();
			isRunning = false;
		}else{
			logger.info("The task is running...");
		}
	}
	
}
