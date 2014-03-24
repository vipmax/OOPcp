package com.vipmax;
import com.vipmax.Controller.Engine;
import com.vipmax.Model.Model;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    Engine engine;
    public void startEngine(){
        engine = new Engine();
        engine.start();

    }


	public static void main(String[] args) {
        new Main().startEngine();

    }




}
