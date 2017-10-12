package com.ru.tgra.utilities;

import com.ru.tgra.AudioManager;
import com.ru.tgra.GameManager;
import com.ru.tgra.models.Point3D;
import com.ru.tgra.models.Vector3D;
import com.ru.tgra.objects.Player;
import com.ru.tgra.objects.Spear;

import java.util.ArrayList;

public class CollisionsUtil
{
    public static void playerWallCollisions(Player player)
    {
        Point3D position = player.getPosition();
        float radius = player.getRadius();

        int x = player.getMazeX();
        int y = player.getMazeY();

        float unitX = (position.x - ((float)(int)position.x) + 0.5f) % 1f;
        float unitZ = (position.z - ((float)(int)position.z) + 0.5f) % 1f;

        if (0 <= x && x < GameManager.mazeWalls.length-1 && 0 <= y && y < GameManager.mazeWalls[0].length-1)
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
                int sideLength = GameManager.mazeWalls.length-1;
                // Top left
                if (x != sideLength && y != sideLength &&  GameManager.mazeWalls[x+1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (y + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Top right
                if (x != 0 && y != sideLength && GameManager.mazeWalls[x-1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (y + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom left
                if (x != sideLength && y != 0 && GameManager.mazeWalls[x+1][y-1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (y - 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom right
                if (x != 0 && y != 0 && GameManager.mazeWalls[x-1][y-1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (y - 0.5f));
                    cornerCollision(position, v, radius);
                }
            }
        }
    }

    public static void playerSpearCollision(Player player)
    {
        float playerRadius = player.getRadius();
        Point3D playerPosition = player.getPosition();

        for (Spear spear : GameManager.spears)
        {
            Point3D spearPosition = spear.getPosition();

            if ((!spear.isDown() && !spear.isFalling()) || (spear.isRetracting() && 1.5f < spearPosition.y))
            {
                continue;
            }

            Vector3D v = new Vector3D(playerPosition.x - spearPosition.x, 0f, playerPosition.z - spearPosition.z);
            float len = v.length();
            float combinedRadius = spear.getRadius() + playerRadius;

            if (len < combinedRadius)
            {
                if (spear.isFalling())
                {
                    GameManager.death();
                    break;
                }
                else
                {
                    v.normalize();
                    v.scale(combinedRadius - len);

                    playerPosition.add(v);
                }
            }
        }
    }

    private static void cornerCollision(Point3D position, Vector3D cornerVector, float radius)
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
