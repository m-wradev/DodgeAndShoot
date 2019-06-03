import java.awt.Color;
import java.io.File;

public class Laser_Turret extends Weapon_System
{
      public final int BASE_PRIMARY_DAMAGE = 5;                                  // Primary damage per shot
      //public final File PRIMARY_FIRE_SOUND = Sound.LASER_TURRET_PRIMARY;         // Primary fire sound
      public final int BASE_PRIMARY_COOLDOWN = 1;  // Default 20                               // How long weapon must wait to be fired again
      public final Color PRIMARY_FIRE_COLOR = Color.blue;                         // Color of the weapon's projectile
      public final int PRIMARY_FIRE_LENGTH = 50;                                 // Length of the projectile
      public final int PRIMARY_FIRE_PROJECTILE_SPEED = 15;                       // Speed of laser
      
      public final int BASE_ALT_DAMAGE = 10;                                     // Alt damage
      public final int BASE_ALT_COOLDOWN = 40;                                   // Alt cooldown
      public final Color ALT_FIRE_COLOR = Color.blue;                            // Color of alt fire
      
      
      public boolean canFire = true;
      public boolean canAltFire = true;
      
      private int damage;
      private int altDamage;
      
      private Spaceship owner;
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public Laser_Turret(Spaceship owner) {
            this.damage = BASE_PRIMARY_DAMAGE;
            this.owner = owner;
            
            super.setCooldownValues(BASE_PRIMARY_COOLDOWN, BASE_ALT_COOLDOWN);
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      // Recurring events that must be handled per frame
      public void handleEvents() {
            if (super.getCooldown() != 0)
                  super.cooldown();
                  
            if (super.getAltCooldown() != 0)
                  super.altCooldown();
      }
      
      // Fire weapon using primary fire mode
      public void fire(Dodge_And_Shoot game) {
            if ( super.canFire() ) {
            
                  //playFireSound();
                  
                  Projectile p = new Laser( BASE_PRIMARY_DAMAGE, PRIMARY_FIRE_PROJECTILE_SPEED, PRIMARY_FIRE_COLOR, PRIMARY_FIRE_LENGTH, owner.getNoseX(), owner.getNoseY(), owner.getPointerX(), owner.getPointerY(), owner, true );
                  game.addProjectile(p);
                  game.addPlayerProjectile(p);
            
                  super.resetCooldown();
                  super.canFire(false);
            }
      }
      
      /*private void playFireSound() throws Exception {
            Sound.playSound(PRIMARY_FIRE_SOUND);
      }*/
      
      // Fire weapon using alt fire mode
      public void altFire(Dodge_And_Shoot game) {
            if ( super.canAltFire() ) {
            
                  super.resetAltCooldown();
                  super.canAltFire(false);
            }
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public int getDamage()        {  return damage;       }
      public int getAltDamage()     {  return altDamage;    }

}