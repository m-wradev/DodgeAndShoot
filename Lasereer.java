import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Lasereer extends Enemy
{
      public final int WIDTH = 65;
      public final Color SHIP_COLOR = Color.black;
 
      public final double ROT_DEG = 2.5;
      public final double MAX_SPEED = 4.3;
      public final int MAX_FIRE_RANGE = 250;
      
      public final int HP = 5;
      public final Weapon_System WEAPON = new Laser_Turret_Lasereer(this);      
  
      private double[] mid = new double[2];
      private double[] pointer = new double[2];
      private double[] xArray = new double[3];
      private double[] yArray = new double[3];
      private double xa, ya;

      private int hp = HP;

      private Dodge_And_Shoot game;
      
      /*======================================================
      ========================================================
      ======================================================*/      

      public Lasereer(Dodge_And_Shoot game) {
            mid = super.getRandomSpawnCoordinates();
           
            xArray[0] = mid[0] - (double)(WIDTH / 2);
            xArray[1] = mid[0] + (double)(WIDTH / 2);
            xArray[2] = mid[0] - (double)(WIDTH / 2);
            
            yArray[0] = mid[1] + (double)(WIDTH / 2);
            yArray[1] = mid[1];
            yArray[2] = mid[1] - (double)(WIDTH / 2);
            
            pointer[0] = xArray[1] + 1;
            pointer[1] = yArray[1];
            
            this.game = game;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public double getNoseX()         {  return xArray[1];    }
      public double getNoseY()         {  return yArray[1];    }
      public double getPointerX()      {  return pointer[0];   }
      public double getPointerY()      {  return pointer[1];   }
      public double getXA()            {  return xa;           }
      public double getYA()            {  return ya;           }
      
      public double[] getLocation()    {  return mid;          }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public Line2D[] getSides() {
            Line2D[] sides = new Line2D[3];
            
            sides[0] = new Line2D.Double(xArray[0], yArray[0], xArray[1], yArray[1]);
            sides[1] = new Line2D.Double(xArray[1], yArray[1], xArray[2], yArray[2]);
            sides[2] = new Line2D.Double(xArray[2], yArray[2], xArray[0], yArray[0]);
            
            return sides;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public void takeDamage(int damage) {
            hp -= damage;
            
            if (hp >= 0) {
                  game.removeEnemy(this);
            }
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public void paint(Graphics2D g) {
            g.setColor(SHIP_COLOR);
            
            g.draw(new Line2D.Double(xArray[0], yArray[0], xArray[1], yArray[1]));
            g.draw(new Line2D.Double(xArray[1], yArray[1], xArray[2], yArray[2]));
            g.draw(new Line2D.Double(xArray[2], yArray[2], xArray[0], yArray[0]));
      }
      
      public void handleEvents() { 
            WEAPON.handleEvents();
      }
      
      // Moves ship forward by xa and ya.  Rotates to face player and fires if in range.
      public void move() {
            double[] playerLocation = game.getPlayerLocation();
            double[] myLocation = getLocation();
            
            double xDist = myLocation[0] - playerLocation[0];
            double yDist = myLocation[1] - playerLocation[1];
            double xPointerDist = pointer[0] - playerLocation[0];
            double yPointerDist = pointer[1] - playerLocation[1];
            
            double shipAngle = Math.toDegrees( Math.atan2(yDist, xDist) );
            double pointerAngle = Math.toDegrees( Math.atan2(yPointerDist, xPointerDist) );
            double angleDifference = shipAngle - pointerAngle;
            
            if (!isFacingPlayer(playerLocation[0], playerLocation[1])) {
                  if (angleDifference > 0)
                        rotateLeft();
                  else if (angleDifference < 0)
                        rotateRight();
            }
            else {
                  if (Math.abs(playerLocation[0] - myLocation[0]) < MAX_FIRE_RANGE)
                        WEAPON.fire(game);
            }
            
                    
            xa = (pointer[0] - getNoseX());
            ya = (pointer[1] - getNoseY());
            
            mid[0] += xa;
            xArray[0] += xa;
            xArray[1] += xa;
            xArray[2] += xa;
            pointer[0] += xa;
      
            mid[1] += ya;
            yArray[0] += ya;
            yArray[1] += ya;
            yArray[2] += ya;
            pointer[1] += ya;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      // Rotate the ship's vertices clockwise around the middle of the triangle.
      private void rotateRight() {
            double[] p1 = new double[] {xArray[0], yArray[0]};
            double[] p2 = new double[] {xArray[1], yArray[1]};
            double[] p3 = new double[] {xArray[2], yArray[2]};
            
            p1 = super.rotatePointRight(p1, mid, Math.toRadians(ROT_DEG));
            p2 = super.rotatePointRight(p2, mid, Math.toRadians(ROT_DEG));
            p3 = super.rotatePointRight(p3, mid, Math.toRadians(ROT_DEG));
            pointer = super.rotatePointRight(pointer, mid, Math.toRadians(ROT_DEG));
            
            xArray[0] = p1[0];
            xArray[1] = p2[0];
            xArray[2] = p3[0];
            
            yArray[0] = p1[1];
            yArray[1] = p2[1];
            yArray[2] = p3[1];
      }
      
      // Rotate the ship's vertices counter-clockwise around the middle of the triangle.
      private void rotateLeft() {
            double[] p1 = new double[] {xArray[0], yArray[0]};
            double[] p2 = new double[] {xArray[1], yArray[1]};
            double[] p3 = new double[] {xArray[2], yArray[2]};
            
            p1 = super.rotatePointLeft(p1, mid, Math.toRadians(ROT_DEG));
            p2 = super.rotatePointLeft(p2, mid, Math.toRadians(ROT_DEG));
            p3 = super.rotatePointLeft(p3, mid, Math.toRadians(ROT_DEG));
            pointer = super.rotatePointLeft(pointer, mid, Math.toRadians(ROT_DEG));
            
            xArray[0] = p1[0];
            xArray[1] = p2[0];
            xArray[2] = p3[0];
            
            yArray[0] = p1[1];
            yArray[1] = p2[1];
            yArray[2] = p3[1];
      }
      
      // Check if ship is pointed toward player
      private boolean isFacingPlayer(double playerX, double playerY) {
            double testX = pointer[0];
            double testY = pointer[1];
            
            for (int i = 0; i < Dodge_And_Shoot.GAME_WINDOW_WIDTH + 300; i++) {
                  testY += ya;
                  testX += xa;
                  
                  if ( ( testY < (playerY + 30) && testY > (playerY - 30) ) && ( testX < (playerX + 3) && testX > (playerX - 3) ) )
                        return true; 
            }
            
            return false;
      }
}