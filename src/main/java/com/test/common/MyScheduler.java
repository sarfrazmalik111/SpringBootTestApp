package com.test.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MyScheduler {

	/**
	 * It will update the status(make Active) of today's PromoCodes, It will call at 01:00AM
	 * CRON: s m h D M Y (second, minute, hour, day, month, weekday)
	 */
	@Scheduled(cron = "0 0 1 * * *")
	public void myShedular1AM() {
		System.out.println("------------ PromoCodesShedular -----------");
	}
	
	/**
	 * It will buy all powerbanks those rentals time has been 7 days, It will execute every at first minute of an hour
	 * CRON: s m h D M Y (second, minute, hour, day, month, weekday)
	 */
	@Scheduled(cron = "0 1 * * * *")
	public void myShedularFirstMinute() {
		System.out.println("------------ BuyPowerbankShedular -----------");
	}
	
	/**
	 * It will update the StationStatus(Online/Offline), It will execute after every 3-minutes
	 * fixedRate: milliSeconds
	 */
	@Scheduled(fixedRate = 180000)
	public void myShedularEvery3Minutes() {
		System.out.println("------------ UpdateStationStatusShedular -----------");
	}

}
