package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.utilities.Material;
import com.ru.tgra.utilities.ModelMatrix;
import com.ru.tgra.utilities.Point3D;
import com.ru.tgra.utilities.Vector3D;

public class Block extends GameObject
{
    public Block(Point3D position, Vector3D scale, Material material)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.material = material;
    }

    public void draw()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setMaterial(material);
        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        BoxGraphic.drawSolidCube();
    }

    public void update(float deltaTime)
    {
        // Do nothing
    }
}
