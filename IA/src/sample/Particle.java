package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Particle extends Circle
{
    private double mass = 1;
    private double velocityX = 0;
    private double velocityY = 0;
    private double accelX = 0;
    private double accelY = 0;
    private double positionX = 0;
    private double positionY = 0;
    public static final double UNIVERSAL_GRAVITATIONAL_CONST = 6.67408 * Math.pow(10,-11);
    public final double PARTICLE_ID;

    public Particle(double mass, double velocityX, double velocityY, double posX, double posY)
    {
        super(posX, posY, 3);
        super.setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = posX;
        this.positionY = posY;
        this.PARTICLE_ID = Math.random();
    }

    public Particle(double mass, double posX, double posY)
    {
        super(posX, posY, 5);
        super.setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        this.positionX = posX;
        this.positionY = posY;
        PARTICLE_ID = Math.random();
    }

    public Particle(double mass)
    {
        super(5);
        super.setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        PARTICLE_ID = Math.random();
    }

    public Particle()
    {
        PARTICLE_ID = Math.random();
    }

    public static String generateRandomHexColor()
    {
        String finalColorHex = "#";
        for (int i = 0; i < 3; i++)
        {
            int randInt = (int)(Math.random() * 256);

            if (randInt > 15)
                finalColorHex += Integer.toHexString(randInt);
            else
                finalColorHex += "0" + Integer.toHexString(randInt);
        }
        return finalColorHex;
    }

    private void updateAcceleration(Particle[] allParticles)
    {
        accelX = 0;
        accelY = 0;

        for (Particle temp : allParticles)
        {
            if (temp.PARTICLE_ID != this.PARTICLE_ID)
            {
                double magDist = Math.sqrt((this.positionX - temp.getPositionX()) * (this.positionX - temp.getPositionX()) + (this.positionY - temp.getPositionY()) * (this.positionY - temp.getPositionY()));
                double angleBtwn = Math.atan2(temp.getPositionY() - this.positionY, temp.getPositionX() - this.positionX);
                accelY += Math.sin(angleBtwn) * UNIVERSAL_GRAVITATIONAL_CONST * temp.getMass() / magDist / magDist;
                accelX += Math.cos(angleBtwn) * UNIVERSAL_GRAVITATIONAL_CONST * temp.getMass() / magDist / magDist;
            }
        }

        //System.out.println("accel x and y " + accelX + " " + accelY);
    }

    private void updateVelocities()
    {
        velocityX += accelX/60.0;
        velocityY += accelY/60.0;

//        System.out.println("velocity x " + velocityX);
//        System.out.println("velocity x " + velocityY);
    }

    public void updatePosition(Particle[] allParticles)
    {
        updateAcceleration(allParticles);
        updateVelocities();

        positionX += velocityX/60.0;
        positionY += velocityY/60.0;

//        System.out.println("pos x " + positionX);
//        System.out.println("pos y " + positionY);
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

    public void setPositionX(double positionX)
    {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY)
    {
        this.positionY = positionY;
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

    public double getPositionX()
    {
        return positionX;
    }

    public double getPositionY()
    {
        return positionY;
    }
}
