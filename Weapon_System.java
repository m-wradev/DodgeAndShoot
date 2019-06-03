/**********************************************************************
Weapon_System.java

Class:    Weapon_System
Access:   Public
Type:     Abstract
Children: Laser_Turret.java, Rocket_Launcher.java


This class serves as the basis for all weapon_systems in the game.
It will handle all frequently recurring events that every weapon system shares (such as weapon cooldowns).
It also sets a static variable (Dodge_And_Shoot game) so that all weapon systems can access methods required to perform actions such as firing a laser.
************************************************************************/

public abstract class Weapon_System
{
      protected static Dodge_And_Shoot game;
      
      private boolean canFire = true;
      private boolean canAltFire = true;
      
      private int weaponMaxCooldown;
      private int weaponMaxAltCooldown;
       
      private int cooldown;
      private int altCooldown;
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      protected void setCooldownValues(int weaponMaxCooldown, int weaponMaxAltCooldown) {
            this.weaponMaxCooldown = weaponMaxCooldown;
            this.weaponMaxAltCooldown = weaponMaxAltCooldown;
      }
      
      public static void setGame(Dodge_And_Shoot g) {
            game = g;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      protected boolean canFire()      {  return canFire;      }
      protected boolean canAltFire()   {  return canAltFire;   }
      
      protected int getCooldown()      {  return cooldown;     }
      protected int getAltCooldown()   {  return altCooldown;  }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      // Decreases cooldown value by one.
      // When cooldown reaches 0, signal that weapon can primary fire again
      protected void cooldown() {
            cooldown--;
            
            if (cooldown == 0)
                  canFire(true);
      }
      
      // Decreases cooldown value by one
      // When altCooldown reaches 0, signal that weapon can alt fire again
      protected void altCooldown() {
            altCooldown--;
            
            if (altCooldown == 0)
                  canAltFire(true);
      }
      
      // Resets weapon cooldown to primary max cooldown
      protected void resetCooldown() {
            cooldown = weaponMaxCooldown;
      }
      
      // Resets weapon alt cooldown to alt max cooldown
      protected void resetAltCooldown() {
            altCooldown = weaponMaxAltCooldown;
      }
      
      // Set whether weapon can currently attack with primary fire
      protected void canFire(boolean b) {
            canFire = b;
      }
      
      // Set whether weapon can currently attack with alt fire
      protected void canAltFire(boolean b) {
            canAltFire = b;
      }

      /*======================================================
      ========================================================
      ======================================================*/

      public abstract void handleEvents();
      public abstract void fire(Dodge_And_Shoot game);
      public abstract void altFire(Dodge_And_Shoot game);
      public abstract int getDamage();
      public abstract int getAltDamage();
}