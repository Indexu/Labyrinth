package com.ru.tgra;

import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Material;
import com.ru.tgra.utilities.Point3D;
import com.ru.tgra.utilities.Vector3D;

public class Settings
{
    /* === Viewport IDs === */
    public static final int viewportIDPerspective = 0;
    public static final int viewportIDMinimap = 1;

    /* === Maze settings === */
    // Side length must be an odd number and increment must be an even number
    public static final int startSideLength = 15;
    public static final int sideLengthIncrement = 4;

    /* === Player settings === */
    public static final float playerFOV = 60f;
    public static final float playerSpeed = 5f;
    public static final float playerLookSensitivity = 100f;

    /* === End point settings === */
    public static final float bobbingSpeed = 4f;
    public static final float bobbingFactor = 0.4f;

    /* === Materials === */
    // Walls
    public static final Material wallMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    public static final Material wallMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.5f, 0.5f, 0.5f, 0.5f), // Emission
            128f,
            1f
    );

    // Floor
    public static final Material floorMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.5f, 0.5f, 0.5f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            Float.MAX_VALUE,
            1f
    );

    public static final Material floorMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.5f, 0.5f, 0.5f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            Float.MAX_VALUE,
            1f
    );

    // End point
    public static final Material endPointMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 0.0f, 0.0f, 1.0f), // Diffuse
            new Color(0.5f, 0.5f, 0.5f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            32f,
            1f
    );
    public static final Material endPointMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 0.0f, 0.0f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(1.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    // Player
    public static final Material playerMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 0.0f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(1.0f, 1.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    /* === Lights === */
    public static final int numberOfLights = 2; // !!!MUST MATCH IN THE SHADER!!!
    public static final Color globalAmbience = new Color(0.5f, 0.0f, 0.0f, 1.0f);

    // Helmet light
    public static final Color helmetLightColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final float helmetLightSpotFactor = 7.0f;
    public static final float helmetConstantAttenuation = 0.3f;
    public static final float helmetLightLinearAttenuation = 0.7f;
    public static final float helmetLightQuadraticAttenuation = 0.3f;

    // End point light
    public static final Color endPointLightColor = new Color(1.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector3D endPointLightDirection = new Vector3D(0, -1, 0);
    public static final float endPointLightSpotFactor = 5.0f;
    public static final float endPointConstantAttenuation = 0.0f;
    public static final float endPointLightLinearAttenuation = 0f;
    public static final float endPointLightQuadraticAttenuation = 2f;
}
