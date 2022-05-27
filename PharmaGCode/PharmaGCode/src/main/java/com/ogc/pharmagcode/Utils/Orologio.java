package com.ogc.pharmagcode.Utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Orologio extends Thread{
    private Clock c=Clock.systemDefaultZone();
    private ZonedDateTime inizioTimer;
    public String chiediOrarioFormattato(){
        DateTimeFormatter f=DateTimeFormatter.ofPattern("dd/MM/YYYY   HH:mm:ss");
        return f.format(chiediOrario());
    }
    public ZonedDateTime chiediOrario(){
        return Instant.now(c).atZone(ZoneId.systemDefault());
    }

    public void setOrologio(EventHandler<ActionEvent> e){
        Timeline clock=new Timeline(new KeyFrame(Duration.ZERO, e), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    public void iniziaTimer(){
        inizioTimer=Instant.now(c).atZone(ZoneId.systemDefault());
    }

    public boolean confrontaTimer(){
        if(inizioTimer==null){
            return false;
        }
        java.time.Duration d= java.time.Duration.between(inizioTimer,chiediOrario());
        if(d.toMinutes()>=15){
            inizioTimer=null;
            return true;
        }else
            return false;
    }

    public void run(){
        Scanner s=new Scanner(System.in);
        String[] cmd;
        while (true){
            try{
                cmd=s.nextLine().split(" ");
                if(cmd[0].equals("offset")){
                    if(cmd.length>1){
                        c=Clock.offset(c, java.time.Duration.ofHours(Integer.parseInt(cmd[1])));
                    }
                    if(cmd.length>2){
                        c=Clock.offset(c, java.time.Duration.ofMinutes(Integer.parseInt(cmd[2])));
                    }
                }
                if(cmd[0].equals("fixed")){

                }
                if(cmd[0].equals("reset")){
                    c=Clock.systemDefaultZone();
                }
            }catch(NumberFormatException e ){
                System.err.println("Formato non valido");
            }
        }
    }

}
