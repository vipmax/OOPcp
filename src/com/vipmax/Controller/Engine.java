package com.vipmax.Controller;

import com.vipmax.View.MainForm;
import com.vipmax.Model.Model;
import com.vipmax.Model.People;
import com.vipmax.Model.TicketService;

import javax.swing.Timer;
import java.util.*;

/**
 * Created by vipmax on 3/18/14.
 */

public class Engine {

    private static Integer countOfPeople = 0;

    private static TicketService ticketService;
    private static MainForm form;
    public static Thread t;

    public static Double nextdt() {
        Double lnx = Math.log(new Random().nextDouble());
        Double z = new Double(0);
        while (lnx.equals(0)) {
            lnx = Math.log(new Random().nextDouble());
        }
        if (Model.increaseOrReduction) {
            z = Model.HOUR / ((double) Model.timeSimulation * Model.countOfPeopleInHour);
        } else {
            z = Model.HOUR * Model.timeSimulation / (double) Model.countOfPeopleInHour;
        }
        Double dt = -z / lnx;
//        System.out.println("delay:" + dt);

        return dt;
    }
    public static void restartTime() {
        synchronized (t) {
            t.notify();
        }
        getTicketService().getCashier1().restart();
    }
    public static void createAndHandlePeople() {
        People people = new People();
        countOfPeople++;
//        System.out.println("Пришло людей: " + countOfPeople + "  " + new Date());
        TicketService.getInstance().handlePeople(people);
    }
    public static TicketService getTicketService() {
        return ticketService;
    }


    public  void start() {
        ticketService = TicketService.getInstance();
        form = new MainForm();
        t = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (Model.isSumulatingRun && Model.isOpenDoor && Model.countOfPeopleInHour != 0) {

                        synchronized (t) {
                            try {
                                Double time = nextdt();

                                if (time >= 1)
                                    t.wait(time.longValue(), Double.valueOf(time - time.longValue()).intValue() );
                                // Thread.sleep(nextdt().longValue());  не корректно работает при смене аргумента
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                        createAndHandlePeople();

//


                    }
                }

            }
        });
        t.start();

//
    }





}
