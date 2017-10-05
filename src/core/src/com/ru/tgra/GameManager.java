package com.ru.tgra;

import com.ru.tgra.objects.GameObject;
import com.ru.tgra.utilities.MazeGenerator;
import com.ru.tgra.utilities.Point3D;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static MazeGenerator mazeGenerator;
    public static boolean[][] mazeWalls;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();

        mazeGenerator = new MazeGenerator(Settings.width, Settings.height);
        mazeWalls = mazeGenerator.getWalls();
    }
}
