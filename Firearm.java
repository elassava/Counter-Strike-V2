package csgo_v2;

import java.util.Random;

public class Firearm extends RangedWeapon {
    private double misfireRate;

    Firearm(String name) {
        super(name);

        this.setMaxAmmo(20); // All firearms have 20 bullets in it's magazine. (Except sniper rifle and machine gun.)
        this.setMisfireRate(0.3); // All firearms have a misfire rate of 0.3. (Except sniper rifle and machine gun.)

        this.setDamagePerHit(30); // Set damage here.
        this.setAmmo(this.getMaxAmmo()); // Set initial ammo here.
        this.setRange(50); // Set range here.
        this.setCost(250); // Set cost here.
    }

    @Override
    public void useWeapon(Player player, Player target) { /* Overrides the useWeapon method. Change(s) made:
    -Since all firearms have a misfire ratio, I've implemented a piece of code to figure out if the gun misfired.*/

        double distance; // Calculates the distance between the player and target.
        distance = Math.sqrt(Math.pow((target.getxAxis() - player.getxAxis()), 2) + Math.pow((target.getyAxis() - player.getyAxis()),2));

        if (target.isTerrorist() != player.isTerrorist()) { // Checks if target is on same side or not.
            if (distance <= this.getRange()) { //Checks if target is in range.
                if (this.getAmmo() > 0) { // Checks if Player has enough ammos.
                    if (target.getHealth() > target.getMinHealth()) { // Checks if target is alive.
                        Random random = new Random(); // Initializing a random class to create random double values between 0.0 and 1.0.

                        double misfireRatio = this.getMisfireRate();
                        double randNum = random.nextDouble();

                        if (randNum < misfireRatio) { // Checks if gun misfired.
                            System.out.println(
                                    "Failed! " + player.getName() + "'s " + this.getName() + " misfired!");

                        } else {

                            double remainingHealth = target.getHealth() - this.getDamagePerHit();
                            this.decreaseAmmo(getAmmoDecreaseRate()); // Updates the ammo.
                            target.decreaseHealth(this.getDamagePerHit()); // Updates the target's health.

                            // Prints statements.
                            if (remainingHealth <= 0) System.out.println(
                                    "Success! Perfect hit!\n" +
                                            "Info: " + player.getName() + " killed " + target.getName() + "!\n " +
                                            "Info: There are " + this.getAmmo() + " bullet(s) in " + player.getName() + "'s weapon.");

                            else if (remainingHealth > 0) System.out.println((
                                    "Success! Perfect hit!\n" +
                                            "Info: " + target.getName() + "'s health level is decreased to " + target.getHealth() + ".\n") +
                                    "Info: There are " + this.getAmmo() + " bullet(s) in " + player.getName() + "'s weapon.");
                        }
                    } else System.out.println("Failed! " + target.getName() + " is already dead!");
                } else
                    System.out.println("Failed! No bullets left in " + player.getName() + "'s " + this.getName() + "!");
            } else System.out.println("Failed! " + target.getName() + " is out of range.");
        } else System.out.println("Failed !" + target.getName() + " is on the same side!");
    }

    // Getter/Setter methods are initialized down below.
    public double getMisfireRate() {
        return misfireRate;
    }

    public void setMisfireRate(double misfireRate) {
        this.misfireRate = misfireRate;
    }

}
