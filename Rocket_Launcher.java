import java.awt.Color;

public class Rocket_Launcher extends Weapon_System
{
      public final int BASE_PRIMARY_DAMAGE = 15;
      public final int BASE_PRIMARY_COOLDOWN = 50;
      public final Color PRIMARY_FIRE_COLOR = Color.black;
      public final double PRIMARY_FIRE_LENGTH = 35;
      public final int PRIMARY_FIRE_WIDTH = 15;
      public final int PRIMARY_FIRE_PROJECTILE_SPEED = 10;
      
      private int damage;
      private int altDamage;
      
      private Spaceship owner;
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public Rocket_Launcher(Spaceship owner) {
            this.damage = BASE_PRIMARY_DAMAGE;
            this.owner = owner;
            
            super.setCooldownValues(BASE_PRIMARY_COOLDOWN, 0);
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public void handleEvents() {
            if (super.getCooldown() != 0)
                  super.cooldown();
                  
            if (super.getAltCooldown() != 0)
                  super.altCooldown();
      }
      
      public void fire(Dodge_And_Shoot game) {
      
      }
      
      public void altFire(Dodge_And_Shoot game) {
      
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public int getDamage()     {  return damage;    }
      public int getAltDamage()  {  return altDamage; }
      
}