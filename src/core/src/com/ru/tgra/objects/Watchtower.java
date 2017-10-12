package com.ru.tgra.objects;

import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.models.*;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.utilities.MathUtils;
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

    private Vector3D direction;
    private Vector3D spotLightDirection;
    private Point3D spotLightCurrentPoint;
    private Point3D spotLightDestinationPoint;
    private float spotLightSpeed;
    private float mazeLength;

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

        mazeLength = GameManager.mazeWalls.length-2;

        spotLightCurrentPoint = new Point3D(mazeLength / 2, 0f, mazeLength / 2);

        Vector3D initialDirection = Vector3D.difference(spotLightCurrentPoint, orbPos);
        spotLight.setDirection(initialDirection);

        this.spotLightDirection = spotLight.getDirection();
        direction = new Vector3D();
        randomizeDirection();

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
        if (spotLightSpeed < 0.8f)
        {
            spotLightSpeed += deltaTime;

            if (0.8f < spotLightSpeed)
            {
                spotLightSpeed = 0.8f;
            }
        }

        Vector3D v = new Vector3D(direction);
        v.scale(deltaTime * spotLightSpeed);

        spotLightCurrentPoint.add(v);
        spotLightDirection.add(v);

        checkDirection();
    }

    private void checkDirection()
    {
        float x = spotLightDestinationPoint.x - spotLightCurrentPoint.x;
        float z = spotLightDestinationPoint.z - spotLightCurrentPoint.z;

        boolean sameVector = MathUtils.sameSignFloats(x, direction.x) && MathUtils.sameSignFloats(z, direction.z);

        if (!sameVector)
        {
            randomizeDirection();
        }
    }

    private void randomizeDirection()
    {
        spotLightDestinationPoint = RandomGenerator.randomPointInXZ(1f, mazeLength, 1f, mazeLength);

        //System.out.println("\nRandomized point: " + spotLightDestinationPoint);

        direction.x = spotLightDestinationPoint.x - spotLightCurrentPoint.x;
        direction.z = spotLightDestinationPoint.z - spotLightCurrentPoint.z;

        direction.normalize();
        spotLightSpeed = 0f;
    }
}
