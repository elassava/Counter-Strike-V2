package csgo_v2;

final public class MasterExpert extends Player {
    private boolean immortal; /* Unlike from other players, expert players can be immortal.
    They can have up to 2 weapons.
    They can use all kinds of weapons.*/

    MasterExpert(String name, boolean isTerrorist) { // Constructor method for MasterExpert object.
        super(name, isTerrorist);
        this.setMaxWeaponCount(2); // Set max weapon count here.
        this.setMoney(1000); // Set initial money here.
        this.setImmortal(false); // Initially, player starts as an immortal.
    }


    public void seeDistance() { // Print's this player's distances to each player.
        for (Player player : getAllPlayers()) {
            if (player != null && player != this) {
                double distance; // Calculates the distance between the player and target.

                distance = Math.sqrt(Math.pow((this.getxAxis() - player.getxAxis()), 2) + Math.pow((this.getyAxis() - player.getyAxis()),2));
                System.out.println(this.getName() + "'s distance to " + player.getName() + ": " + Math.round(distance) + " units.");
            } if (player == null) break;
        }
    }

    public void seeDistance(Player player) { // Print's this player's distances to a specific player.
        double distance; // Calculates the distance between the player and target.
        distance = Math.sqrt(Math.pow((this.getxAxis() - player.getxAxis()), 2) + Math.pow((this.getyAxis() - player.getyAxis()),2));
        System.out.println(this.getName() + "'s distance to " + player.getName() + ": " + Math.round(distance) + " units");
    }

    public void beImmortal() { // Makes the player immortal: Player can take damage, but can not die.
        System.out.println("----------------------------------------\n" +
                "New Operation: " +
                this.getName() + " gets immortality");
        double immortalityCost = 600.0; // Set immortality cost here.

        if (!this.isImmortal()) { // Checks if player is not immortal at the time.
            if (this.getMoney() - immortalityCost >= 0) { // Checks if player can afford it.
                this.setImmortal(true);

                // Prints statements.
                System.out.println("You better run! " + this.getName() + " is immortal now and can't be killed!");
            } else System.out.println("Failed! " + this.getName() + " can not afford immortality right now!");
        } else System.out.println("Failed! Can't you get enough " + this.getName() + "? You are already immortal.");
    }

    public void beMortal() { // Reverts immortality.
        System.out.println("----------------------------------------\n" +
                "New Operation: " + this.getName() + " gets mortal");
        if (this.isImmortal()) { // Checks if player is immortal.
            this.setImmortal(false);
            // Prints statements.
            System.out.println(this.getName() + " is no longer immortal.");
        } else System.out.println("Bad news " + this.getName() + ": You weren't immortal anyway!");
    }


    // Getter and setter methods.
    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }
}
