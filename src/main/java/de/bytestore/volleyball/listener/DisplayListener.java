package de.bytestore.volleyball.listener;

public interface DisplayListener {
    void mouseClick(int keyIO);

    void mouseUp(int keyIO);

    void keyClick(int keyIO);

    void keyDown(int keyIO);

    void keyUp(int keyIO);

    void mouseMove(double xIO, double yIO);

    void changeFPS(int fpsIO);
}
