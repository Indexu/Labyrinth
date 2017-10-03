package com.ru.tgra.objects;

import com.ru.tgra.Camera;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.utilities.Material;
import com.ru.tgra.utilities.ModelMatrix;
import com.ru.tgra.utilities.Point3D;
import com.ru.tgra.utilities.Vector3D;

public class Player extends GameObject
{
    private Camera camera;

    // Movement
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveForward;
    private boolean moveBack;
    private boolean lookLeft;
    private boolean lookRight;

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
        if (moveLeft)
        {
            camera.slide(-deltaTime * Settings.playerSpeed, 0, 0);
            moveLeft = false;
        }

        if (moveRight)
        {
            camera.slide(deltaTime * Settings.playerSpeed, 0, 0);
            moveRight = false;
        }

        if (moveForward)
        {
            camera.slide(0, 0, -deltaTime * Settings.playerSpeed);
            moveForward = false;
        }

        if (moveBack)
        {
            camera.slide(0, 0, deltaTime * Settings.playerSpeed);
            moveBack = false;
        }

        if (lookLeft)
        {
            camera.yaw(Settings.playerLookSensitivity * deltaTime);
            lookLeft = false;
        }

        if (lookRight)
        {
            camera.yaw(-Settings.playerLookSensitivity * deltaTime);
            lookRight = false;
        }
    }

    public void draw()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        GraphicsEnvironment.shader.setMaterial(material);

        BoxGraphic.drawSolidCube();
    }

    public void moveLeft()
    {
        moveLeft = true;
    }

    public void moveRight()
    {
        moveRight = true;
    }

    public void moveForward()
    {
        moveForward = true;
    }

    public void moveBack()
    {
        moveBack = true;
    }

    public void lookLeft()
    {
        lookLeft = true;
    }

    public void lookRight()
    {
        lookRight = true;
    }

    public Camera getCamera()
    {
        return camera;
    }
}
