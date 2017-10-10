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
import com.sun.org.apache.xpath.internal.operations.Mod;

public class LabyrinthGame extends ApplicationAdapter
{
	private Shader shader;

	private Camera orthoCam;

	private Player player;

	private float oldMouseX;
	private float oldMouseY;

	private Light headLight;

	@Override
	public void create ()
	{
		init();
		GameManager.createMaze();

		// Light
		headLight = new Light();
		headLight.setID(0);
        headLight.setColor(Settings.helmetLightColor);
        headLight.setSpotFactor(Settings.helmetLightSpotFactor);
        headLight.setConstantAttenuation(Settings.helmetConstantAttenuation);
        headLight.setLinearAttenuation(Settings.helmetLightLinearAttenuation);
        headLight.setQuadraticAttenuation(Settings.helmetLightQuadraticAttenuation);

        // Floor
        Material floorMaterial = new Material(Settings.floorAmbience, Settings.floorDiffuse, Settings.floorSpecular, Settings.floorEmission, Settings.floorShininess, Settings.wallTransparency);
        Block floor = new Block(new Point3D(Settings.width / 2, -0.5f, Settings.height / 2), new Vector3D(Settings.width, 0.01f, Settings.height), floorMaterial, new CubeMask(false, false, false, false, true, false));
        GameManager.gameObjects.add(floor);

        // End point
        Material endPointMat = new Material(Settings.endPointAmbience, Settings.endPointDiffuse, Settings.endPointSpecular, Settings.endPointEmission, Settings.endPointShininess, Settings.endPointTransparency);

        Block endPoint = new Block(GameManager.mazeGenerator.getEnd(), new Vector3D(0.5f, 0.5f, 0.5f), endPointMat, new CubeMask());
        GameManager.gameObjects.add(endPoint);

        // Minimap
        orthoCam = new Camera();
        orthoCam.setOrthographicProjection(-5, 5, -5, 5, 3f, 100);

        // Player
        Material playerMat = new Material(Settings.playerAmbience, Settings.playerDiffuse, Settings.playerSpecular, Settings.playerEmission, Settings.playerShininess, Settings.playerTransparency);
        player = new Player(GameManager.mazeGenerator.getStart(), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, playerMat);
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
			player.lookLeft();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			player.lookRight();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
            player.moveLeft();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
            player.moveRight();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
            player.moveForward();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
            player.moveBack();
		}

		// Debug
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            player.getCamera().pitch(Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            player.getCamera().pitch(-Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E))
        {
            player.getCamera().roll(Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q))
        {
            player.getCamera().roll(-Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            player.getCamera().slide(0f, Settings.playerSpeed * deltaTime, 0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
        {
            player.getCamera().slide(0f, -Settings.playerSpeed * deltaTime, 0f);
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
				player.getCamera().setPerspectiveProjection(Settings.playerFOV, Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), 0.1f, 100.0f);
				shader.setViewMatrix(player.getCamera().getViewMatrix());
				shader.setProjectionMatrix(player.getCamera().getProjectionMatrix());
				shader.setEyePosition(player.getCamera().eye);
			}
			else
			{
			    Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
				Gdx.gl.glViewport((int)(Gdx.graphics.getWidth() * 0.65f), (int)(Gdx.graphics.getHeight() * 0.65f), Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
				orthoCam.look(new Point3D(player.getCamera().eye.x, 30.0f, player.getCamera().eye.z), player.getPosition(), new Vector3D(0, 0, 1));
				shader.setViewMatrix(orthoCam.getViewMatrix());
				shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
			}

            shader.setGlobalAmbience(Settings.globalAmbience);

			if (viewNum == Settings.viewportIDPerspective)
            {
                headLight.setPosition(player.getPosition());
                headLight.setDirection(new Vector3D(-player.getCamera().n.x, -player.getCamera().n.y, -player.getCamera().n.z));
            }
            else
            {
                headLight.getPosition().y = 3f;
                headLight.getDirection().add(new Vector3D(0f, -1f, 0f));
            }

            shader.setLight(headLight);

			for (GameObject gameObject : GameManager.gameObjects)
            {
                gameObject.draw(viewNum);
            }

			if (viewNum == Settings.viewportIDMinimap)
			{
                player.draw(viewNum);

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

	private void init()
	{
		GraphicsEnvironment.init();
		GameManager.init();

		shader = GraphicsEnvironment.shader;

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		oldMouseX = Gdx.input.getX();
		oldMouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
	}
}
