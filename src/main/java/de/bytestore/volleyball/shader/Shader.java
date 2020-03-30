package de.bytestore.volleyball.shader;

import de.bytestore.volleyball.Volleyball;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Shader {
    private int VERTEX_SHADER_ID, FRAGMENT_SHADER_ID, PROGRAM_ID;
    private String FRAGMENT_FILE, VERTEX_FILE;

    public Shader(String vertexIO, String fragmentIO) {
        VERTEX_FILE = vertexIO;
        FRAGMENT_FILE = fragmentIO;
    }

    public void create() {
        PROGRAM_ID = GL20.glCreateProgram();

        VERTEX_SHADER_ID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(VERTEX_SHADER_ID, readFile(VERTEX_FILE));
        GL20.glCompileShader(VERTEX_SHADER_ID);

        if (GL20.glGetShaderi(VERTEX_SHADER_ID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println("ERROR: Vertex Shader - " + GL20.glGetShaderInfoLog(VERTEX_SHADER_ID));
        }

        FRAGMENT_SHADER_ID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(FRAGMENT_SHADER_ID, readFile(FRAGMENT_FILE));
        GL20.glCompileShader(FRAGMENT_SHADER_ID);

        if (GL20.glGetShaderi(FRAGMENT_SHADER_ID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println("ERROR: Fragment Shader - " + GL20.glGetShaderInfoLog(FRAGMENT_SHADER_ID));
        }

        GL20.glAttachShader(PROGRAM_ID, VERTEX_SHADER_ID);
        GL20.glAttachShader(PROGRAM_ID, FRAGMENT_SHADER_ID);

        GL20.glLinkProgram(PROGRAM_ID);
        GL20.glValidateProgram(PROGRAM_ID);

        if (GL20.glGetProgrami(PROGRAM_ID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.out.println("ERROR: Program Linking - " + GL20.glGetProgramInfoLog(PROGRAM_ID));
        }

        if (GL20.glGetProgrami(PROGRAM_ID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.out.println("ERROR: Program Validate - " + GL20.glGetProgramInfoLog(PROGRAM_ID));
        }


    }

    public abstract void bindAttributes();

    public void binAttribute(int indexIO, String locationIO) {
        GL20.glBindAttribLocation(PROGRAM_ID, indexIO, locationIO);
    }

    public void bind() {
        GL20.glUseProgram(PROGRAM_ID);
    }

    public void remove() {
        GL20.glDetachShader(PROGRAM_ID, VERTEX_SHADER_ID);
        GL20.glDetachShader(PROGRAM_ID, FRAGMENT_SHADER_ID);

        GL20.glDeleteShader(VERTEX_SHADER_ID);
        GL20.glDeleteShader(FRAGMENT_SHADER_ID);

        GL20.glDeleteProgram(PROGRAM_ID);
    }

    private String readFile(String fileIO) {
        BufferedReader readerIO = null;
        StringBuilder stringIO = new StringBuilder();

        try {
            File pathIO = new File(this.getClass().getResource("/" + fileIO).getFile());
            readerIO = new BufferedReader(new FileReader(pathIO));

            String line;
            while ((line = readerIO.readLine()) != null) {
                stringIO.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("ERROR: Coldn't find file !");
        }

        return stringIO.toString();
    }
}
