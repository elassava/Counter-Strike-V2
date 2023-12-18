package csgo_v2;

final public class Melee extends Weapon {
    private boolean isFireable;
    private boolean isFired;

    public Melee(String name, boolean isFireable) { // Constructor method for Melee claas.
        super(name);

        this.setCost(100); // Set cost here.
        this.setDamagePerHit(50); // Set damage here.

        this.setFireable(isFireable); // Set if the weapon is fireable here.
        this.setFired(false); // Initially, all weapons are owned in a non-fired state.
    }

    @Override
    public void useWeapon(Player player, Player target) { /* An overrided version of useWeapon method for player to "use" the weapon.
    Change(s) made:  Since melee weapons do not need ammos, there won't be a print statement about Player's remaining ammo. */
        if (target.isTerrorist() != player.isTerrorist()) { // Checks if target is on same side.
            if (target.getHealth() > target.getMinHealth()) { // Checks if target is alive.

                double remainingHealth = target.getHealth() - this.getDamagePerHit();
                target.decreaseHealth(this.getDamagePerHit()); // Decreases the target's health.

                //Prints statements.
                if (remainingHealth <= 0) System.out.println(
                        "Success! Perfect hit!\n" +
                                "Info: " + player.getName() + " killed " + target.getName() + "! ");

                else if (remainingHealth > 0) System.out.println((
                        "Success! Perfect hit!\n" +
                                "Info: " + target.getName() + "'s health level is decreased to " + target.getHealth() + "."));

            } else System.out.println("Failed! " + target.getName() + " is already dead!");
        } else System.out.println("Failed !" + target.getName() + " is on the same side!");
    }


    public void ignite(Player player) { // Sets the weapon on fire -> Increases damage by 5.
        if (isFireable()) { // Checks if the weapon is fireable.
            if (!isFired()) { // Checks if the weapon is already fired.
                this.increaseDamage(5); // Increases the damage of the weapon by 5.

                // Prints statements.
                System.out.println(
                        "Info: " + player.getName() + "'s weapon " + this.getName() + " is succesfully fired now!");

            } else System.out.println("Failed! " + this.getName() + " is already fired!");

        } else System.out.println("Failed! " + this.getName() + " is a non-fireable weapon!");
    }

    public void douse(Player player) { // Turns the weapon back to non-fired state.
        if (isFireable()) { // Check if the weapon is fireable.
            if (isFired()) { // Check if the weapon is fired.
                this.decreaseDamage(5); // Set damage back to it's initial value.

                // Prints statements.
                System.out.println(
                        "Info: " + player.getName() + " succesfully extinguished his/her weapon " + this.getName() + ".");

            } else System.out.println(
                    "Failed! " + this.getName() + " isn' fired!");

        } else System.out.println(
                "Failed! " + this.getName() + " is a non-fireable weapon!");
    }

    // Getter/Setter methods.
    public boolean isFireable() {
        return isFireable;
    }

    public void setFireable(boolean fireable) {
        isFireable = fireable;
    }

    public boolean isFired() {
        return isFired;
    }

    public void setFired(boolean fired) {
        isFired = fired;
    }
}


