package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.models.*;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.utilities.RandomGenerator;

public class Watchtower extends GameObject
{
    private CubeMask mask;
    private CubeMask minimapMask;

    private Material orbMaterial;
    private Material orbMinimapMaterial;
    private Vector3D orbScale;
    private Light spotLight;
    private Point3D orbPos;

    public Watchtower(Point3D position,
                      Vector3D towerScale,
                      Vector3D orbScale,
                      Material towerMaterial,
                      Material towerMinimapMaterial,
                      Material orbMaterial,
                      Material orbMinimapMaterial,
                      CubeMask mask,
                      Light spotLight)
    {
        super();

        this.position = position;
        this.scale = towerScale;
        this.material = towerMaterial;
        this.minimapMaterial = towerMinimapMaterial;
        this.orbMaterial = orbMaterial;
        this.orbMinimapMaterial = orbMinimapMaterial;
        this.orbScale = orbScale;
        this.mask = mask;
        this.spotLight = spotLight;
        this.orbPos = spotLight.getPosition();

        minimapMask = new CubeMask(false, false, false, false, true, false);
    }

    public void draw(int viewportID)
    {
        GraphicsEnvironment.shader.setLight(spotLight);

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        if (viewportID == Settings.viewportIDMinimap)
        {
            GraphicsEnvironment.shader.setMaterial(minimapMaterial);
            BoxGraphic.drawSolidCube(minimapMask);
        }
        else
        {
            GraphicsEnvironment.shader.setMaterial(material);
            BoxGraphic.drawSolidCube(mask);
        }

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(orbPos);
        ModelMatrix.main.addScale(orbScale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        if (viewportID == Settings.viewportIDMinimap)
        {
            GraphicsEnvironment.shader.setMaterial(orbMinimapMaterial);
        }
        else
        {
            GraphicsEnvironment.shader.setMaterial(orbMaterial);
        }

        SphereGraphic.drawSolidSphere();
    }

    public void update(float deltaTime)
    {
        float amount = RandomGenerator.randomFloatInRange(-5f, 5f) * deltaTime;
        if (RandomGenerator.nextBool())
        {
            spotLight.getDirection().x += amount;
        }
        else
        {
            spotLight.getDirection().z += amount;
        }
    }
}
