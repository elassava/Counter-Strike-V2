package csgo_v2;

final public class BowAndArrow extends RangedWeapon {
    private boolean canBePoisonous; // Different from other ranged weapons, BAA's can be poisoned.
    private boolean isPoisonous;

    BowAndArrow(String name, boolean canBePoisonous) { // Constructor method for BAA.
        super(name);

        this.setRange(45); // Set range here.
        this.setCost(120); // Set cost here.
        this.setDamagePerHit(15); // Set damage here.
        this.setAmmo(10); // Set ammo here.

        this.setCanBePoisonous(canBePoisonous);
        this.setPoisonous(false); // Initially, weapon is not poisonous.
    }

    @Override
    public void useWeapon(Player player, Player target) { /* Overrides the useWeapon method to make a slight change:
    -Changed the output (bullets become arrow). */

        double distance; // Calculates the distance between the player and target.
        distance = Math.sqrt(Math.pow((target.getxAxis() - player.getxAxis()), 2) + Math.pow((target.getyAxis() - player.getyAxis()),2));

        if (target.isTerrorist() != player.isTerrorist()) { // Checks if target is in the same side or not.
            if (distance <= this.getRange()) { // Checks if target is in range.
                if (this.getAmmo() > 0) { // Checks if Player has enough ammos.
                    if (target.getHealth() > target.getMinHealth()) { // Checks if target is alive.

                        double remainingHealth = target.getHealth() - this.getDamagePerHit();
                        this.decreaseAmmo(1); // Updates the ammo.
                        target.decreaseHealth(this.getDamagePerHit()); // Updates the target's health.

                        if (remainingHealth <= 0) System.out.println("Success! Perfect hit!\n" +
                                "Info: " + player.getName() + " killed " + target.getName() + "!\n " +
                                "Info: There are " + this.getAmmo() + " arrow(s) for " + player.getName() + "'s bow.");

                        else if (remainingHealth > 0) System.out.println("Success! Perfect hit!\n" +
                                "Info: " + target.getName() + "'s health level is decreased to " + target.getHealth() + ".\n" +
                                "Info: There are " + this.getAmmo() + " arrow(s) for " + player.getName() + "'s bow.");

                    } else System.out.println("Failed! " + target.getName() + " is already dead!");
                } else
                    System.out.println("Failed! No arrows left to shoot in " + this.getName() + "'s " + this.getName() + "!");
            } else System.out.println("Failed! " + target.getName() + " is out of range.");
        } else System.out.println("Failed !" + target.getName() + " is on the same side!");
    }

    public void poison(Player player) { // Poisons the BAA if possible.
        if (this.isCanBePoisonous()) { // Checks if BAA can be poisoned.
            if (!this.isPoisonous()) { // If BAA is not poisoned already, poisons the weapon. This increases the damage by 15.
                this.setPoisonous(true);
                this.increaseDamage(15);

                // Prints statements.
                System.out.println(
                        "Info: " + player.getName() + " successfully poisoned his/her " + this.getName() + ".");

            } else System.out.println(
                    "Failed! Nice try " + player.getName() + "! " + this.getName() + " is already poisoned!");

        } else System.out.println(
                "Failed! Nice try " + player.getName() + "! " + this.getName() + " can not be poisoned!");
    }

    public void clean(Player player) { // Cleans a poisoned BAA. This reduces the damage back to it's initial value.
        if (this.isPoisonous()) { // Checks if BAA is poisoned.
            this.setPoisonous(false);
            this.decreaseDamage(15);

            // Prints statements.
            System.out.println(
                    "Info: " + player.getName() + " successfully cleaned his/her " + this.getName() + " from poison!");
        } else
            System.out.println(
                    "Nice try " + player.getName() + "! " + this.getName() + " is not poisoned!");
    }

    // Getter/Setter methods are initialized down below.
    public boolean isCanBePoisonous() {
        return canBePoisonous;
    }

    public void setCanBePoisonous(boolean canBePoisonous) {
        this.canBePoisonous = canBePoisonous;
    }

    public boolean isPoisonous() {
        return isPoisonous;
    }

    public void setPoisonous(boolean poisonous) {
        isPoisonous = poisonous;
    }
}
