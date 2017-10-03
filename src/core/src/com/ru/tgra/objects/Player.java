package com.ru.tgra.objects;

import com.ru.tgra.Camera;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.utilities.Material;
import com.ru.tgra.utilities.ModelMatrix;
import com.ru.tgra.utilities.Point3D;
import com.ru.tgra.utilities.Vector3D;

public class Player extends GameObject
{
    private Camera camera;

    public Player(Point3D position, Vector3D scale, float speed, Material material)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.speed = speed;
        this.material = material;

        Point3D center = new Point3D(position);
        center.z += 1;

        camera = new Camera();
        camera.look(position, center, new Vector3D(0,1,0));
    }

    public void update(float deltaTime)
    {

    }

    public void draw()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position.x, position.y, position.z);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        GraphicsEnvironment.shader.setMaterial(material);

        BoxGraphic.drawSolidCube();
    }

    public Camera getCamera()
    {
        return camera;
    }
}
