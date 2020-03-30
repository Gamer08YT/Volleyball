package de.bytestore.volleyball.core;

import de.bytestore.volleyball.listener.GameListener;
import de.bytestore.volleyball.render.DisplayManager;
import de.bytestore.volleyball.render.Model;
import de.bytestore.volleyball.render.Renderer;
import de.bytestore.volleyball.shader.BasicShader;

public class Bootstrap implements GameListener {
    public static Renderer rendererIO = new Renderer();

    private static BasicShader shaderIO = new BasicShader();
    private static Model modelIO;

    public Bootstrap() {

        DisplayManager.createDisplay();
        DisplayManager.renderBackground(1, 0, 20);

        shaderIO.create();

        modelIO = new Model(new float[]{
                -0.5f, -0.5f, 0.0f, //TOP LEFT      0
                0.5f, 0.5f, 0.0f, //TOP RIGHT       1
                -0.5f, -0.5f, 0.0f, // BOTTOM LEFT  2
                0.5f, -0.5f, 0.0f //BOTTOM RIGHT    3
        }, new int[]{
                0, 1, 2,
                2, 3, 1
        });

        modelIO.create();

        Initiater.addListener(this);

        DisplayManager.updateDisplay();
    }


    @Override
    public void gameLoop() {
        shaderIO.bind();
        rendererIO.renderModel(modelIO);
    }

    @Override
    public void gameStop() {
        System.out.println("STOP");
        shaderIO.remove();
        modelIO.remove();
    }
}
