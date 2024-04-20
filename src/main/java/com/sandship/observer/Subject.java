package com.sandship.observer;
/**
 * Defines the contract for warehouse subjects that can be observed.
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
