package com.vipmax.Model;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by vipmax on 3/18/14.
 */
public class Model {
	public final static Integer HOUR = 1000 * 60 * 60;
	public static Integer timeSimulation = 1;
	public static Boolean increaseOrReduction = true;
	public static Integer timeAvgCashier1 = 1, timeAvgCashier2 = 1;
	public static Integer countOfPeopleInHour = 3600;
	public static volatile Boolean isSumulatingRun = true,isOpenDoor = true;
}
