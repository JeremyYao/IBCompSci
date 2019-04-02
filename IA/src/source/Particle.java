package source;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Particle extends Circle
{
    private double mass;
    private double velocityX;
    private double velocityY;
    private double accelX;
    private double accelY;
    private double positionX;
    private double positionY;
    private static double updateFreq = 60; //Desired frequency updating particle location
    public static final double UNIVERSAL_GRAVITATIONAL_CONSTANT = 6.67408 * Math.pow(10,-11);
    public final double PARTICLE_ID; //Unique identifying floating point number for particle

    /**
     * Constructs a new Particle object representing a particle floating in outer space with a mass, a velocity, and
     * an initial position relative to the origin (0,0) located in the bottom left of the simulation window.
     *
     * @param mass The mass of the particle in Kilograms.
     * @param velocityX The X component of the particle's velocity
     * @param velocityY The Y component of the particle's velocity
     * @param posX Initial X coordinate of the particle.
     * @param posY Initial Y coordinate of the particle.
     */
    public Particle(double mass, double velocityX, double velocityY, double posX, double posY)
    {
        super(posX, posY, Math.pow(mass/Math.pow(10, 15), 1.0/5) * 3);

        //set minimum radius
        if(mass/Math.pow(10, 15) < 1)
            this.setRadius(3);

        setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = posX;
        this.positionY = posY;
        this.PARTICLE_ID = Math.random();
    }

    /**
     * Returns a randomly genernated hexadecimal color
     *
     * @return
     */
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

    /**
     * Updates the acceleration on the particle, in both the X and Y direction, by calculating all gravitational
     * forces exerted on the current particle by other instances of Particle in the simulation.
     *
     * @param allParticles array containing all instances of Particle
     */
    private void updateAcceleration(Particle[] allParticles)
    {
        accelX = 0;
        accelY = 0;

        for (Particle temp : allParticles)
        {
            if (temp.PARTICLE_ID != this.PARTICLE_ID)
            {
                double distBtwn = Math.sqrt(Math.pow((this.positionX - temp.getPositionX()), 2)
                        +  Math.pow((this.positionY - temp.getPositionY()), 2));
                double angleBtwn = Math.atan2(temp.getPositionY() - this.positionY, temp.getPositionX() - this.positionX);
                accelY += Math.sin(angleBtwn) * UNIVERSAL_GRAVITATIONAL_CONSTANT * temp.getMass() / Math.pow(distBtwn, 2);
                accelX += Math.cos(angleBtwn) * UNIVERSAL_GRAVITATIONAL_CONSTANT * temp.getMass() / Math.pow(distBtwn, 2);
            }
        }
    }

    /**
     * Updates the current X and Y velocities of the particle based on its X and Y acceleration and update frequency.
     */
    private void updateVelocity()
    {
        velocityX += accelX/ updateFreq;
        velocityY += accelY/ updateFreq;
    }

    /**
     * Updates the current position of the particle, based on its X and Y components of velocity, and
     * relocates it within the simulation it's running in.
     *
     *  @param allParticles array containing all instances of Particle
     */
    public void updatePosition(Particle[] allParticles)
    {
        updateAcceleration(allParticles);
        updateVelocity();

        positionX += velocityX/ updateFreq;
        positionY += velocityY/ updateFreq;

        //Relocates particle relative to (0,0) located in the bottom left of the simulation window.
        //Re-adjust Y position from JavaFX's so that an increase in Y means that particle
        // is further up relative to window.
        relocate(positionX, 695 - positionY);
    }

    //Accessor Methods
    public double getMass()
    {
        return mass;
    }

    public double getPositionX()
    {
        return positionX;
    }

    public double getPositionY()
    {
        return positionY;
    }

    public static double getUpdateFreq() {
        return updateFreq;
    }

    //Setter Methods
    public static void setUpdateFreq(double updateFreq)
    {
        Particle.updateFreq = updateFreq;
    }
}
