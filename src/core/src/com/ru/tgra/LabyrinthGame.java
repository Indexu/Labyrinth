package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.models.ModelMatrix;
import com.ru.tgra.models.Point3D;
import com.ru.tgra.models.Vector3D;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.shapes.BoxGraphic;
import com.ru.tgra.shapes.CoordFrameGraphic;
import com.ru.tgra.shapes.SincGraphic;
import com.ru.tgra.shapes.SphereGraphic;
import com.ru.tgra.utilities.CollisionsUtil;

public class LabyrinthGame extends ApplicationAdapter
{
	private Shader shader;

	private float oldMouseX;
	private float oldMouseY;

	@Override
	public void create ()
	{
		init();

        GameManager.mainMenu = false;
        GameManager.createMaze();
        GameManager.mazeGenerator.printMaze();
    }

    private void mainMenuInput()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            GameManager.mainMenu = false;
            GameManager.createMaze();
        }
    }

	private void input(float deltaTime)
	{
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		if (mouseX != oldMouseX)
		{
			//GameManager.player.getCamera().yaw((mouseX - oldMouseX) * deltaTime * -30);
			oldMouseX = mouseX;
		}

		if (mouseY != oldMouseY)
		{
			//GameManager.player.getCamera().pitch((mouseY - oldMouseY) * deltaTime * 30);
			oldMouseY = mouseY;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			GameManager.player.lookLeft();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			GameManager.player.lookRight();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
            GameManager.player.moveLeft();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
            GameManager.player.moveRight();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
            GameManager.player.moveForward();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
            GameManager.player.moveBack();
		}

		// Debug
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            GameManager.player.getCamera().pitch(Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            GameManager.player.getCamera().pitch(-Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E))
        {
            GameManager.player.getCamera().roll(Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q))
        {
            GameManager.player.getCamera().roll(-Settings.playerLookSensitivity * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            GameManager.player.getCamera().slide(0f, Settings.playerSpeed * deltaTime, 0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
        {
            GameManager.player.getCamera().slide(0f, -Settings.playerSpeed * deltaTime, 0f);
        }
	}

	private void update(float deltaTime)
	{
	    if (GameManager.mainMenu)
        {
            return;
        }

        for (GameObject gameObject : GameManager.gameObjects)
        {
            gameObject.update(deltaTime);
        }

        //CollisionsUtil.playerWallCollisions(GameManager.player);
        //CollisionsUtil.playerSpearCollision(GameManager.player);

	    GameManager.checkEndPoint();
	}

	private void display()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.graphics.setTitle("Labyrinth | FPS: " + Gdx.graphics.getFramesPerSecond());

        if (GameManager.mainMenu)
        {
            mainMenuInput();

            return;
        }

		for (int viewNum = 0; viewNum < 2; viewNum++)
		{
			if (viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				GameManager.player.getCamera().setPerspectiveProjection(Settings.playerFOV, Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), 0.1f, 100.0f);
				shader.setViewMatrix(GameManager.player.getCamera().getViewMatrix());
				shader.setProjectionMatrix(GameManager.player.getCamera().getProjectionMatrix());
				shader.setEyePosition(GameManager.player.getCamera().eye);
			}
			else
			{
			    Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
				Gdx.gl.glViewport((int)(Gdx.graphics.getWidth() * 0.65f), (int)(Gdx.graphics.getHeight() * 0.65f), Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
                GameManager.minimapCamera.look(new Point3D(GameManager.player.getCamera().eye.x, 30.0f, GameManager.player.getCamera().eye.z), GameManager.player.getPosition(), new Vector3D(0, 0, 1));
				shader.setViewMatrix(GameManager.minimapCamera.getViewMatrix());
				shader.setProjectionMatrix(GameManager.minimapCamera.getProjectionMatrix());
			}

            shader.setGlobalAmbience(Settings.globalAmbience);

			if (viewNum == Settings.viewportIDPerspective)
            {
                GameManager.headLight.setPosition(GameManager.player.getPosition(), true);
                GameManager.headLight.setDirection(new Vector3D(-GameManager.player.getCamera().n.x, -GameManager.player.getCamera().n.y, -GameManager.player.getCamera().n.z));
            }
            else
            {
                GameManager.headLight.getPosition().y = 3f;
                GameManager.headLight.getDirection().add(new Vector3D(0f, -1f, 0f));
            }

			for (GameObject gameObject : GameManager.gameObjects)
            {
                gameObject.draw(viewNum);
            }
		}

	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		input(deltaTime);
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
