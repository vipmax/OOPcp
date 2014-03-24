package com.vipmax.View;

import com.vipmax.Controller.Engine;
import com.vipmax.Model.Model;
import com.vipmax.Model.TicketService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vipmax on 3/16/14.
 */
public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JLabel countOfTicketsLabel;
    private JLabel queueToCashier1Label;
    private JLabel queueToCashier2Label;
    private JButton inQueueButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton startButton;
    private JButton closeServiceButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;


    public MainForm() {
        init();
        countOfTicketsLabel.setText(Engine.getTicketService().getCountOfTickets().toString());
        textField1.setText(Engine.getTicketService().getCashier1().getAvgTime().toString());
        textField2.setText(Engine.getTicketService().getCashier2().getAvgTime().toString());
        textField3.setText(Model.countOfPeopleInHour.toString());
        inQueueButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Engine.createAndHandlePeople();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    queueToCashier1Label.setText(new Integer(Engine.getTicketService().getCashier1().getQueue().size()).toString());
                    queueToCashier2Label.setText(new Integer(Engine.getTicketService().getCashier2().getQueue().size()).toString());
                    countOfTicketsLabel.setText(Engine.getTicketService().getCountOfTickets().toString());

                    try {
                        Thread.sleep(1000 / (4 * Model.timeSimulation));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox) e.getSource();
                Model.timeSimulation = Integer.valueOf((String) box.getSelectedItem());
                Engine.restartTime();
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox) e.getSource();
                String s = (String) box.getSelectedItem();
                if (s.equals("увеличится")) {
                    Model.increaseOrReduction = true;
                } else Model.increaseOrReduction = false;
                Engine.restartTime();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Model.isSumulatingRun == false) {
                    ((JButton) e.getSource()).setText("Остановить");
                    Model.isSumulatingRun = true;

                } else {

                    ((JButton) e.getSource()).setText("Запустить");
                    Model.isSumulatingRun = false;

                }

            }
        });
        closeServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Model.isOpenDoor) {
                    ((JButton) e.getSource()).setText("Открыть кассы");
                    Model.isOpenDoor = false;

                } else {
                    ((JButton) e.getSource()).setText("Закрыть кассы");
                    Model.isOpenDoor = true;
                }


            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Engine.getTicketService().getCashier1().setAvgTime(Integer.valueOf(((JTextField) e.getSource()).getText()));

            }
        });
        textField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Engine.getTicketService().getCashier2().setAvgTime(Integer.valueOf(((JTextField) e.getSource()).getText()));
            }
        });
        textField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.countOfPeopleInHour = Integer.valueOf(((JTextField) e.getSource()).getText());
//				Engine.changeDelayTimer();
                Engine.restartTime();


            }
        });
    }

    public void init() {

        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setVisible(true);
        pack();
    }

    public void updateCountOfTicketLabel(Integer count) {
        this.countOfTicketsLabel.setText(count.toString());
    }

    public void updateQueueLabel(Integer ID, Integer count) {

        switch (ID) {
            case 1:
                this.queueToCashier1Label.setText(count.toString());
                break;
            case 2:
                this.queueToCashier2Label.setText(count.toString());
                break;
        }
    }


}