package com.ru.tgra.utilities;

public class Material
{
    private Color ambience;
    private Color diffuse;
    private Color specular;
    private Color emission;

    public Material()
    {
        ambience = new Color();
        diffuse = new Color();
        specular = new Color();
        emission = new Color();
    }

    public Material(Material material)
    {
        this.ambience = new Color(material.ambience);
        this.diffuse = new Color(material.diffuse);
        this.specular = new Color(material.specular);
        this.emission = new Color(material.emission);
    }

    public Material(Color ambience, Color diffuse, Color specular, Color emission)
    {
        this.ambience = ambience;
        this.diffuse = diffuse;
        this.specular = specular;
        this.emission = emission;
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

    public Color getSpecular()
    {
        return specular;
    }

    public void setSpecular(Color specular)
    {
        this.specular = specular;
    }

    public Color getEmission()
    {
        return emission;
    }

    public void setEmission(Color emission)
    {
        this.emission = emission;
    }
}
