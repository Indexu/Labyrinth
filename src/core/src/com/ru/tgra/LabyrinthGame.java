package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.objects.Block;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.objects.Player;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.shapes.CoordFrameGraphic;
import com.ru.tgra.shapes.SincGraphic;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.utilities.*;

import java.util.Random;
import java.util.Set;

public class LabyrinthGame extends ApplicationAdapter
{
	private Shader shader;

	private Camera orthoCam;

	private Player player;

	private float oldMouseX;
	private float oldMouseY;

	private Random rand;
	private float angle = 0;

	private Light light;
	private Material material;
	private Color minimapColor;

	@Override
	public void create ()
	{
		GraphicsEnvironment.init();
		GameManager.init();

		shader = GraphicsEnvironment.shader;

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		rand = new Random();

		oldMouseX = Gdx.input.getX();
		oldMouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		Point3D lightPos = new Point3D(0, 10.0f, 0);
		float lightShininess = 30f;
		Color lightAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        Color lightDiffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        Color lightSpecular = new Color(1.0f, 1.0f, 1.0f, 1.0f);

		light = new Light(lightPos, lightAmbience, lightDiffuse, lightSpecular, lightShininess);

        Color materialAmbience = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        Color materialDiffuse = new Color(0.44f, 0.0f, 0.89f, 1.0f);
        Color materialSpecular = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        Color materialEmission = new Color(0.0f, 0.0f, 0.0f, 0.0f);

        material = new Material(materialAmbience, materialDiffuse, materialSpecular, materialEmission);

        minimapColor = new Color(0.2f, 0.6f, 0.8f, 1.0f);

        MazeGenerator mazeGenerator = new MazeGenerator(Settings.width, Settings.height);

        boolean[][] mazeWalls = mazeGenerator.getWalls();

        Vector3D scale = new Vector3D(1f, 2f, 1f);

        for (int i = 0; i < Settings.width; i++)
        {
            for (int j = 0; j < Settings.height; j++)
            {
                if (mazeWalls[i][j])
                {
                    Point3D position = new Point3D(i, 0, j);

                    Block block = new Block(position, new Vector3D(scale), material);

                    GameManager.gameObjects.add(block);
                }
            }
        }

        // Floor
        Block floor = new Block(new Point3D(Settings.width / 2, -0.5f, Settings.height / 2), new Vector3D(Settings.width, 0.01f, Settings.height), material);
        GameManager.gameObjects.add(floor);

        // End point
        Material endPointMat = new Material(material);
        endPointMat.setDiffuse(new Color(1.0f, 0f, 0f, 1.0f));

        Block endPoint = new Block(mazeGenerator.getEnd(), new Vector3D(0.5f, 0.5f, 0.5f), endPointMat);
        GameManager.gameObjects.add(endPoint);

        // Minimap
        orthoCam = new Camera();
        orthoCam.setOrthographicProjection(-10, 10, -10, 10, 3f, 100);

        Material playerMat = new Material(material);
        playerMat.setDiffuse(new Color(1.0f, 1.0f, 0f, 1.0f));

        player = new Player(mazeGenerator.getStart(), new Vector3D(0.5f, 1f, 0.5f), Settings.playerSpeed, playerMat);
	}

	private void input(float deltaTime)
	{
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		if (mouseX != oldMouseX)
		{
			//player.getCamera().yaw((mouseX - oldMouseX) * deltaTime * -30);
			oldMouseX = mouseX;
		}

		if (mouseY != oldMouseY)
		{
			//player.getCamera().pitch((mouseY - oldMouseY) * deltaTime * 30);
			oldMouseY = mouseY;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			player.getCamera().yaw(Settings.playerLookSensitivity * deltaTime);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			player.getCamera().yaw(-Settings.playerLookSensitivity * deltaTime);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			player.getCamera().slide(-deltaTime * Settings.playerSpeed, 0, 0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			player.getCamera().slide(deltaTime * Settings.playerSpeed, 0, 0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			player.getCamera().slide(0, 0, -deltaTime * Settings.playerSpeed);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			player.getCamera().slide(0, 0, deltaTime * Settings.playerSpeed);
		}
	}

	private void update(float deltaTime)
	{
		//angle += 180.0f * deltaTime;

		//do all updates to the game
        player.update(deltaTime);
	}

	private void display()
	{
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.graphics.setTitle("Labyrinth | FPS: " + Gdx.graphics.getFramesPerSecond());

		for (int viewNum = 0; viewNum < 2; viewNum++)
		{
			if (viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				player.getCamera().setPerspectiveProjection(Settings.playerFOV, Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), 0.1f, 10000000.0f);
				shader.setViewMatrix(player.getCamera().getViewMatrix());
				shader.setProjectionMatrix(player.getCamera().getProjectionMatrix());
				shader.setEyePosition(player.getCamera().eye);
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
				// player.getCamera().setPerspectiveProjection(fov, 1.0f, 0.1f, 10000000.0f);
				orthoCam.look(new Point3D(player.getCamera().eye.x, 20.0f, player.getCamera().eye.z), player.getCamera().eye, new Vector3D(0, 0, -1));
				shader.setViewMatrix(orthoCam.getViewMatrix());
				shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
			}

            shader.setGlobalAmbience(0f);
			shader.setLight(light);
			shader.setMaterial(material);

			for (GameObject gameObject : GameManager.gameObjects)
            {
                gameObject.draw();
            }

			if (viewNum == 1)
			{
                player.draw();
			}
		}

	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		input(deltaTime);
		//put the code inside the update and display methods, depending on the nature of the code
		update(deltaTime);
		display();

	}
}
