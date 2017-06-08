package self.coding.challenge.twitsearch;

import java.util.Observable;

/**
 * Observable class used for receiver to activity communication
 */
class NetworkObserver extends Observable {
    private static NetworkObserver instance = new NetworkObserver();

    /**
     * singleton instance of the class
     *
     * @return singleton object of the class
     */
    static NetworkObserver getInstance() {
        return instance;
    }

    /**
     * default constructor
     */
    private NetworkObserver() {
    }

    /**
     * method to notify new data from the observer
     *
     * @param data new data updated
     */
    void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}