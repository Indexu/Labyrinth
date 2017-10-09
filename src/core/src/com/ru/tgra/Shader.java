package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Light;
import com.ru.tgra.utilities.Material;
import com.ru.tgra.utilities.Point3D;

import java.nio.FloatBuffer;

public class Shader
{
    private int renderingProgramID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private int positionLoc;
    private int normalLoc;
    private int eyePosLoc;
    private int shininessFactorLoc;
    private int globalAmbienceLoc;
    private int lightPosLoc;
    private int lightColorLoc;
    private int materialDiffuseLoc;
    private int materialSpecularLoc;
    private int materialAmbienceLoc;
    private int materialEmissionLoc;
    private int materialTransparencyLoc;

    private int modelMatrixLoc;
    private int viewMatrixLoc;
    private int projectionMatrixLoc;

    public Shader()
    {
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);

        positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(positionLoc);

        normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
        Gdx.gl.glEnableVertexAttribArray(normalLoc);

        modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
        projectionMatrixLoc	    = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        eyePosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");
        lightPosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition");

        shininessFactorLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_shininessFactor");
        globalAmbienceLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbience");

        lightColorLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor");
        materialDiffuseLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
        materialSpecularLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");
        materialAmbienceLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialAmbience");
        materialEmissionLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialEmission");
        materialTransparencyLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialTransparency");

        Gdx.gl.glUseProgram(renderingProgramID);
    }

    public void setMaterial(Material material)
    {
        setMaterialAmbience(material.getAmbience());
        setMaterialDiffuse(material.getDiffuse());
        setMaterialSpecular(material.getSpecular());
        setMaterialEmission(material.getEmission());
        setShininessFactor(material.getShininess());
    }

    public void setLight(Light light)
    {
        setLightPosition(light.getPosition());
        setLightColor(light.getColor());
    }

    public void setMaterialDiffuse(Color color)
    {
        Gdx.gl.glUniform4f(materialDiffuseLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialSpecular(Color color)
    {
        Gdx.gl.glUniform4f(materialSpecularLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialAmbience(Color color)
    {
        Gdx.gl.glUniform4f(materialAmbienceLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialEmission(Color color)
    {
        Gdx.gl.glUniform4f(materialEmissionLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialTransparency(float transparency)
    {
        Gdx.gl.glUniform1f(materialTransparencyLoc, transparency);
    }

    public void setLightColor(Color color)
    {
        Gdx.gl.glUniform4f(lightColorLoc, color.r, color.g, color.b, color.a);
    }

    public void setShininessFactor(float f)
    {
        Gdx.gl.glUniform1f(shininessFactorLoc, f);
    }

    public void setGlobalAmbience(float f)
    {
        Gdx.gl.glUniform1f(globalAmbienceLoc, f);
    }

    public void setLightPosition(Point3D position)
    {
        Gdx.gl.glUniform4f(lightPosLoc, position.x, position.y, position.z, 1.0f);
    }

    public void setEyePosition(Point3D position)
    {
        Gdx.gl.glUniform4f(eyePosLoc, position.x, position.y, position.z, 1.0f);
    }

    public int getVertexPointer()
    {
        return positionLoc;
    }

    public int getNormalPointer()
    {
        return normalLoc;
    }

    public void setModelMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
    }

    public void setViewMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
    }

    public void setProjectionMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
    }
}
