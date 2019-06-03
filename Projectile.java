import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public abstract class Projectile
{
      protected static Dodge_And_Shoot game;
      private int totalFired;
      
      public static void setGame(Dodge_And_Shoot g) {
            game = g;
      }
      
      public int getTotalFired()  {  return totalFired;   }
      
      // Tracks how many shots have been fired in total
      protected void weaponFired() {
            totalFired++;
      }
      
      public abstract void paint(Graphics2D g);
      public abstract void move();
      public abstract void checkBounds();
      public abstract int getDamage();
      public abstract Line2D getLine();
      public abstract Line2D[] getSides();
     
}