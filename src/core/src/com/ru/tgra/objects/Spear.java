package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.models.Material;
import com.ru.tgra.models.ModelMatrix;
import com.ru.tgra.models.Point3D;
import com.ru.tgra.models.Vector3D;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.utilities.RandomGenerator;

public class Spear extends GameObject
{
    private Vector3D boxScale;
    private float radius;
    private float timer;
    private boolean falling;
    private boolean paused;
    private boolean retracting;
    private boolean isDown;
    private boolean active;
    private float activateTime;

    public Spear(Point3D position, Vector3D scale, Material material, Material minimapMaterial)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.material = material;
        this.minimapMaterial = minimapMaterial;

        boxScale = new Vector3D(scale);
        boxScale.x *= 2f;
        boxScale.z *= 2f;

        radius = boxScale.x * 2;

        activateTime = RandomGenerator.randomFloatInRange(3f, 10f);

        timer = 0f;
        falling = false;
        paused = true;
        retracting = false;
        isDown = false;
        active = false;
    }

    public void draw(int viewportID)
    {
        if (viewportID == Settings.viewportIDMinimap)
        {
            GraphicsEnvironment.shader.setMaterial(minimapMaterial);
        }
        else
        {
            GraphicsEnvironment.shader.setMaterial(material);
        }

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        SphereGraphic.drawSolidSpear();

        ModelMatrix.main.popMatrix();
        ModelMatrix.main.addScale(boxScale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        BoxGraphic.drawSolidCube();
    }

    public void update(float deltaTime)
    {
        if (!active)
        {
            timer += deltaTime;

            if (activateTime < timer)
            {
                timer = 0f;
                active = true;
            }

            return;
        }

        // Paused
        if (paused)
        {
            timer += deltaTime;

            if (Settings.spearPauseTime <= timer)
            {
                timer = 0f;
                paused = false;

                if (isDown)
                {
                    retracting = true;
                }
                else
                {
                    falling = true;
                }
            }
        }
        // Falling
        else if (falling)
        {
            position.y -= deltaTime * Settings.spearFallSpeed;

            if (position.y < Settings.spearGroundY)
            {
                falling = false;
                isDown = true;
                paused = true;
                position.y = Settings.spearGroundY;
            }
        }
        // Retracting
        else if (retracting)
        {
            position.y += deltaTime * Settings.spearRetractSpeed;

            if (Settings.spearUpY < position.y)
            {
                paused = true;
                isDown = false;
                retracting = false;
                position.y = Settings.spearUpY;
            }
        }
    }

    public float getRadius()
    {
        return radius;
    }

    public boolean isFalling()
    {
        return falling;
    }

    public boolean isDown()
    {
        return isDown;
    }

    public boolean isRetracting()
    {
        return retracting;
    }
}
