package de.bytestore.volleyball.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Model {
    private int VERTEX_ARRAY_ID, VERTEX_BUFFER_ID, VERTEX_COUNT, INDICES_BUFFER_ID;
    private float[] VERTICES;
    private int[] INDICES;

    public Model(float[] verticesIO, int[] indicesIO) {
        VERTICES = verticesIO;
        INDICES = indicesIO;
        VERTEX_COUNT = indicesIO.length / 3;
        System.out.println(VERTEX_COUNT);
    }

    public void create() {
        System.out.println("CREATE!");
        FloatBuffer bufferIO = BufferUtils.createFloatBuffer(VERTICES.length);
        bufferIO.put(VERTICES);
        bufferIO.flip();

        IntBuffer indicesIO = BufferUtils.createIntBuffer(INDICES.length);
        indicesIO.put(INDICES);
        indicesIO.flip();

        VERTEX_ARRAY_ID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VERTEX_ARRAY_ID);

        VERTEX_BUFFER_ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VERTEX_BUFFER_ID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bufferIO, GL15.GL_STATIC_DRAW);

        INDICES_BUFFER_ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, INDICES_BUFFER_ID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesIO, GL15.GL_STATIC_DRAW);

        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL30.glBindVertexArray(0);
        GL20.glDisableVertexAttribArray(0);
    }

    public void remove() {
        System.out.println("REMOVING!");
        GL30.glDeleteVertexArrays(VERTEX_ARRAY_ID);
        GL15.glDeleteBuffers(VERTEX_BUFFER_ID);
        GL15.glDeleteBuffers(INDICES_BUFFER_ID);
    }

    public int getVertexArrayID() {
        return VERTEX_ARRAY_ID;
    }

    public int getVertexBufferID() {
        return VERTEX_BUFFER_ID;
    }

    public int getVertexCount() {
        return VERTEX_COUNT;
    }
}
