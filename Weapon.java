package csgo_v2;

public abstract class Weapon {
    private String name; // Every weapon has name, range, cost and damage per hit values, no matter what kind of weapon it is.
    private double cost; // Cost -> Indicates the buying cost of the weapon.
    private double damagePerHit; // Damage -> Indicates the damage done by the weapon for each attack.

    public Weapon(String name) { /* Constructor method for Weapon class.
     To make the game more realistic, the damage, range etc. values are determined in the constructor classes. */
        this.setName(name);
    }

    public abstract void useWeapon(Player player, Player target); /* This is an abstract method for using the weapons.
    The idea behind this method -> As each weapon works differently,
     overriding a "use" method for weapons that provide modifications will simplify weapon handling.*/

    /* Besides the getter/setter methods,
     there are also some methods to increase the readability of the code are initialized down below. */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDamagePerHit() {
        return damagePerHit;
    }

    public void setDamagePerHit(double damagePerHit) {
        this.damagePerHit = damagePerHit;
    }

    public void decreaseDamage(double decrease) {
        this.damagePerHit -= decrease;
    } // Decreases the damage of weapon by given value.

    public void increaseDamage(double increase) {
        this.damagePerHit += increase;
    } // Increases the damage of weapon by given value.
}
