package ru.polosatuk.homecredit.observerDateTime;

import android.content.Context;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeDateFactory implements Observable {
    private Context context;
    private List<Observer> observers;
    private String date;
    private String time = " 23:59";
    private LocalDate localDate;
    private Thread temp;
    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private boolean threadStartStop = true;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(" HH:mm:ss");


    public TimeDateFactory(Context context, Observer observer) {
        observers = new LinkedList<>();
        this.context = context;
        registerObserver(observer);
        AndroidThreeTen.init(context);
        ZoneId.systemDefault();
        temp = new Thread(te);
        temp.start();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(date, time);
    }
    Runnable te = new Runnable() {
        @Override
        public void run() {

            if(isThreadStartStop()){
                while (isThreadStartStop()) {
                    Log.d("CostsFragment", "!!!INTHREAD " + isThreadStartStop() + " date = " + date + " time = " + time +temp.getName() + temp.getState()
                    );
                    date = dateFormat.format(LocalDate.now());
                    time = timeFormat.format(LocalTime.now());
                    setDateTime(date, time);
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                temp.interrupt();
            }

        }
    };


    public void setDateTime(String date, String time) {
        this.date = date;
        this.time = time;
        notifyObservers();
    }

    public void plusDate(String date) {
        localDate = LocalDate.parse(date, dateFormat);
        time = " 23:59";
        date = localDate.plusDays(1).format(dateFormat);
        Log.d("CostsFragment", "!!PLUS DATE threadStartStop = " + isThreadStartStop() + " date = " + date + " time = " + time+ temp.getState());
        if (!date.equals(LocalDate.now().format(dateFormat))) {
            setThreadStartStop(false);
            setDateTime(date, time);
            Log.d("CostsFragment", "DATE PLUS DATE threadStartStop = " + isThreadStartStop() + " date = " + date + " time = " + time+ temp.getState());
        }else {
            setThreadStartStop(true);
            setDateTime(date, LocalTime.now().format(timeFormat));
            synchronized (temp){
                temp.notify();
            }
            if (temp.getState() == Thread.State.TERMINATED){
                temp = new Thread(te);
                temp.start();
            }
            Log.d("CostsFragment", "DATE!=Local DATE threadStartStop = " + isThreadStartStop() + temp.getState());
        }
    }



    public void minusDate(String date) {
//        try {
//            temp.wait(10* 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        localDate = LocalDate.parse(date, dateFormat);
        time = " 23:59";
        date = localDate.minusDays(1).format(dateFormat);
        if (!date.equals(LocalDate.now().format(dateFormat))) {
            setThreadStartStop(false);
            setDateTime(date, time);
            Log.d("CostsFragment", "MINUS DATE threadStartStop = " + isThreadStartStop() + " date = " + date + " time = " + time+ temp.getState());
        }else {
            setThreadStartStop(true);
            setDateTime(date,LocalTime.now().format(timeFormat));
            synchronized (temp){
                temp.notify();
            }
            if (temp.getState() == Thread.State.TERMINATED){
                temp = new Thread(te);
                temp.start();
            }
            Log.d("CostsFragment", "DATE=Local DATE threadStartStop = " + isThreadStartStop() +temp.getState());
        }
    }


    public boolean isThreadStartStop() {
        return threadStartStop;
    }

    public void setThreadStartStop(boolean threadStartStop) {
        this.threadStartStop = threadStartStop;

    }
}
