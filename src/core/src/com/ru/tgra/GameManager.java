package com.ru.tgra;

import com.ru.tgra.objects.GameObject;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();
    }
}
