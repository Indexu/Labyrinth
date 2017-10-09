package com.ru.tgra.utilities;

public class Light
{
    private Point3D position;
    private Color color;

    public Light()
    {
        position = new Point3D();
        color = new Color();
    }

    public Light(Point3D position, Color color)
    {
        this.position = position;
        this.color = color;
    }

    public Point3D getPosition()
    {
        return position;
    }

    public void setPosition(Point3D position)
    {
        this.position = position;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }
}
