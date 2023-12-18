package csgo_v2;

public class Game {
    public static void main(String[] args) {
        // Initializing the player and weapons for testing.
        BeginnerAmateur player1 = new BeginnerAmateur("TERRORIST_1", true);
        BeginnerAmateur player2 = new BeginnerAmateur("SOLDIER_1", false);
        MasterExpert player3 = new MasterExpert("TERRORIST_2", true);
        MasterExpert player4 = new MasterExpert("SOLDIER_2", false);
        MasterExpert player5 = new MasterExpert("SOLDIER_3", false);

        BowAndArrow bowAndArrow1 = new BowAndArrow("Poisonable Bow and Arrow",  true);
        BowAndArrow bowAndArrow2 = new BowAndArrow("Non-Poisonable Bow and Arrow",  false);

        Firearm pistol = new Firearm("Pistol");

        MachineGun machineGun = new MachineGun("Machine Gun") ;

        SniperRifle sniperRifle = new SniperRifle("Sniper Rifle");

        Melee melee1 = new Melee("Fireable Axe", true);
        Melee melee2 = new Melee("Non-Fireable Knife",  false);

        // Testing the buyWeapon method.
        player1.buyWeapon(melee1);
        player1.buyWeapon(sniperRifle);
        player1.buyWeapon(machineGun);

        player2.buyWeapon(melee1);
        player2.buyWeapon(melee2);
        player2.buyWeapon(sniperRifle);
        player2.buyWeapon(machineGun);
        player2.buyWeapon(pistol);

        player3.buyWeapon(melee2);
        player3.buyWeapon(sniperRifle);
        player3.buyWeapon(melee1);

        player4.buyWeapon(melee1);
        player4.buyWeapon(machineGun);

        // Testing the chooseWeapon method.
        player1.chooseWeapon(machineGun);
        player1.chooseWeapon(melee1);

        // Testing the attack method.
        player1.shoot(player2);
        player1.shoot(player2);
        player1.shoot(player2);
        player1.shoot(player2);
        player1.shoot(player3);

        // Testing the fire weapon method.
        player1.igniteWeapon();
        player1.igniteWeapon();

        // Testing the reloadAmmo method.
        player1.reloadAmmo();

        // Testing the extinguishWeapon method.
        player1.douseWeapon();

        player2.igniteWeapon();
        player2.douseWeapon();

        // Testing the detachScope method.
        player2.detachScope();

        // Testing the take cure method.
        player2.takeCure();

        player3.chooseWeapon(sniperRifle);

        // Testing the attach-detach scope methods.
        player3.detachScope();

        player3.shoot(player4);
        player3.shoot(player4);
        player3.shoot(player4);
        player3.shoot(player4);

        // Testing the get immortal method.
        player4.beImmortal();
        player3.shoot(player4);
        player4.reloadAmmo();

        // Testing the revert immortal method.
        player4.beMortal();
        player4.beMortal();


        // Testing the loanMoney method.
        player3.loanMoney(player4, 200);
        player3.loanMoney(player1, 50);

        player4.chooseWeapon(machineGun);
        // Testing the turn on-off sweep methods.
        player4.turnOnSweep();
        player4.turnOnSweep();

        player4.shoot(player1);
        player4.shoot(player1);
        player4.shoot(player1);

        player4.turnOffSweep();
        player4.turnOffSweep();

        player5.buyWeapon(bowAndArrow1);
        player5.buyWeapon(bowAndArrow2);

        //Testing the poison-clean weapon methods.
        player5.poisonWeapon();
        player5.cleanWeapon();

        player5.chooseWeapon(bowAndArrow1);

        player5.poisonWeapon();
        player5.poisonWeapon();
        player5.cleanWeapon();
        player5.cleanWeapon();

        //Testing the move method.
        player3.move(15,45);
        player1.move(45,14);
        player1.move(455, 455);

        //Testing the drop weapon method.
        player3.dropWeapon();
        player3.dropWeapon();
        player3.dropWeapon();
        player3.dropWeapon();
        player1.dropWeapon();

        //Testing the exchange method.
        player3.exchangeWeapon(player5);
        player1.exchangeWeapon(player5);
        player5.exchangeWeapon(player4);

    }
}