package entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gloabal {
    public static final ObservableList<Session> sessions = FXCollections.observableArrayList();
    public static final ObservableList<Session> expiringSessions = FXCollections.observableArrayList();
}
