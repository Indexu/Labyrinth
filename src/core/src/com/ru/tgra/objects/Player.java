package com.ru.tgra.objects;

import com.ru.tgra.Camera;
import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.shapes.SphereGraphic;
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

        wallCollision();

        movementVector.set(0, 0, 0);
        yaw = 0;
    }

    public void draw(int viewportID)
    {
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

    private void wallCollision()
    {
        int x = (int) (position.x + 0.5f);
        int y = (int) (position.z + 0.5f);

        float unitX = (position.x - ((float)(int)position.x) + 0.5f) % 1f;
        float unitZ = (position.z - ((float)(int)position.z) + 0.5f) % 1f;

        if (0 <= x && x < GameManager.mazeWalls.length && 0 <= y && y < GameManager.mazeWalls[0].length)
        {
            float left = unitX + radius;
            float right = unitX - radius;
            float top = unitZ + radius;
            float bottom = unitZ - radius;
            boolean collided = false;

            // Check right
            if (right < 0 && x != 0 && GameManager.mazeWalls[x-1][y])
            {
                position.x -= right;
                collided = true;
            }
            // Check left
            else if (1 < left && x != GameManager.mazeWalls.length-1 && GameManager.mazeWalls[x+1][y])
            {
                position.x -= (left % 1);
                collided = true;
            }

            // Check bottom
            if (bottom < 0 && y != 0 && GameManager.mazeWalls[x][y-1])
            {
                position.z -= bottom;
                collided = true;
            }
            // Check top
            else if (1 < top && y != GameManager.mazeWalls[0].length-1 && GameManager.mazeWalls[x][y+1])
            {
                position.z -= (top % 1);
                collided = true;
            }

            // Check diagonal
            if (!collided)
            {
                // Top left
                if (GameManager.mazeWalls[x+1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (y + 0.5f));
                    cornerCollision(v);
                }

                // Top right
                if (GameManager.mazeWalls[x-1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (y + 0.5f));
                    cornerCollision(v);
                }

                // Bottom left
                if (GameManager.mazeWalls[x+1][y-1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (y - 0.5f));
                    cornerCollision(v);
                }

                // Bottom right
                if (GameManager.mazeWalls[x-1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (y - 0.5f));
                    cornerCollision(v);
                }
            }
        }
    }

    private void cornerCollision(Vector3D cornerVector)
    {
        float distance = cornerVector.length();

        if (distance < radius)
        {
            cornerVector.divide(distance);
            cornerVector.scale(radius - distance);

            position.add(cornerVector);
        }
    }
}
