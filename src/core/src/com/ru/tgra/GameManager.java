package com.ru.tgra;

import com.ru.tgra.objects.Block;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static MazeGenerator mazeGenerator;
    public static boolean[][] mazeWalls;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();
        mazeGenerator = new MazeGenerator();
    }

    public static void createMaze()
    {
        mazeGenerator.generateMaze(Settings.width, Settings.height);
        mazeWalls = mazeGenerator.getWalls();

        Material wallMaterial = new Material(Settings.wallAmbience, Settings.wallDiffuse, Settings.wallSpecular, Settings.wallEmission, Settings.wallShininess, Settings.floorTransparency);
        Vector3D scale = new Vector3D(1f, 2f, 1f);

        for (int i = 0; i < Settings.height; i++)
        {
            for (int j = 0; j < Settings.width; j++)
            {
                if (mazeWalls[i][j])
                {
                    Point3D position = new Point3D(i, 0.5f, j);

                    /* === Cube mask === */
                    CubeMask mask = new CubeMask();
                    mask.setBottom(false);
                    mask.setTop(false);

                    // North
                    if (i != Settings.height-1)
                    {
                        mask.setNorth(!mazeWalls[i+1][j]);
                    }
                    else
                    {
                        mask.setNorth(false);
                    }

                    // South
                    if (i != 0)
                    {
                        mask.setSouth(!mazeWalls[i-1][j]);
                    }
                    else
                    {
                        mask.setSouth(false);
                    }

                    // East
                    if (j != Settings.width-1)
                    {
                        mask.setEast(!mazeWalls[i][j+1]);
                    }
                    else
                    {
                        mask.setEast(false);
                    }

                    // West
                    if (j != 0)
                    {
                        mask.setWest(!mazeWalls[i][j-1]);
                    }
                    else
                    {
                        mask.setWest(false);
                    }

                    Block block = new Block(position, new Vector3D(scale), wallMaterial, mask);

                    gameObjects.add(block);
                }
            }
        }
    }
}
