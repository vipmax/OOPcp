package com.vipmax.Model;

import java.util.Queue;

/**
 * Created by vipmax on 3/16/14.
 */
public class TicketService {
    private Seller cashier1;
    private Seller cashier2;

    private static TicketService instance;
    private static Integer countOfTickets = 0;

    public interface Seller {

        void startSell();

        void stopSell();
        void restart();

        Queue<People> getQueue();

        void addToQueue(People people);

        Integer getAvgTime();

        void setAvgTime(Integer avgTime);


    }

    private TicketService() {
        cashier1 = new Cashier(1, 20);
        cashier2 = new Cashier(2, 30);
        cashier1.startSell();
        cashier2.startSell();
    }

    public Seller getCashier1() {
        return cashier1;
    }

    public Seller getCashier2() {
        return cashier2;
    }

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
            return instance;
        }
        return instance;
    }


    public Integer getCountOfTickets() {
        return countOfTickets;
    }

    public void setCountOfTickets(Integer count, People people) {
        if (people instanceof Seller) {
            countOfTickets = count;
        } else try {
            throw new Exception("Ты не продавец");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    public void handlePeople(People people) {
        if (cashier1.getQueue().size() == cashier2.getQueue().size()) {
            cashier1.getQueue().add(people);
        } else {
            if (cashier1.getQueue().size() < cashier2.getQueue().size())
                cashier1.getQueue().add(people);
            else cashier2.getQueue().add(people);
        }


    }


}
