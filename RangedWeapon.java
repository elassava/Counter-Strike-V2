package csgo_v2;

public abstract class RangedWeapon extends Weapon { // Unlike melee weapons, ranged weapons have ammo.
    private int maxAmmo; // Each ranged weapon has a maximum ammo value.
    private int ammo;
    private int ammoDecreaseRate = 1;
    private double range; // Range -> Indicates the maximum shooting range of weapon.

    RangedWeapon(String name) { // Constructor method for ranged weapons.
        super(name);
    }

    @Override
    public void useWeapon(Player player, Player target) { // Overrides the useWeapon method.
        double distance; // Calculates the distance between the player and target.
        distance = Math.sqrt(Math.pow((target.getxAxis() - player.getxAxis()), 2) + Math.pow((target.getyAxis() - player.getyAxis()),2));

       if (target.isTerrorist() != player.isTerrorist()) { // Checks if target is on opposite side.
           if (distance <= this.getRange()) { // Checks if target is in range.
               if (this.getAmmo() > 0) { // Checks if player has enough ammo.
                   if (target.getHealth() > target.getMinHealth()) { // Checks if target is alive.

                       double remainingHealth = target.getHealth() - this.getDamagePerHit();
                       this.decreaseAmmo(this.getAmmoDecreaseRate()); // Decreases ammo of weapon by one and target's health by damage per hit.
                       target.decreaseHealth(this.getDamagePerHit()); // Update target's health.

                       // Prints statements.
                       if (remainingHealth <= 0) System.out.println(
                               "Success! Perfect hit!\n" +
                                       "Info: " + target.getName() + "'s health level is decreased to " + target.getMinHealth() + "." +
                                       "Info: " + player.getName() + " killed " + target.getName() + "!\n " +
                                       "Info: There are " + this.getAmmo() + " bullet(s) in " + player.getName() + "'s weapon.");

                       else if (remainingHealth > 0) System.out.println((
                               "Success! Perfect hit!\n" +
                                       "Info: " + target.getName() + "'s health level is decreased to " + target.getHealth() + ".\n" +
                                       "Info: There are " + this.getAmmo() + " bullet(s) in " + player.getName() + "'s weapon.\n"));

                   } else System.out.println(
                           "Failed! " + target.getName() + " is already dead!");
               } else System.out.println("Failed! No bullets left in " + player.getName() + "'s " + this.getName() + "!");
           } else System.out.println("Failed! " + target.getName() + " is out of range.");
       } else System.out.println("Failed !" + target.getName() + " is on the same side!");
    }

    // Getter/Setter methods. Besides them, I implemented some methods to increase the readability of the code.
    public int getAmmoDecreaseRate() {
        return ammoDecreaseRate;
    }

    public void setAmmoDecreaseRate(int ammoDecreaseRate) {
        this.ammoDecreaseRate = ammoDecreaseRate;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void decreaseAmmo(int decrease) {
        this.ammo -= decrease;
    } // Decreases the ammo by given value.

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getAmmo() {
        return ammo;
    }
}
