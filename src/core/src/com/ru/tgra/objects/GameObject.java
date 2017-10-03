package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.utilities.*;

public abstract class GameObject
{
    protected Point3D position;
    protected Vector3D direction;
    protected float speed;
    protected float rotation;
    protected Vector3D scale;
    protected Material material;
    protected boolean destroyed;

    public GameObject()
    {
        position = new Point3D();
        direction = new Vector3D();
        speed = 0;
        rotation = 0;
        scale = new Vector3D();
        material = new Material();
        destroyed = false;
    }

    public GameObject(Point3D position, Vector3D direction, float speed, float rotation, Vector3D scale, Material material)
    {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;

        destroyed = false;
    }

    public abstract void draw();

    public abstract void update(float deltaTime);

    public void destroy()
    {
        destroyed = true;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public Point3D getPosition()
    {
        return position;
    }

    public void setPosition(Point3D position)
    {
        this.position = position;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction)
    {
        this.direction = direction;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Vector3D getScale()
    {
        return scale;
    }

    public void setScale(Vector3D scale)
    {
        this.scale = scale;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }
}
