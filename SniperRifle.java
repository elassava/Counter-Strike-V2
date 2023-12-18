package csgo_v2;

final public class SniperRifle extends Firearm {
    private double fieldViewDistance;
    private boolean scopeStatus;
    private int initialRange = 70;

    SniperRifle(String name) { // Constructor method for sniper rifles.
        super(name);
        this.setMisfireRate(0.01); // All sniper rifles have 0.01 misfire rate if no scope is attached.
        this.setMaxAmmo(100); // Set max. ammo here.
        this.setAmmo(this.getMaxAmmo()); // Set initial ammo here.

        this.setRange(this.getInitialRange()); // Set range here.
        this.setCost(400); // Set cost here.
        this.setDamagePerHit(55); // Set damage here.
        this.setFieldViewDistance(100); // Set field view distance here. (How long a player can see with the scope.)
        this.setScopeStatus(false); // Initially, sniper rifles are owned with no scope. Player can attach a scope by using attachScope() method over Player class.

    }

    public void attachWeaponScope(Player player) { // Attaches a scope to the rifle -> misfire rate will be updated as 0.45, range is updated.

        if (!isScopeStatus()) { // Checks if a scope is attached or not.
            this.setScopeStatus(true);
            this.setRange(this.getFieldViewDistance()); // Player can see with scope now.
            this.setMisfireRate(0.45); // Update misfire rate here.

            System.out.println(player.getName() + " has succesfully attached a scope to " + this.getName() + "!");
        } else System.out.println(player.getName() + " failed! There is already a scope attached to " + this.getName() + "!");
    }

    public void detachWeaponScope(Player player) { // Detaches a scope from the rifle -> misfire rate will be set back to 0.01.

        if (isScopeStatus()) {
            this.setScopeStatus(false);
            this.setRange(this.getInitialRange());
            this.setMisfireRate(0.01); // Update misfire rate here.

            System.out.println(player.getName() + " has succesfully detached the scope from " + this.getName() + "!");
        } else System.out.println(player.getName() + " failed! There is no scope attached to " + this.getName() + "!");
    }

    //Getter/Setter methods.
    public double getFieldViewDistance() {
        return fieldViewDistance;
    }

    public void setFieldViewDistance(double fieldViewDistance) {
        this.fieldViewDistance = fieldViewDistance;
    }

    public boolean isScopeStatus() {
        return scopeStatus;
    }

    public void setScopeStatus(boolean scopeStatus) {
        this.scopeStatus = scopeStatus;
    }

    public int getInitialRange() {
        return initialRange;
    }

    public void setInitialRange(int initialRange) {
        this.initialRange = initialRange;
    }
}
