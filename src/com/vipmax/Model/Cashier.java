package com.vipmax.Model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by vipmax on 3/16/14.
 */
public class Cashier extends People implements TicketService.Seller {
    public final Integer ID;
    private Integer timeServiceAVG;
    public Boolean isSellable;
    Queue<People> queue;
    private Thread t;

    public Cashier(Integer ID, Integer timeServiceAVG) {
        this.ID = ID;
        this.timeServiceAVG = timeServiceAVG;
        isSellable = true;
        queue = new LinkedList<People>();
    }

    @Override
    public void startSell() {


        t = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isSellable) {
                    if (Model.isSumulatingRun) {
                        synchronized (t) {
                            try {

                                t.wait(handlePeople().longValue(), 1);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (queue.size() > 0) {
//                            System.out.println("ID " + ID + "queueSize" + queue.size());
                            queue.poll();
                            TicketService.getInstance().setCountOfTickets(TicketService.getInstance().getCountOfTickets() + 1, Cashier.this);

                        }
                    }

                }

            }
        });
        t.start();


    }

    private Double handlePeople() {

        Double lnx = Math.log(new Random().nextDouble());
        Double z = new Double(0);
        while (lnx.equals(0)) {
            lnx = Math.log(new Random().nextDouble());
        }
        if (Model.increaseOrReduction) {
            z = Double.valueOf(timeServiceAVG * 1000 / Model.timeSimulation);
        } else {
            z = Double.valueOf(timeServiceAVG * 1000 * Model.timeSimulation);
        }
        Double dt = -z / lnx;
        System.out.println("Cashier "+ ID +" delay:" + dt);

        return dt;

    }

    @Override
    public void stopSell() {
        t.stop();
    }

    @Override
    public Queue<People> getQueue() {
        return queue;
    }

    @Override
    public void addToQueue(People people) {
        queue.add(people);
    }

    @Override
    public Integer getAvgTime() {
        return timeServiceAVG;
    }

    @Override
    public void setAvgTime(Integer avgTime) {
        timeServiceAVG = avgTime;
        restart();
    }


    public  void restart() {
        synchronized (t) {
            t.notify();
        }
    }
}
