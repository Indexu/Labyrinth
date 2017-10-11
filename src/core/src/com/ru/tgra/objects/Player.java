package com.ru.tgra.objects;

import com.ru.tgra.Camera;
import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.models.Material;
import com.ru.tgra.models.ModelMatrix;
import com.ru.tgra.models.Point3D;
import com.ru.tgra.models.Vector3D;
import com.ru.tgra.utilities.CollisionsUtil;

public class Player extends GameObject
{
    private Camera camera;

    private float radius;
    private float offset = 0.5f;

    private Vector3D movementVector;
    private float yaw;

    public Player(Point3D position, Vector3D scale, float speed, Material material)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.speed = speed;
        this.material = material;

        radius = 0.25f;

        Point3D center = new Point3D(position);
        center.z += 1;

        camera = new Camera();
        camera.look(position, center, new Vector3D(0,1,0));

        movementVector = new Vector3D();
        yaw = 0;
    }

    public void update(float deltaTime)
    {
        movementVector.scale(deltaTime);
        yaw *= deltaTime;

        camera.slide(movementVector.x, movementVector.y, movementVector.z);
        camera.yaw(yaw);

        movementVector.set(0, 0, 0);
        yaw = 0;
    }

    public void draw(int viewportID)
    {
        if (viewportID == Settings.viewportIDPerspective)
        {
            return;
        }

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        GraphicsEnvironment.shader.setMaterial(material);

        SphereGraphic.drawSolidSphere();
    }

    public void moveLeft()
    {
        movementVector.x = -Settings.playerSpeed;
    }

    public void moveRight()
    {
        movementVector.x = Settings.playerSpeed;
    }

    public void moveForward()
    {
        movementVector.z = -Settings.playerSpeed;
    }

    public void moveBack()
    {
        movementVector.z = Settings.playerSpeed;
    }

    public void lookLeft()
    {
        yaw = Settings.playerLookSensitivity;
    }

    public void lookRight()
    {
        yaw = -Settings.playerLookSensitivity;
    }

    public Camera getCamera()
    {
        return camera;
    }

    public int getMazeX()
    {
        return (int) (position.x + 0.5f);
    }

    public int getMazeY()
    {
        return (int) (position.z + 0.5f);
    }

    public float getRadius()
    {
        return radius;
    }
}
