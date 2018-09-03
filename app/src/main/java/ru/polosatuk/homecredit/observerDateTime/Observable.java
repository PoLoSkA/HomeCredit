package ru.polosatuk.homecredit.observerDateTime;

public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
