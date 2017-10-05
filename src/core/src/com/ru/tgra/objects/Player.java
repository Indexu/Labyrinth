package com.ru.tgra.objects;

import com.ru.tgra.Camera;
import com.ru.tgra.GameManager;
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

        radius = scale.x * 0.5f;

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

        wallCollision();
    }

    public void draw(int viewportID)
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

    private void wallCollision()
    {
        int x = (int) (position.x + 0.5f);
        int y = (int) (position.z + 0.5f);

        float unitX = (position.x - ((float)(int)position.x) + 0.5f) % 1f;
        float unitZ = (position.z - ((float)(int)position.z) + 0.5f) % 1f;

        if (0 <= x && x < GameManager.mazeWalls.length && 0 <= y && y < GameManager.mazeWalls[0].length)
        {
            float left = unitX - radius;
            float right = unitX + radius;
            float top = unitZ - radius;
            float bottom = unitZ + radius;
            boolean collided = false;

            // Check left
            if (left < 0 && x != 0 && GameManager.mazeWalls[x-1][y])
            {
                position.x -= left;
                collided = true;
            }
            // Check right
            else if (1 < right && x != GameManager.mazeWalls.length-1 && GameManager.mazeWalls[x+1][y])
            {
                position.x -= (right % 1);
                collided = true;
            }

            // Check top
            if (top < 0 && y != 0 && GameManager.mazeWalls[x][y-1])
            {
                position.z -= top;
                collided = true;
            }
            // Check bottom
            else if (1 < bottom && y != GameManager.mazeWalls[0].length-1 && GameManager.mazeWalls[x][y+1])
            {
                position.z -= (bottom % 1);
                collided = true;
            }

            // Check diagonal
            if (collided)
            {
                // TODO
            }
        }
    }
}
