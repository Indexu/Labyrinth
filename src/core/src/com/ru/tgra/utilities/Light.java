package com.ru.tgra.utilities;

public class Light
{
    private Point3D position;
    private Color ambience;
    private Color diffuse;
    private Color specualar;
    private float shininess;

    public Light()
    {
        position = new Point3D();
        ambience = new Color();
        diffuse = new Color();
        specualar = new Color();
        shininess = 0f;
    }

    public Light(Point3D position, Color ambience, Color diffuse, Color specualar, float shininess)
    {
        this.position = position;
        this.ambience = ambience;
        this.diffuse = diffuse;
        this.specualar = specualar;
        this.shininess = shininess;
    }

    public Point3D getPosition()
    {
        return position;
    }

    public void setPosition(Point3D position)
    {
        this.position = position;
    }

    public Color getAmbience()
    {
        return ambience;
    }

    public void setAmbience(Color ambience)
    {
        this.ambience = ambience;
    }

    public Color getDiffuse()
    {
        return diffuse;
    }

    public void setDiffuse(Color diffuse)
    {
        this.diffuse = diffuse;
    }

    public Color getSpecualar() {
        return specualar;
    }

    public void setSpecualar(Color specualar)
    {
        this.specualar = specualar;
    }

    public float getShininess()
    {
        return shininess;
    }

    public void setShininess(float shininess)
    {
        this.shininess = shininess;
    }
}
