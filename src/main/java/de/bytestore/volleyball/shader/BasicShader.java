package de.bytestore.volleyball.shader;

public class BasicShader extends Shader {
    private static final String VERTEX_FILE = "basicVertexShader.vs", FRAGMENT_FILE = "basicFragmentShader.fs";

    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.binAttribute(0, "vertices");
    }
}
