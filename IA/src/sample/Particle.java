package sample;

public class Particle
{
    private double mass, velocityX, velocityY, accelX, accelY;
    private static final double universalGravConst = 6.67408 * Math.pow(10,-11);

    public Particle(double mass, double velocityx, double velocityy)
    {
        this.mass = mass;
        this.velocityX = velocityx;
        this.velocityY = velocityy;
    }

    public void updateAccel()
    {

    }
}
