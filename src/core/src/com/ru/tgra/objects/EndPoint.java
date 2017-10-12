package com.ru.tgra.objects;

import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.models.*;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.utilities.*;

public class EndPoint extends Block
{
    private float bob;

    public EndPoint(Point3D position, Vector3D scale, Material material, Material minimapMaterial, CubeMask mask)
    {
        super(position, scale, material, minimapMaterial, mask);
        this.rotation = new Vector3D();
        bob = 0f;
    }

    @Override
    public void draw(int viewportID)
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        if (viewportID == Settings.viewportIDPerspective)
        {
            ModelMatrix.main.addRotationX(rotation.x);
            ModelMatrix.main.addRotationY(rotation.y);
            ModelMatrix.main.addRotationZ(rotation.z);

            GraphicsEnvironment.shader.setMaterial(material);
        }
        else
        {
            GraphicsEnvironment.shader.setMaterial(minimapMaterial);
        }

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        GraphicsEnvironment.shader.setLight(GameManager.endPointLight);

        SphereGraphic.drawSolidPolySphere();
    }

    @Override
    public void update(float deltaTime)
    {
        rotation.x += RandomGenerator.randomFloatInRange(50f, 100f) * deltaTime;
        rotation.y += RandomGenerator.randomFloatInRange(50f, 100f) * deltaTime;
        rotation.z += RandomGenerator.randomFloatInRange(50f, 100f) * deltaTime;

        bob += deltaTime * Settings.bobbingSpeed;

        position.y += Math.sin(bob) * Settings.bobbingFactor * deltaTime;
    }
}
