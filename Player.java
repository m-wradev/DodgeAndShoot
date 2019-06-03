import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import java.awt.geom.Line2D;

public class Player extends Spaceship
{     
      public final int WIDTH = 30;                    // Width of base
      public final Color SHIP_COLOR = Color.blue;     // Color of the ship
      
      public final double ROT_DEG = 4;                // Degrees rotated
      public final double MAX_SPEED = 5;              // Speed of ship
      public final double MAX_VELOCITY = 1;           // Max velocity (1 so that speed doesn't go faster than max speed)
      public final double FRWD_VEL_STEP = 0.007;      // How fast ship gains speed going forward
      public final double BKWD_VEL_STEP = 0.005;      // How fast ship gains speed going backward
      public final double DECELERATION_STEP = 0.005;  // How fast ship decelerates
      
      public final int HP = 100;
      public final Laser_Turret DEFAULT_STARTING_WEAPON = new Laser_Turret(this);

      private boolean upPressed = false;
      private boolean rightPressed = false;
      private boolean downPressed = false;
      private boolean leftPressed = false;
      private boolean xPressed = false;
      private boolean zPressed = false;
      
      private double[] xArray = new double[3];
      private double[] yArray = new double[3];
      private double[] mid = new double[2];
      private double[] pointer = new double[2];

      private double ya, xa;
      private double currentRotation;
      private double forwardVelocity, backwardVelocity;

      private Dodge_And_Shoot game;
     
      private int hp = HP;
      private Weapon_System currentWeapon;
      
      /*======================================================
      ========================================================
      ======================================================*/      
      
      public Player(int midX, int midY, Dodge_And_Shoot game) {
            this.game = game;
      
            mid[0] = (double)midX;
            mid[1] = (double)midY;
     
            xArray[0] = midX - (double)(WIDTH / 2);
            xArray[1] = midX + (double)(WIDTH / 2);
            xArray[2] = midX - (double)(WIDTH / 2);
            
            yArray[0] = midY + (double)(WIDTH / 2);
            yArray[1] = midY;
            yArray[2] = midY - (double)(WIDTH / 2);
            
            pointer[0] = xArray[1] + 1;
            pointer[1] = yArray[1];
            
            currentWeapon = DEFAULT_STARTING_WEAPON;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/      
      
      // Handles events such as weapon cooldowns.
      public void handleEvents() {
            currentWeapon.handleEvents();
      }
      
      public void paint(Graphics2D g) {
            g.setColor(SHIP_COLOR);
            
            g.draw(new Line2D.Double(xArray[0], yArray[0], xArray[1], yArray[1]));
            g.draw(new Line2D.Double(xArray[1], yArray[1], xArray[2], yArray[2]));
            g.draw(new Line2D.Double(xArray[2], yArray[2], xArray[0], yArray[0]));
      }
      
      /*======================================================
      ========================================================
      ======================================================*/      
      
      public void upPressed(boolean isPressed) {
            upPressed = isPressed;
      }
      
      public void rightPressed(boolean isPressed) {
            rightPressed = isPressed;
      }
      
      public void downPressed(boolean isPressed) {
            downPressed = isPressed;
      }
      
      public void leftPressed(boolean isPressed) {
            leftPressed = isPressed;
      }
      
      public void xPressed(boolean isPressed) {
            xPressed = isPressed;
      }
      
      public void zPressed(boolean isPressed) {
            zPressed = isPressed;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public boolean upPressed()       {  return upPressed;       } 
      public boolean rightPressed()    {  return rightPressed;    }
      public boolean downPressed()     {  return downPressed;     }
      public boolean leftPressed()     {  return leftPressed;     }
      public boolean xPressed()        {  return xPressed;        }  
      public boolean zPressed()        {  return zPressed;        }
      
      public double getNoseX()         {  return xArray[1];       }
      public double getNoseY()         {  return yArray[1];       }
      public double getPointerX()      {  return pointer[0];      }
      public double getPointerY()      {  return pointer[1];      }
      public double getXA()            {  return xa;              }
      public double getYA()            {  return ya;              }
      public double getRotation()      {  return currentRotation; }
      
      public double[] getLocation()    {  return mid;             }
      
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
      }
       
      // Handles key presses and moves ship by XA and YA
      public void move() {
      
            if (upPressed && rightPressed) {
                  forwardAccelerate();
                  moveForward();
                  rotateRight();
            }
            else if (upPressed && leftPressed) {
                  forwardAccelerate();
                  moveForward();
                  rotateLeft();
            }
            else if (downPressed && rightPressed) {
                  backwardAccelerate();
                  moveBackward();
                  rotateRight();
            }
            else if (downPressed && leftPressed) {
                  backwardAccelerate();
                  moveBackward();
                  rotateLeft();
            }
            else if (upPressed) {
                  forwardAccelerate();
                  moveForward();
            }
            else if (downPressed) {
                  backwardAccelerate();
                  moveBackward();
            }
            else if (rightPressed) {
                  rotateRight();
            }
            else if (leftPressed) {
                  rotateLeft();
            }
            
            if (zPressed) {
                  firePrimary();
            }
            else if (xPressed) {
                  fireSecondary();
            }
            
            if (!upPressed && !downPressed) {
                  decelerate();
            }
            
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
      
      // Increases ships forward velocity until MAX_SPEED is reached
      // If ship is moving backward when method is called, reduce backward velocity until 0, then accelerate forward.
      private void forwardAccelerate() {
            if (backwardVelocity > 0) {
                  backwardVelocity -= FRWD_VEL_STEP;
            }
            else if (forwardVelocity < MAX_VELOCITY) {
                  forwardVelocity += FRWD_VEL_STEP;
            }
      }
      
      // Increases ships backward velocity until MAX_SPEED is reach
      // If ship is moving forward when method is called, reduce forward velocity until 0, then acclerate backward.
      private void backwardAccelerate() {
            if (forwardVelocity > 0) {
                  forwardVelocity -= BKWD_VEL_STEP;
            }
            else if (backwardVelocity < MAX_VELOCITY) {
                  backwardVelocity += BKWD_VEL_STEP;
            }
      }
      
      // Decelerate ship until it stops moving
      private void decelerate() {
            if (forwardVelocity > 0) {
                  forwardVelocity -= FRWD_VEL_STEP;
                  xa = (pointer[0] - getNoseX()) * (MAX_SPEED * forwardVelocity);
                  ya = (pointer[1] - getNoseY()) * (MAX_SPEED * forwardVelocity);                  
            }
            if (backwardVelocity > 0) {
                  backwardVelocity -= BKWD_VEL_STEP;
                  xa = -(pointer[0] - getNoseX()) * (MAX_SPEED * backwardVelocity);
                  ya = -(pointer[1] - getNoseY()) * (MAX_SPEED * backwardVelocity);
            }
            
            
      }
      
      // Moves ship forward
      private void moveForward() {
            if (backwardVelocity > 0) {
                  decelerate();
            }
            else {
                  xa = (pointer[0] - getNoseX()) * (MAX_SPEED * forwardVelocity);
                  ya = (pointer[1] - getNoseY()) * (MAX_SPEED * forwardVelocity);      
            }     
      }
      
      // Moves ship backward
      private void moveBackward() {
            if (forwardVelocity > 0) {
                  decelerate();
            }
            else {
                  xa = -(pointer[0] - getNoseX()) * (MAX_SPEED * backwardVelocity);
                  ya = -(pointer[1] - getNoseY()) * (MAX_SPEED * backwardVelocity);
            }
      }
      
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
            
            currentRotation += ROT_DEG;
            if (currentRotation > 360)
                  currentRotation -= 360;
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
            
            currentRotation -= ROT_DEG;
            if (currentRotation < -360)
                  currentRotation += 360;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      // Fire primary weapon
      private void firePrimary() {
            currentWeapon.fire(game);
      }
      
      // Fire alt fire
      private void fireSecondary() {
            currentWeapon.altFire(game);
      }
}