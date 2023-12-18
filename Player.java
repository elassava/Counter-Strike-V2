package csgo_v2;

import java.util.ArrayList; // Importing the necessary classes.
import java.util.Random;

public abstract class Player {
    // Initializing the variables that every "Player" has.
    Random random = new Random();

    private static Player[] allPlayers = new Player[100]; /* Stores all the players that have been created.
     This is used to see the distances between all players easily. */

    private String name; // Every Player has name, health, money, and a boolean value indicating if they are a terrorist or not.
    private double health;
    private double money;

    private boolean isTerrorist; // Every player has a boolean value indicates if they are a terrorist or not.

    private int maxWeaponCount; // Every player has a maximum number of weapons they can own.
    private int weaponCount; // Indicates how many weapons the player has at the moment.

    protected ArrayList<Weapon> weapons = new ArrayList<>(maxWeaponCount); // An ArrayList to store the weapons that player owns.
    protected Weapon currentWeapon; /* Stores the weapon that being used.
    I implemented this method to avoid confusion when using different kinds of weapons.
    Players can change which weapon they are using. Initially, it will be the last weapon they bought.*/

    private final int xAxisBound = 100; // Set the boundaries for x and y axes.
    private final int yAxisBound = 50;
    private final int[] coordinates = {random.nextInt(xAxisBound), random.nextInt(yAxisBound)}; /* Every player has coordinates assigned randomly when created.
    It will be used in upcoming versions to indicate if an enemy is in range or not. */
    private int xAxis = coordinates[0];
    private int yAxis = coordinates[1];


    Player(String name, boolean isTerrorist) { // Constructor method for default player.
        this.setName(name);
        this.setTerrorist(isTerrorist);
        this.setWeaponCount(0);
        this.setHealth(this.getMaxHealth());
        addPlayer(this);
    }

    private static void addPlayer(Player player) { // Adds player to the array that stores all the players.
        for (int i = 0; i < allPlayers.length; i++) {
            if (allPlayers[i] == null) {
                allPlayers[i] = player;
                break;
            }
        }
    }

    public void shoot(Player target) { /*
     (See Weapon class and it's subclasses to understand the logic of useWeapon method.)

     Since the operation of each weapon is different, I preferred this way instead of writing a fire method over the Player class.
     Logic of this method: Each weapon has a useWeapon(Player player, Player target) method.
     Player and Target variables allow easy modification of the players(health) and an informative output to the user.
     When attack(Player target) method is called, it will automatically call the current weapon's useWeapon method.
     By this way, all weapons can be used properly without using override with a single method over the player class.
     */

        if (target != null) System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " shoots at " + target.getName());

        else System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " shoots at an enemy.");

        if (this.currentWeapon != null) {
            if (target != null) {
                double remainingHealth = target.getHealth() - currentWeapon.getDamagePerHit(); // Sets a remaining health value (for readability).
                if (target instanceof MasterExpert && ((MasterExpert) target).isImmortal() && remainingHealth <= 0) {
            /* Checks if enemy is immortal.
            An immortal enemy's health can be reduced until it hits 0.
            If an attack reduces the player's health below 0, this piece of code will work and the player will not die. */
                    System.out.println(this.getName() + " failed! " + target.getName() + " can not be killed now! He/she is immortal!");
                } else currentWeapon.useWeapon(this, target); // Calls the current weapon's useWeapon() method.
            } else
                System.out.println("Failed! Don't be delusional " + this.getName() + ", the target doesn't even exist!");
        } else System.out.println(this.getName() + " failed! (S)he is not using a weapon right now.");
    }

    public void move(int x, int y) { // Player can move to given coordinates.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " moves.");

        if ((x >= 0 && x <= xAxisBound) && (y >= 0 && y <= yAxisBound)) { // Checks if given parameters are in bounds.
            this.setCoordinates(x, y); // Sets new coordinates.
            System.out.println("Info: " + this.getName() + " moved to [" + this.getxAxis() + ", " + this.getyAxis() + "].");
            if (this instanceof MasterExpert expert) expert.seeDistance(); // If player is expert, also prints distance to each player.
        } else System.out.println(this.getName() + " failed! You can not get out of bounds!");
    }

    public void buyWeapon(Weapon weapon) { /* This method is used to buy a weapon. First, it checks if the parameter is a proper type of weapon.
    Then, it checks if player has enough money.
    After, if a player has enough inventories to buy a weapon.
    If all terms are satisfied, player will switch his/her current weapon to the weapon he/she bought.
    Else, proper warnings will be printed.*/
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " buys a " + weapon.getName());

        if (weapon instanceof Firearm || weapon instanceof BowAndArrow || weapon instanceof Melee) { /*
        Types of weapons player can buy: sniper rifle, machine gun, fire arm, bow and arrow, and melee weapon. */

            if (!weapons.contains(weapon)) { // Checks if player already has the chosen weapon (If yes, player can not buy same weapon twice).
                if (this.getMoney() - weapon.getCost() >= 0) { // Checks if player can afford it.
                    if (weaponCount < maxWeaponCount) { // Checks if player has enough room.

                        this.decreaseMoney(weapon.getCost()); // Decreases the player's money.
                        this.increaseWeaponCount(1); // Increases the count of weapons owned by player by one.
                        weapons.add(weapon); // Adds the weapon to the "weapons" ArrayList.
                        currentWeapon = weapon; // Sets the bought weapon as current weapon.

                        // Prints updates/warnings.
                        System.out.println("Info: " + this.getName() + " bought and using a " + currentWeapon.getName() + "!\n" +
                                "Info: " + this.getName() + " has " + this.getMoney() + "$ now!");
                    } else System.out.println("Failed! " + this.getName() + " already has a full inventory!");
                } else
                    System.out.println("Failed! " + this.getName() + " does not have enough money to buy a " + weapon.getName() + " !");
            } else System.out.println("Failed! " + this.getName() + " already has a " + weapon.getName() + " !");
        } else
            System.out.println("Failed! Players can have only sniper rifle, machine gun, fire arm, bow and arrow, and melee weapon weapon types.");
    }

    public void reloadAmmo() { /* This method is used for reloading ammo.
        Since only ranged weapons have ammo's, method will check if the current weapon is a Ranged weapon.
        Then the cost of reload will be calculated. If player has enough money, the weapon's ammo will set back to maximum. */

        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " reloads his/her ammo");

        double costPerAmmo = 5.0; // Cost per ammo is set and can be changed here.
        if (currentWeapon instanceof RangedWeapon rangedWeapon) { // Check if weapon is Ranged.

            int ammoToBeReloaded = rangedWeapon.getMaxAmmo() - rangedWeapon.getAmmo();
            double costOfReload = ammoToBeReloaded * costPerAmmo; // Calculates cost of reload.
            if (this.getMoney() - costOfReload >= 0) { // Checks if player can afford reloading.

                this.decreaseMoney(costOfReload); // Updates the player's money and ammo.
                rangedWeapon.setAmmo(rangedWeapon.getMaxAmmo());

                // Print updates/warnings.
                System.out.println(this.getName() + " has successfully reloaded her magazine!\n"
                        + "Info: " + this.getName() + " has " + rangedWeapon.getMaxAmmo() + " ammo for his/her " +
                        rangedWeapon.getName() + ".\n" +
                        "Info: " + this.getName() + " has " + this.getMoney() + "$ now.");

            } else
                System.out.println("Failed! " + this.getName() + " does not have enough money to reload his/her ammo.");
        } else
            System.out.println(this.getName() + " failed! Come on, do you really think melee weapons work with ammo?!");
    }

    public void chooseWeapon(Weapon choice) { /* Player can change his/her weapon using this method.
     I implemented this method to make the game more realistic.
     Player should choose the weapon they are going to use first, and then take some actions.*/
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " changes his/her weapon");

        if ((weapons.contains(choice))) { // Checks if player owns the weapon of choice.
            if (currentWeapon == choice) {
                System.out.println("Failed! " + this.getName() + " is already using a " + choice.getName() + ".");
            } else {
                currentWeapon = weapons.get(weapons.indexOf(choice));
                System.out.println("Info: " + this.getName() + " changed his/her weapon to " + currentWeapon.getName() + "!");
            }
        } else System.out.println("Failed! " + this.getName() + " doesn't own a " + choice.getName() + ".");
    }

    public void dropWeapon() { /* Master Experts can drop their weapons to open up space in their inventory.
    If the player has more than one weapon and drops one, the weapon (s)he is currently using will be updated to
    the one (s)he bought before the one (s)he dropped (like the real scenario in the CSGO game).
    If a player drops the only weapon (s)he has, his/her current weapon will be "null".*/

        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " drops his/her current weapon.");
        if (this instanceof MasterExpert){
            if (weapons.size() > 0) { // Checks if player has any weapon.
                int indexOfWeapon;
                indexOfWeapon = weapons.indexOf(currentWeapon);

                weapons.remove(currentWeapon); // Removes the current weapon from the weapons list.
                this.decreaseWeaponCount(1); // Decreases the player's weapon count by one.

                if (this.getWeaponCount() == 0)
                    System.out.println("Info: " + this.getName() + " has dropped his/her weapon "
                            + currentWeapon.getName() + " and doesn't have a weapon anymore."); // If player drops his/her only weapon, this print statement will run.

                else
                    System.out.println("Info: " + this.getName() + " has dropped his/her weapon " + currentWeapon.getName() + " and has "
                            + this.getWeaponCount() + " weapons in total.");

                if (indexOfWeapon == 0) currentWeapon = null; // Updates the current weapon.
                else {
                    currentWeapon = weapons.get(indexOfWeapon - 1);
                    System.out.println(this.getName() + " is now using his/her " + currentWeapon.getName() + ".");
                }

            } else System.out.println("Failed! " + this.getName() + " doesn't own any weapon.");
        } else System.out.println(this.getName() + " failed! Only Master Experts can drop their weapons.");
    }

    public void exchangeWeapon(Player player) { /* Exchanges a weapon with a Master Expert teammate.
    Similar to the original game, players will trade their current weapons.
    Player will give the teammate his/her current weapon, similarly, player will take the teammate's current weapon.*/
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " exchanges weapon with " + player.getName() + ".");

        if (player instanceof MasterExpert && this instanceof MasterExpert) { // Checks if target player is a Master Expert.
            if (this.currentWeapon != null && player.currentWeapon != null) {
                if (this.isTerrorist() == player.isTerrorist()) { // Checks if they are in the same team.
                    Weapon tempWeapon = this.getCurrentWeapon(); // Creates a temporary weapon to make exchanging easy.

                    this.setCurrentWeapon(player.currentWeapon); // Exchange the weapons.
                    this.weapons.remove(this.getCurrentWeapon()); // Update the weapons list.
                    this.weapons.add(player.getCurrentWeapon());

                    player.setCurrentWeapon(tempWeapon);
                    player.weapons.remove(player.getCurrentWeapon());
                    player.weapons.add(tempWeapon);

                    // Print statements.
                    System.out.println("Info: " + this.getName() + " and " + player.getName() + " exchanged their weapons.\n" +
                            this.getName() + " is now using " + this.getCurrentWeapon().getName() + ".\n" +
                            player.getName() + " is now using " + player.getCurrentWeapon().getName() + ".");

                } else System.out.println("Failed! Come on, too young to be a spy!");
            } else
                System.out.println("Failed! " + this.getName() + "/" + player.getName() + " doesn't own any weapon, (s)he can not exchange!");
        } else System.out.println("Failed! Only Master Experts can trade their weapons, not amateurs!");
    }


    public void igniteWeapon() { // A method for "firing" the melee weapons.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " fires his/her weapon");
        if (currentWeapon instanceof Melee melee) { // Checks if the weapon is Melee.
            melee.ignite(this); // Fires the weapon.
        } else System.out.println(this.getName() + " failed! Only melee weapons can be fired.");
    }

    public void douseWeapon() { // Reverts the fired melee weapon to non-fired state.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " extinguishes his/her weapon");
        if (currentWeapon instanceof Melee melee) { // Checks if the weapon is Melee.
            melee.douse(this); // Extinguishes the weapon.
        } else System.out.println(this.getName() + " failed! Only melee weapons can be extinguished.");
    }

    public void poisonWeapon() { // Poisons the weapon if it's a BAA.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " poisons his/her weapon");

        if (this instanceof MasterExpert){
            if (currentWeapon instanceof BowAndArrow baa) { // Checks if weapon is BAA.
                baa.poison(this); // Poisons weapon.
            } else System.out.println(this.getName() + " failed! Only Bow and Arrows can be poisoned.");
        } else System.out.println(this.getName() + " failed! Only Master Experts can poison a weapon.");
    }

    public void cleanWeapon() { // Cleans the said weapon, if weapon is poisoned.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " cleans his/her weapon");
        if (this instanceof MasterExpert) {
            if (currentWeapon instanceof BowAndArrow baa) { // Checks if weapon is BAA.
                baa.clean(this); // Cleans weapon from poison.
            } else System.out.println(this.getName() + " failed! Only Bow and Arrows can be cleaned from poison.");
        } else System.out.println(this.getName() + " failed! Only Master Experts can clean a weapon.");
    }


    public void takeCure() { // Renews the Player's health to max. value if he/she has enough money.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " takes cure");

        double cureCost = 500.0; // Set the cure cost here.
        double remainingMoney = this.getMoney() - cureCost;

        if (remainingMoney > 0) { // Checks if player can afford cure.

            this.decreaseMoney(cureCost);
            this.setHealth(this.getMaxHealth()); // Sets player's health back to maximum.

            // Prints the information.
            System.out.println("Success! " + this.getName() + " is now as fit as s(he) has never been before!\n" +
                    "Info: " + this.getName() + "'s health level is now " + this.getHealth() + ".\n" +
                    "Info: " + this.getName() + " has " + this.getMoney() + "$ now.");

        } else System.out.println("Failed! " + this.getName() + " cannot afford it now!");
    }

    public void loanMoney(Player borrower, int amountToBeGiven) { // Loans money to a teammate if Player has enough money and if borrower is in the same team.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " loans money to " + borrower.getName());

        if (this instanceof BeginnerAmateur player) { // Checks if player is a Beginner Amateur.
            if (borrower.isTerrorist() != this.isTerrorist()) // Checks if borrower is in the same team with this Player.
                System.out.println("Failed! Come on " + this.getName() + "! Too young to be a spy!");

            else if (amountToBeGiven > this.getMoney()) // Checks if player has enough money to lend.
                System.out.println("Failed! " + this.getName() + " cannot afford it now!");
            else {
                borrower.increaseMoney(amountToBeGiven); // Increases money of borrower.
                this.decreaseMoney(amountToBeGiven); // Decreases the money of loaner.
                System.out.println("Success! " + this.getName() + " is a perfect philanthropist!\n" +
                        "Info: " + this.getName() + " has now " + this.getMoney() + "$!\n" +
                        "Info: " + borrower.getName() + " has now " + borrower.getMoney() + "$!");
            }
        } else System.out.println(this.getName() + " failed! Only beginner amateurs can loan money to others.");
    }

    public void attachScope() { // Attaches a scope to a sniper rifle by calling the attachWeaponScope method over SniperRifle class.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " attaches a scope to his/her weapon");

        if (this instanceof MasterExpert){ // Check if player is master expert.
            if (currentWeapon instanceof SniperRifle rifle) { // Checks if the current weapon is a sniper rifle.
                rifle.attachWeaponScope(this);

            } else // If the weapon is not a sniper rifle, a scope can not be attached to/detached from it.
                System.out.println("Failed! Hate to break it to you " + this.getName() +
                        " but you can't attach a scope to your weapon! It's not a sniper rifle.");
        } else System.out.println(this.getName() + " failed! Only Master Experts can attach a scope.");
    }

    public void detachScope() { // Detaches the scope from a sniper rifle by calling detachWeaponScope method over SniperRifle class.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " detaches a scope from his/her weapon");

        if (this instanceof MasterExpert){ // Checks if player is master expert.
            if (currentWeapon instanceof SniperRifle rifle) { // Checks if the current weapon is a sniper rifle.
                rifle.detachWeaponScope(this);

            } else // If the weapon is not a sniper rifle, a scope can not be attached to/detached from it.
                System.out.println(this.getName() + " failed! How are you going to detach a scope if you can't even attach it to your weapon?!");
        } else System.out.println(this.getName() + " failed! Only Master Experts can detach a scope.");
    }

    public void turnOnSweep() { // Turns on sweep mode for machine guns.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " turns the sweep mode on");
        if (this instanceof MasterExpert){ // Checks if player is master expert.
            if (currentWeapon instanceof MachineGun machineGun) { // Checks if the current weapon is a machine gun.
                machineGun.turnOnWeaponSweep(this); // Calls the proper method over MachineGun class.

            } else // If the weapon is not a machine gun, it can't have a sweep mode.
                System.out.println(this.getName() + " failed! Only a machine gun can have sweep feature.");
        } else System.out.println(this.getName() + " failed! Only Master Experts can use sweep mode.");
    }

    public void turnOffSweep() { // Turns off the sweep mode for machine guns.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " turns the sweep mode off");
       if (this instanceof MasterExpert){ // Checks if player is master expert.
           if (currentWeapon instanceof MachineGun machineGun) { // Checks if the current weapon is a machine gun.
               machineGun.turnOffWeaponSweep(this); // Calls the proper method over MachineGun class.
           } else // If the weapon is not a machine gun, it can't have a sweep mode.
               System.out.println(this.getName() + " failed! Only a machine gun can have sweep feature.");
       } else System.out.println(this.getName() + " failed! Only Master Experts can use sweep mode.");
    }


    // Getter and setter methods are initialized down below. Also, I implemented some methods to increase readability of the code.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void decreaseHealth(double decrease) { // Decreases the health by given value.
        this.health -= decrease;
    } // Decreases the health by given value.

    public void increaseHealth(double increase) { // Increases the health by given value.
        this.health += increase;


    } // Increases the health by given value.
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void decreaseMoney(double decrease) { // Decreases the money by given value.
        this.money -= decrease;
    } // Decreases the money by given value.

    public void increaseMoney(double increase) {  // Increases the money by given value.
        this.money += increase;
    } // Increases the money by given value.

    public boolean isTerrorist() {
        return isTerrorist;
    }

    public void setTerrorist(boolean terrorist) {
        isTerrorist = terrorist;
    }

    public int getMaxWeaponCount() {
        return maxWeaponCount;
    }

    public void setMaxWeaponCount(int maxWeaponCount) {
        this.maxWeaponCount = maxWeaponCount;
    }

    public void decreaseWeaponCount(int decrease) {
        this.weaponCount -= decrease;
    } // Decreases the weapon count by given value.

    public void increaseWeaponCount(int increase) { // Increases the weapon count by given value.
        this.weaponCount += increase;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    private void updateAxes() { // Updates the axes if player moves.
        this.xAxis = coordinates[0];
        this.yAxis = coordinates[1];
    }

    public void setCoordinates(int x, int y) { // Updates the player's coordinates.
        coordinates[0] = x;
        coordinates[1] = y;
        updateAxes();
    }

    public int getyAxisBound() {
        return yAxisBound;
    }

    public int getxAxisBound() {
        return xAxisBound;
    }

    public int getWeaponCount() {
        return weaponCount;
    }

    void setWeaponCount(int weaponCount) {
        this.weaponCount = weaponCount;
    }

    public double getMaxHealth() {
        return 100; // Set max. health here.
    }

    public double getMinHealth() {
        return 0; // Set min. health here.
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public static Player[] getAllPlayers() {
        return allPlayers;
    }

    public static void setAllPlayers(Player[] allPlayers) {
        Player.allPlayers = allPlayers;
    }
}
