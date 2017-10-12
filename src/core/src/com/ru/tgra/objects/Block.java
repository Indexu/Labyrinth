package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.models.*;
import com.ru.tgra.shapes.BoxGraphic;

public class Block extends GameObject
{
    protected CubeMask mask;
    protected CubeMask minimapMask;

    public Block(Point3D position, Vector3D scale, Material material, Material minimapMaterial, CubeMask mask)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.material = material;
        this.minimapMaterial = minimapMaterial;
        this.mask = mask;

        minimapMask = new CubeMask(false, false, false, false, true, false);
    }

    public void draw(int viewportID)
    {
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
    }

    public void update(float deltaTime)
    {
        // Do nothing
    }
}
