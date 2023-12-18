package csgo_v2;

final public class BeginnerAmateur extends Player { /* Unlike to Master Expert players,
    Beginner Amateur players can own up to 5 weapons.
    But they can only use Melee weapons. */

    BeginnerAmateur(String name, boolean isTerrorist) { // Constructor method for an Amateur player.
        super(name, isTerrorist);
        this.setMoney(500); // Default money($) for a beginner.
        setMaxWeaponCount(1); // Max. weapon count for a beginner.
    }

    @Override
    public void buyWeapon(Weapon weapon) { /* Overrides the buyWeapon method initialized in Player class.
    This method is designed for just buying melee weapons, since beginners can not use anything other than melee weapons.*/
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " buys a weapon");

        if (weapon instanceof Melee) { // Checks if the weapon is a melee weapon.
            if (!weapons.contains(weapon)) { // Checks if player has the weapon. (If yes, player can not buy same weapon twice).
                if (this.getMoney() - weapon.getCost() >= 0) { // Checks if player can afford the weapon.
                    if (this.getWeaponCount() < this.getMaxWeaponCount()) { // Checks if player has enough room.
                        this.decreaseMoney(weapon.getCost()); // Decreases the player's money.
                        this.increaseWeaponCount(1); // Increases the count of weapons owned by player by one.
                        weapons.add(weapon); // Adds the weapon to the "weapons" ArrayList.
                        currentWeapon = weapon; // Sets the bought weapon as current weapon.

                        // Print updates/warnings.
                        System.out.println("Info: " + this.getName() + " bought and using a " + currentWeapon.getName() + "!\n" +
                                "Info: " + this.getName() + " has " + this.getMoney() + "$ now!");
                    } else System.out.println("Failed! " + this.getName() + " already has a full inventory!");
                } else
                    System.out.println("Failed! " + this.getName() + " does not have enough money to buy a " + weapon.getName() + " !");
            } else System.out.println("Failed! " + this.getName() + " already has a " + weapon.getName() + " !");
        } else
            System.out.println("Failed! " + this.getName() + " is an amateur! He/she can only use melee weapons, don't risk it!");
    }
}
