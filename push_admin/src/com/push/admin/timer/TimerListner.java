package com.push.admin.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.push.admin.dbbean.*;
import org.apache.log4j.Logger;

public class TimerListner {
	private static TimerListner timerListner;
	private static Timer timer; 
	private  List<TPushApk> apks;
	private  List<TPushLink> links;
	private  List<TPushText> texts;
    private  List<TPushCommands> orders;
	private  List<TPushUpdateConfig> configs;
	private static final Logger logger = Logger.getLogger(TimerListner.class);
	private static boolean isOpening = false;
	
	private TimerListner(){
		timer = new Timer(true);
		this.apks = new ArrayList<TPushApk>();
		this.links = new ArrayList<TPushLink>();
		this.texts = new ArrayList<TPushText>();
        this.orders = new ArrayList<TPushCommands>();
		this.configs = new ArrayList<TPushUpdateConfig>();
	}
	
	public static TimerListner getInstance(){
		if(timerListner==null){
			timerListner = new TimerListner();
		}
		return timerListner;
	}
	
	public void openListner(){
		synchronized (this) {
			if (!isOpening) {
				timer.schedule(new NewTask(), 0, 60 * 1000);
				isOpening = true;
				logger.info("Open the listner!");
			}
		}
	}
	
	public void closeListner(){
		timer.cancel();
		isOpening=false;
		timerListner=null;
		logger.info("Close the listner!");
	}

	public List<TPushApk> getApks() {
		return apks;
	}

	public void setApks(List<TPushApk> apks) {
		this.apks = apks;
	}

	public List<TPushLink> getLinks() {
		return links;
	}

	public void setLinks(List<TPushLink> links) {
		this.links = links;
	}

	public List<TPushText> getTexts() {
		return texts;
	}

	public void setTexts(List<TPushText> texts) {
		this.texts = texts;
	}

	public List<TPushUpdateConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<TPushUpdateConfig> configs) {
		this.configs = configs;
	}

    public List<TPushCommands> getOrders() {
        return orders;
    }

    public void setOrders(List<TPushCommands> orders) {
        this.orders = orders;
    }
}
