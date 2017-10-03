package com.ru.tgra;

import com.ru.tgra.utilities.Color;

public class Settings
{
    /* === Maze settings === */
    // Height and width MUST both be odd numbers
    public static final int height = 25;
    public static final int width = 25;

    /* === Player settings === */
    public static final float playerFOV = 60f;
    public static final float playerSpeed = 5f;
    public static final float playerLookSensitivity = 100f;

    /* === Colors === */
    // Walls
    public static final Color wallAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color wallDiffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color wallSpecular= new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color wallEmission = new Color(0.2f, 0.2f, 0.2f, 1.0f);

    // Floor
    public static final Color floorAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color floorDiffuse = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color floorSpecular= new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color floorEmission = new Color(0.0f, 0.0f, 0.0f, 0.0f);

    // End point
    public static final Color endPointAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color endPointDiffuse = new Color(1.0f, 0f, 0f, 1.0f);
    public static final Color endPointSpecular= new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color endPointEmission = new Color(0.0f, 0.0f, 0.0f, 0.0f);

    // Player
    public static final Color playerAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color playerDiffuse = new Color(1.0f, 1.0f, 0f, 1.0f);
    public static final Color playerSpecular= new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color playerEmission = new Color(0.0f, 0.0f, 0.0f, 0.0f);

    // Light
    public static final float globalAmbience = 0f;
    public static final float lightShininess = 60f;
    public static final Color lightAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color lightDiffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color lightSpecular= new Color(1.0f, 1.0f, 1.0f, 1.0f);
}
