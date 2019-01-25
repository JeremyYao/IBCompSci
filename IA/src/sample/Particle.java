package sample;

import javafx.scene.shape.Circle;

import java.util.Collection;

public class Particle extends Circle
{
    private double mass = 1;
    private double velocityX = 0;
    private double velocityY = 0;
    private double accelX = 0;
    private double accelY = 0;
    private double posX = 0;
    private double posY = 0;
    private static int fps = 60;
    static final double UNIV_GRAV_CONST = 6.67408 * Math.pow(10,-11);
    final double PARTICLE_ID;

    public Particle(double mass, double velocityX, double velocityY, double posX, double posY)
    {
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.posX = posX;
        this.posY = posY;
        this.PARTICLE_ID = Math.random();
    }

    public Particle(double mass, double posX, double posY)
    {
        this.mass = mass;
        this.posX = posX;
        this.posY = posY;
        PARTICLE_ID = Math.random();
    }

    public Particle(double mass)
    {
        this.mass = mass;
        PARTICLE_ID = Math.random();
    }

    public Particle()
    {
        PARTICLE_ID = Math.random();
    }

    private void updateAccel(Collection<Particle> allParticles)
    {
        accelX = 0;
        accelY = 0;

        for (Particle temp : allParticles)
        {
            if (temp.PARTICLE_ID != PARTICLE_ID)
            {
                double magDist = Math.sqrt(Math.pow(posX - temp.getPosX(), 2) + Math.pow(posY - temp.getPosY(), 2));
                double forceGrav = UNIV_GRAV_CONST * mass * temp.getMass() / Math.pow(magDist, 2);
                double angleBtwn = Math.atan2(temp.getPosY() - posY, temp.getPosX() - posX);
                accelY += Math.sin(angleBtwn) * forceGrav / mass;
                accelX += Math.cos(angleBtwn) * forceGrav / mass;
            }
        }
    }

    private void updateVelocities()
    {
        velocityX += accelX/fps;
        velocityY += accelY/fps;
    }

    public void updatePosition(Collection<Particle> allParticles)
    {
        updateAccel(allParticles);
        updateVelocities();

        posX += velocityX * 1/fps;
        posY += velocityY * 1/fps;
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public void setVelocityX(double velocityX)
    {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY)
    {
        this.velocityY = velocityY;
    }

    public void setAccelX(double accelX)
    {
        this.accelX = accelX;
    }

    public void setAccelY(double accelY)
    {
        this.accelY = accelY;
    }

    public void setPosX(double posX)
    {
        this.posX = posX;
    }

    public void setPosY(double posY)
    {
        this.posY = posY;
    }

    public void setFps(int fps)
    {
        this.fps = fps;
    }

    public double getMass()
    {
        return mass;
    }

    public double getVelocityX()
    {
        return velocityX;
    }

    public double getVelocityY()
    {
        return velocityY;
    }

    public double getAccelX()
    {
        return accelX;
    }

    public double getAccelY()
    {
        return accelY;
    }

    public double getPosX()
    {
        return posX;
    }

    public double getPosY()
    {
        return posY;
    }

    public int getFps()
    {
        return fps;
    }
}
