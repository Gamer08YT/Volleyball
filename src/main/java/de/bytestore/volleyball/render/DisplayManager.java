package de.bytestore.volleyball.render;

import de.bytestore.volleyball.core.Initiater;
import de.bytestore.volleyball.listener.DisplayListener;
import de.bytestore.volleyball.listener.GameListener;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayManager {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Volleyball | V0.1";

    private static final double FPS_CAP = (1.0 / 60);
    private static double TIME;
    private static double UNPROCESSED;

    private static GLFWKeyCallback KEYBOARD;
    private static GLFWMouseButtonCallback MOUSE;
    private static GLFWCursorPosCallback CURSOR;

    private static Vector3f BACKGROUND;

    private static long WINDOW;

    public static void createDisplay() {

        if (!GLFW.glfwInit()) {
            System.out.println("ERROR: Couldn't init GLFW !");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        WINDOW = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);

        if (WINDOW == 0) {
            System.out.println("ERROR: Couldn't init Window !");
        }

        GLFW.glfwMakeContextCurrent(WINDOW);
        GL.createCapabilities();

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(WINDOW, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);

        GLFW.glfwShowWindow(WINDOW);

        GLFW.glfwSetKeyCallback(WINDOW, KEYBOARD = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                for (DisplayListener listenerIO : Initiater.getDisplayListener()) {
                    if (action == 1) {
                        listenerIO.keyClick(key);
                    } else if (action == 2) {
                        listenerIO.keyDown(key);
                    } else listenerIO.keyUp(key);
                }
            }
        });

        GLFW.glfwSetMouseButtonCallback(WINDOW, MOUSE = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                for (DisplayListener listenerIO : Initiater.getDisplayListener()) {
                    if (action == 1) {
                        listenerIO.mouseClick(button);
                    } else listenerIO.mouseUp(button);
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(WINDOW, CURSOR = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                for (DisplayListener listenerIO : Initiater.getDisplayListener()) {
                    listenerIO.mouseMove(xpos, ypos);
                }
            }
        });

    }

    public static void updateDisplay() {
        while (!GLFW.glfwWindowShouldClose(WINDOW)) {
            if (isUpdating()) {
                //System.out.println("load" + FPS_CAP);
                for (GameListener listenerIO : Initiater.getGameListener()) {
                    listenerIO.gameLoop();
                }

                for (DisplayListener listenerIO : Initiater.getDisplayListener()) {
                    listenerIO.changeFPS(Integer.parseInt("" + FPS_CAP));
                }

                GL11.glClearColor(BACKGROUND.x, BACKGROUND.y, BACKGROUND.z, 1.0f);
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                GLFW.glfwPollEvents();
                GLFW.glfwSwapBuffers(WINDOW);
            }
        }

        closeDisplay();
        for (GameListener listenerIO : Initiater.getGameListener()) {
            listenerIO.gameStop();
        }
    }

    public static void closeDisplay() {
        GLFW.glfwTerminate();
    }

    public static void renderBackground(int rIO, int gIO, int bIO) {
        BACKGROUND = new Vector3f(rIO, gIO, bIO);
    }

    // Frames per Second
    private static double getSystemTime() {
        return (double) System.nanoTime() / (double) 1000000000;
    }

    private static boolean isUpdating() {
        //if (!closed) {
        double nextTime = getSystemTime();
        double passedTime = nextTime - TIME;
        UNPROCESSED += passedTime;
        TIME = nextTime;

        while (UNPROCESSED > 1.0 / FPS_CAP) {
            UNPROCESSED -= 1.0 / FPS_CAP;
            return true;
        }
        //}
        return false;
    }
}
