import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Rocketeer extends Enemy
{
      public final int MAIN_BODY_WIDTH = 15;
      public final int PRONG_WIDTH = 10;
      public final int MAIN_BODY_LENGTH = 35;
      public final int PRONG_LENGTH = 40;
      public final Color SHIP_COLOR = Color.black;
      
      public final double ROT_DEG  = 2.5;
      public final double MAX_SPEED = 4;
      public final int HP = 10;
      public final Weapon_System WEAPON = new Rocket_Launcher_Rocketeer(this);
      
      private double[] mid = new double[2];
      private double[] pointer = new double[2];
      private double[] xArray = new double[8];
      private double[] yArray = new double[8];
      
      private double[] leftLauncher = new double[2];
      private double[] leftLauncherPointer = new double[2];
      private double[] rightLauncher = new double[2];
      private double[] rightLauncherPointer = new double[2];
      
      /*private double leftLauncherP1[] = new double[2];
      private double leftLauncherP2[] = new double[2];
      private double rightLauncherP1[] = new double[2];
      private double rightLauncherP2[] = new double[2];*/
      
      private double xa, ya;
      
      private int hp = HP;
      
      private Dodge_And_Shoot game;

      /*======================================================
      ========================================================
      ======================================================*/

      public Rocketeer(Dodge_And_Shoot game) {
            mid = super.getRandomSpawnCoordinates();
            
            this.xArray[0] = mid[0] - MAIN_BODY_WIDTH;
            this.xArray[1] = mid[0] + PRONG_LENGTH / 2;
            this.xArray[2] = mid[0] + PRONG_LENGTH / 2;
            this.xArray[3] = mid[0];
            this.xArray[4] = mid[0];
            this.xArray[5] = mid[0] + PRONG_LENGTH / 2;
            this.xArray[6] = mid[0] + PRONG_LENGTH / 2;
            this.xArray[7] = mid[0] - MAIN_BODY_WIDTH;
            
            this.yArray[0] = mid[1] - MAIN_BODY_LENGTH / 2 - PRONG_WIDTH;
            this.yArray[1] = mid[1] - MAIN_BODY_LENGTH / 2 - PRONG_WIDTH;
            this.yArray[2] = mid[1] - MAIN_BODY_LENGTH / 2;
            this.yArray[3] = mid[1] - MAIN_BODY_LENGTH / 2;
            this.yArray[4] = mid[1] + MAIN_BODY_LENGTH / 2;
            this.yArray[5] = mid[1] + MAIN_BODY_LENGTH / 2;
            this.yArray[6] = mid[1] + MAIN_BODY_LENGTH / 2 + PRONG_WIDTH;
            this.yArray[7] = mid[1] + MAIN_BODY_LENGTH / 2 + PRONG_WIDTH;
            
            this.leftLauncher[0] = mid[0] + PRONG_LENGTH / 2;
            this.leftLauncher[1] = mid[1] - (MAIN_BODY_LENGTH / 2) - (PRONG_WIDTH / 2);
            this.leftLauncherPointer[0] = leftLauncher[0] + 1;
            this.leftLauncherPointer[1] = leftLauncher[1];
            
            /*this.leftLauncherP1[0] = xArray[1];
            this.leftLauncherP1[1] = yArray[1];
            this.leftLauncherP2[0] = xArray[2];
            this.leftLauncherP2[1] = yArray[2];*/
            
            this.rightLauncher[0] = mid[0] + PRONG_LENGTH / 2;
            this.rightLauncher[1] = mid[1] + (MAIN_BODY_LENGTH / 2) + (PRONG_WIDTH / 2);
            this.rightLauncherPointer[0] = rightLauncher[0] + 1;
            this.rightLauncherPointer[1] = rightLauncher[1];
            
            /*this.rightLauncherP1[0] = xArray[6];
            this.rightLauncherP1[1] = yArray[6];*/
            
            this.pointer[0] = mid[0] + 1;
            this.pointer[1] = mid[1];
            
            this.game = game;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public double getNoseX()                  {  return mid[0];                   }
      public double getNoseY()                  {  return mid[1];                   }
      public double getLeftLauncherX()          {  return leftLauncher[0];          }
      public double getLeftLauncherY()          {  return leftLauncher[1];          }
      public double getLeftLauncherPointerX()   {  return leftLauncherPointer[0];   }
      public double getLeftLauncherPointerY()   {  return leftLauncherPointer[1];   }
      public double getRightLauncherX()         {  return rightLauncher[0];         }
      public double getRightLauncherY()         {  return rightLauncher[1];         }
      public double getRightLauncherPointerX()  {  return rightLauncherPointer[0];  }
      public double getRightLauncherPointerY()  {  return rightLauncherPointer[1];  }
      public double getPointerX()               {  return pointer[0];               }
      public double getPointerY()               {  return pointer[1];               }
      public double getXA()                     {  return xa;                       }
      public double getYA()                     {  return ya;                       }
      
      public double[] getLocation()             {  return mid;                      }
      
      public Line2D[] getSides() {
            Line2D[] sides = new Line2D[8];
            
            sides[0] = new Line2D.Double(xArray[0], yArray[0], xArray[1], yArray[1]);
            sides[1] = new Line2D.Double(xArray[1], yArray[1], xArray[2], yArray[2]);
            sides[2] = new Line2D.Double(xArray[2], yArray[2], xArray[3], yArray[3]);
            sides[3] = new Line2D.Double(xArray[3], yArray[3], xArray[4], yArray[4]);
            sides[4] = new Line2D.Double(xArray[3], yArray[3], xArray[4], yArray[4]);
            sides[5] = new Line2D.Double(xArray[4], yArray[4], xArray[5], yArray[5]);
            sides[6] = new Line2D.Double(xArray[5], yArray[5], xArray[6], yArray[6]);
            sides[7] = new Line2D.Double(xArray[7], yArray[7], xArray[0], yArray[0]);
            
            return sides;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      // Decreases health by damage.  If health reaches 0, removes ship from the game.
      public void takeDamage(int damage) {
            hp -= damage;
            
            if (hp <= 0)
                  game.removeEnemy(this);
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      // Draws ship to the screen
      public void paint(Graphics2D g) {
            g.setColor(SHIP_COLOR);
            
            g.draw(new Line2D.Double(xArray[0], yArray[0], xArray[1], yArray[1]));
            g.draw(new Line2D.Double(xArray[1], yArray[1], xArray[2], yArray[2]));
            g.draw(new Line2D.Double(xArray[2], yArray[2], xArray[3], yArray[3]));
            g.draw(new Line2D.Double(xArray[3], yArray[3], xArray[4], yArray[4]));
            g.draw(new Line2D.Double(xArray[4], yArray[4], xArray[5], yArray[5]));
            g.draw(new Line2D.Double(xArray[5], yArray[5], xArray[6], yArray[6]));
            g.draw(new Line2D.Double(xArray[6], yArray[6], xArray[7], yArray[7]));
            g.draw(new Line2D.Double(xArray[7], yArray[7], xArray[0], yArray[0]));
      }
      
      public void handleEvents() {
      
      }
      
      // Moves ship forward by xa and ya.  Rotates to face player.
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
                  WEAPON.fire(game);
            }
            
                    
            xa = (pointer[0] - getNoseX());
            ya = (pointer[1] - getNoseY());
            
            mid[0] += xa;
            xArray[0] += xa;
            xArray[1] += xa;
            xArray[2] += xa;
            xArray[3] += xa;
            xArray[4] += xa;
            xArray[5] += xa;
            xArray[6] += xa;
            xArray[7] += xa;
            leftLauncher[0] += xa;
            leftLauncherPointer[0] += xa;
            rightLauncher[0] += xa;
            rightLauncherPointer[0] += xa;
            pointer[0] += xa;
      
            mid[1] += ya;
            yArray[0] += ya;
            yArray[1] += ya;
            yArray[2] += ya;
            yArray[3] += ya;
            yArray[4] += ya;
            yArray[5] += ya;
            yArray[6] += ya;
            yArray[7] += ya;
            leftLauncher[1] += ya;
            leftLauncherPointer[1] += ya;
            rightLauncher[1] += ya;
            rightLauncherPointer[1] += ya;
            pointer[1] += ya;
      }
      
      // Rotate ship counterclockwise
      private void rotateRight() {
            double[] p1 = {xArray[0], yArray[0]};
            double[] p2 = {xArray[1], yArray[1]};
            double[] p3 = {xArray[2], yArray[2]};
            double[] p4 = {xArray[3], yArray[3]};
            double[] p5 = {xArray[4], yArray[4]};
            double[] p6 = {xArray[5], yArray[5]};
            double[] p7 = {xArray[6], yArray[6]};
            double[] p8 = {xArray[7], yArray[7]};
            
            p1 = super.rotatePointRight(p1, mid, Math.toRadians(ROT_DEG));
            p2 = super.rotatePointRight(p2, mid, Math.toRadians(ROT_DEG));
            p3 = super.rotatePointRight(p3, mid, Math.toRadians(ROT_DEG));
            p4 = super.rotatePointRight(p4, mid, Math.toRadians(ROT_DEG));
            p5 = super.rotatePointRight(p5, mid, Math.toRadians(ROT_DEG));
            p6 = super.rotatePointRight(p6, mid, Math.toRadians(ROT_DEG));
            p7 = super.rotatePointRight(p7, mid, Math.toRadians(ROT_DEG));
            p8 = super.rotatePointRight(p8, mid, Math.toRadians(ROT_DEG));
            leftLauncher = super.rotatePointRight(leftLauncher, mid, Math.toRadians(ROT_DEG));
            leftLauncherPointer = super.rotatePointRight(leftLauncherPointer, mid, Math.toRadians(ROT_DEG));
            rightLauncher = super.rotatePointRight(rightLauncher, mid, Math.toRadians(ROT_DEG));
            rightLauncherPointer = super.rotatePointRight(rightLauncher, mid, Math.toRadians(ROT_DEG));
            pointer = super.rotatePointRight(pointer, mid, Math.toRadians(ROT_DEG));
            
            xArray[0] = p1[0];
            xArray[1] = p2[0];
            xArray[2] = p3[0];
            xArray[3] = p4[0];
            xArray[4] = p5[0];
            xArray[5] = p6[0];
            xArray[6] = p7[0];
            xArray[7] = p8[0];
            
            yArray[0] = p1[1];
            yArray[1] = p2[1];
            yArray[2] = p3[1];
            yArray[3] = p4[1];
            yArray[4] = p5[1];
            yArray[5] = p6[1];
            yArray[6] = p7[1];
            yArray[7] = p8[1];
      }
      
      // Rotate ship clockwise
      private void rotateLeft() {
            double[] p1 = {xArray[0], yArray[0]};
            double[] p2 = {xArray[1], yArray[1]};
            double[] p3 = {xArray[2], yArray[2]};
            double[] p4 = {xArray[3], yArray[3]};
            double[] p5 = {xArray[4], yArray[4]};
            double[] p6 = {xArray[5], yArray[5]};
            double[] p7 = {xArray[6], yArray[6]};
            double[] p8 = {xArray[7], yArray[7]};
            
            p1 = super.rotatePointLeft(p1, mid, Math.toRadians(ROT_DEG));
            p2 = super.rotatePointLeft(p2, mid, Math.toRadians(ROT_DEG));
            p3 = super.rotatePointLeft(p3, mid, Math.toRadians(ROT_DEG));
            p4 = super.rotatePointLeft(p4, mid, Math.toRadians(ROT_DEG));
            p5 = super.rotatePointLeft(p5, mid, Math.toRadians(ROT_DEG));
            p6 = super.rotatePointLeft(p6, mid, Math.toRadians(ROT_DEG));
            p7 = super.rotatePointLeft(p7, mid, Math.toRadians(ROT_DEG));
            p8 = super.rotatePointLeft(p8, mid, Math.toRadians(ROT_DEG));
            leftLauncher = super.rotatePointLeft(leftLauncher, mid, Math.toRadians(ROT_DEG));
            leftLauncherPointer = super.rotatePointLeft(leftLauncherPointer, mid, Math.toRadians(ROT_DEG));
            rightLauncher = super.rotatePointLeft(rightLauncher, mid, Math.toRadians(ROT_DEG));
            rightLauncherPointer = super.rotatePointLeft(rightLauncherPointer, mid, Math.toRadians(ROT_DEG));
            pointer = super.rotatePointLeft(pointer, mid, Math.toRadians(ROT_DEG));
            
            xArray[0] = p1[0];
            xArray[1] = p2[0];
            xArray[2] = p3[0];
            xArray[3] = p4[0];
            xArray[4] = p5[0];
            xArray[5] = p6[0];
            xArray[6] = p7[0];
            xArray[7] = p8[0];
            
            yArray[0] = p1[1];
            yArray[1] = p2[1];
            yArray[2] = p3[1];
            yArray[3] = p4[1];
            yArray[4] = p5[1];
            yArray[5] = p6[1];
            yArray[6] = p7[1];
            yArray[7] = p8[1];
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