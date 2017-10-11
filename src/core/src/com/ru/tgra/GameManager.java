package com.ru.tgra;

import com.ru.tgra.objects.Block;
import com.ru.tgra.objects.EndPoint;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.objects.Player;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;

    public static MazeGenerator mazeGenerator;
    public static boolean[][] mazeWalls;

    public static boolean mainMenu;

    public static Light headLight;
    public static Light endPointLight;
    public static Camera minimapCamera;
    public static Player player;

    private static Point3D endPointPos;
    private static int currentLevel;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();
        mazeGenerator = new MazeGenerator();
        currentLevel = 0;
    }

    public static void createMaze()
    {
        currentLevel++;

        gameObjects.clear();

        int sideLength = Settings.startSideLength + (Settings.sideLengthIncrement * (currentLevel - 1));

        generateMaze(sideLength);
        createFloor(new Point3D(sideLength / 2, -0.5f, sideLength / 2), sideLength);
        createWalls(sideLength);
        createEndPoint();
        createPlayer();
        createMinimap();
        createHeadLight();
        createEndPointLight();
    }

    public static void checkEndPoint()
    {
        int endPointX = (int)endPointPos.x;
        int endPointZ = (int)endPointPos.z;

        int playerX = player.getMazeX();
        int playerZ = player.getMazeY();

        if (playerX == endPointX && playerZ == endPointZ)
        {
            //createMaze();
        }
    }

    private static void createEndPointLight()
    {
        endPointLight = new Light();
        endPointLight.setID(1);
        endPointLight.setColor(Settings.endPointLightColor);
        endPointLight.setPosition(endPointPos, false);
        endPointLight.setDirection(Settings.endPointLightDirection);
        endPointLight.setSpotFactor(Settings.endPointLightSpotFactor);
        endPointLight.setConstantAttenuation(Settings.endPointConstantAttenuation);
        endPointLight.setLinearAttenuation(Settings.endPointLightLinearAttenuation);
        endPointLight.setQuadraticAttenuation(Settings.endPointLightQuadraticAttenuation);
    }

    private static void createHeadLight()
    {
        headLight = new Light();
        headLight.setID(0);
        headLight.setColor(Settings.helmetLightColor);
        headLight.setSpotFactor(Settings.helmetLightSpotFactor);
        headLight.setConstantAttenuation(Settings.helmetConstantAttenuation);
        headLight.setLinearAttenuation(Settings.helmetLightLinearAttenuation);
        headLight.setQuadraticAttenuation(Settings.helmetLightQuadraticAttenuation);
    }

    private static void createMinimap()
    {
        minimapCamera = new Camera();
        minimapCamera.setOrthographicProjection(-5, 5, -5, 5, 3f, 100);
    }

    private static void createPlayer()
    {
        player = new Player(new Point3D(mazeGenerator.getEnd()), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, Settings.playerMinimapMaterial);
        // player = new Player(mazeGenerator.getStart(), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, playerMat);
        gameObjects.add(player);
    }

    private static void createEndPoint()
    {
        endPointPos = mazeGenerator.getEnd();

        EndPoint endPoint = new EndPoint(endPointPos, new Vector3D(0.25f, 0.25f, 0.25f), Settings.endPointMaterial, Settings.endPointMinimapMaterial, new CubeMask());
        gameObjects.add(endPoint);
    }

    private static void createWalls(int sideLength)
    {
        Vector3D scale = new Vector3D(1f, 2f, 1f);

        for (int i = 0; i < sideLength; i++)
        {
            for (int j = 0; j < sideLength; j++)
            {
                if (mazeWalls[i][j])
                {
                    Point3D position = new Point3D(i, 0.5f, j);

                    /* === Cube mask === */
                    CubeMask mask = new CubeMask();
                    mask.setBottom(false);
                    mask.setTop(false);

                    // North
                    if (i != sideLength-1)
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
                    if (j != sideLength-1)
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

                    Block block = new Block(position, new Vector3D(scale), Settings.wallMaterial, Settings.wallMinimapMaterial, mask);

                    gameObjects.add(block);
                }
            }
        }
    }

    private static void createFloor(Point3D pos, float sideLength)
    {
        Block floor = new Block(pos, new Vector3D(sideLength, 0.01f, sideLength), Settings.floorMaterial, Settings.floorMinimapMaterial, new CubeMask(false, false, false, false, true, false));
        gameObjects.add(floor);
    }

    private static void generateMaze(int sideLength)
    {
        mazeGenerator.generateMaze(sideLength);
        mazeWalls = mazeGenerator.getWalls();
    }
}
