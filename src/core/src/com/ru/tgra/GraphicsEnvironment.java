package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ru.tgra.models.Color;
import com.ru.tgra.models.Point3D;

public class GraphicsEnvironment
{
    public static Shader shader;

    private static SpriteBatch batch;
    private static BitmapFont fontNormal;
    private static BitmapFont fontLarge;
    private static BitmapFont fontExtraLarge;
    private static GlyphLayout layout;

    public static void init()
    {
        shader = new Shader();
        initFonts();

        shader.setBrightness(1.0f);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void drawText(Point3D position, String text, Color color, int size)
    {
        BitmapFont fontToUse;

        switch (size)
        {
            default:
            case 1:
                fontToUse = fontNormal;
                break;

            case 2:
                fontToUse = fontLarge;
                break;

            case 3:
                fontToUse = fontExtraLarge;
                break;
        }

        layout = new GlyphLayout(fontToUse, text);

        float offsetX = layout.width /2;
        float offsetY = layout.height /2;

        float fontX = position.x - offsetX;
        float fontY = position.y + offsetY;

        batch.begin();

        fontToUse.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        fontToUse.draw(batch, text, fontX, fontY);

        batch.end();

        shader.setShader();
    }

    private static void initFonts()
    {
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/October Crow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 18;
        fontNormal = generator.generateFont(parameter);

        parameter.size = 32;
        fontLarge = generator.generateFont(parameter);

        parameter.size = 46;
        fontExtraLarge = generator.generateFont(parameter);

        generator.dispose();
    }
}
