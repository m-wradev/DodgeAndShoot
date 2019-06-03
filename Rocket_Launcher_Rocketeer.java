import java.awt.Color;

public class Rocket_Launcher_Rocketeer extends Weapon_System
{
      public final int BASE_PRIMARY_DAMAGE = 15;
      public final int BASE_PRIMARY_COOLDOWN = 30;
      public final Color PRIMARY_FIRE_COLOR = Color.black;
      public final int PRIMARY_FIRE_LENGTH = 30;
      public final int PRIMARY_FIRE_WIDTH = 10;
      public final int PRIMARY_FIRE_PROJECTILE_SPEED = 3;
      
      private int damage;
      private int altDamage;
      
      private Rocketeer owner;
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public Rocket_Launcher_Rocketeer(Rocketeer owner) {
            this.damage = BASE_PRIMARY_DAMAGE;
            this.owner = owner;
            
            super.setCooldownValues(BASE_PRIMARY_DAMAGE, 0);
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
            if (canFire()) {
                  Projectile p1 = new Rocket( BASE_PRIMARY_DAMAGE, PRIMARY_FIRE_PROJECTILE_SPEED, PRIMARY_FIRE_COLOR, PRIMARY_FIRE_LENGTH, PRIMARY_FIRE_WIDTH, owner.getLeftLauncherX(), owner.getLeftLauncherY(), owner.getLeftLauncherPointerX(), owner.getLeftLauncherPointerY(), owner, false );
                  Projectile p2 = new Rocket( BASE_PRIMARY_DAMAGE, PRIMARY_FIRE_PROJECTILE_SPEED, PRIMARY_FIRE_COLOR, PRIMARY_FIRE_LENGTH, PRIMARY_FIRE_WIDTH, owner.getRightLauncherX(), owner.getRightLauncherY(), owner.getRightLauncherPointerX(), owner.getRightLauncherPointerY(), owner, false );
            
                  game.addProjectile(p1);
                  game.addProjectile(p2);
            
                  super.resetCooldown();
                  super.canFire(false);
            }
      }
      
      public void altFire(Dodge_And_Shoot game) {
      
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public int getDamage()     {  return damage;    }
      public int getAltDamage()  {  return altDamage; }
      
}