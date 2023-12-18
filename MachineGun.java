package csgo_v2;

final public class MachineGun extends Firearm {
    private boolean sweepStatus; // Unlike from other weapons, machine guns can have a sweep mode.
    // Initially, ammo decreases by one. If sweep mode is turned on, decrease rate becomes 10.


    MachineGun(String name) { // Constructor method for machine guns.
        super(name);
        this.setMaxAmmo(10); // Set max ammo here.
        this.setAmmo(this.getMaxAmmo()); // Set initial ammo here.
        setMisfireRate(0.1); // Set misfire rate here.

        this.setRange(50); // Set range here.
        this.setCost(350); // Set cost here.
        this.setDamagePerHit(35); // Set damage here.

        this.setSweepStatus(false); // Initially, sweep is off.
    }

    public void turnOnWeaponSweep(Player player) { // Turns on the sweep mode.
        if (!this.isSweepStatus()) { // Checks if sweep is off.
            setSweepStatus(true);
            this.setAmmoDecreaseRate(10);
            System.out.println(player.getName() + " has succesfully turned on sweep feature for " + this.getName() + ".");
        } else System.out.println(
                player.getName() + " failed! " + this.getName() + " has sweep on already!");
    }

    public void turnOffWeaponSweep(Player player) { // Turns off the sweep mode.
        if (this.isSweepStatus()) { // Checks if sweep is on.
            setSweepStatus(false);
            this.setAmmoDecreaseRate(1);
            System.out.println(player.getName() + " has succesfully turned off sweep feature for " + this.getName() + ".");
        } else System.out.println(
                player.getName() + " failed! " + this.getName() + " doesn't have sweep on anyways!");
    }

    //Getter and setter methods.
    public boolean isSweepStatus() {
        return sweepStatus;
    }

    private void setSweepStatus(boolean sweepStatus) {
        this.sweepStatus = sweepStatus;
    }
}
