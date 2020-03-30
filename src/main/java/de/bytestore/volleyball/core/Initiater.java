package de.bytestore.volleyball.core;

import de.bytestore.volleyball.listener.DisplayListener;
import de.bytestore.volleyball.listener.GameListener;

import java.util.ArrayList;
import java.util.List;

public class Initiater {
    private static List<DisplayListener> displayIO = new ArrayList<DisplayListener>();
    private static List<GameListener> gameIO = new ArrayList<GameListener>();

    public static void initListener() {

    }

    // Display Listener
    public static void addListener(DisplayListener listenerIO) {
        displayIO.add(listenerIO);
    }

    public static List<DisplayListener> getDisplayListener() {
        return displayIO;
    }

    // Game Listener
    public static void addListener(GameListener listenerIO) {
        gameIO.add(listenerIO);
    }

    public static List<GameListener> getGameListener() {
        return gameIO;
    }
}
