package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.utilities.*;

public class Block extends GameObject
{
    private CubeMask mask;
    private CubeMask minimapMask;

    public Block(Point3D position, Vector3D scale, Material material, CubeMask mask)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.material = material;
        this.mask = mask;

        minimapMask = new CubeMask(false, false, false, false, true, false);
    }

    public void draw(int viewportID)
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setMaterial(material);
        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        if (viewportID == Settings.viewportIDMinimap)
        {
            BoxGraphic.drawSolidCube(minimapMask);
        }
        else
        {
            BoxGraphic.drawSolidCube(mask);
        }
    }

    public void update(float deltaTime)
    {
        // Do nothing
    }

    public CubeMask getMask()
    {
        return mask;
    }
}
