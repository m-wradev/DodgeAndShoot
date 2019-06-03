import java.awt.Graphics2D;

public abstract class Spaceship
{     
      public Spaceship() {
      
      }
      
      //*************************************************************************
      // Rotate point right around an anchor
      //
      // Params: Array double p - coordinates to rotate (x, y)
      //         Array double anchor - coordinates to rotate p around (x, y)
      //         double t - Theta (degrees to rotate p around anchor)
      //*************************************************************************
      protected double[] rotatePointRight(double[] p, double[] anchor, double t) {
            double[] newCoords =  new double[2];
            
            p[0] = p[0] - anchor[0];
            p[1] = p[1] - anchor[1];
             
            newCoords[0] = (p[0] * Math.cos(t)) - (p[1] * Math.sin(t));
            newCoords[1] = (p[1] * Math.cos(t)) + (p[0] * Math.sin(t));
            
            newCoords[0] = newCoords[0] + anchor[0];
            newCoords[1] = newCoords[1] + anchor[1];
            
            return newCoords;
      }
      
      
      //*************************************************************************
      // Rotate point left around an anchor
      //
      // Params: Array double p - coordinates to rotate (x, y)
      //         Array double anchor - coordinates to rotate p around (x, y)
      //         double t - Theta (degrees to rotate p around anchor)
      //*************************************************************************
      protected double[] rotatePointLeft(double[] p, double[] anchor, double t) {
            double[] newCoords = new double[2];
            
            p[0] = p[0] - anchor[0];
            p[1] = p[1] - anchor[1];
            
            newCoords[0] = (p[0] * Math.cos(t)) + (p[1] * Math.sin(t));
            newCoords[1] = (p[1] * Math.cos(t)) - (p[0] * Math.sin(t));
            
            newCoords[0] = newCoords[0] + anchor[0];
            newCoords[1] = newCoords[1] + anchor[1];
            
            return newCoords;
      }
      
      
      
      public abstract void handleEvents();
      public abstract void paint(Graphics2D g);
      public abstract void move();
      public abstract void takeDamage(int damage);
      public abstract double getNoseX();
      public abstract double getNoseY();
      public abstract double getPointerX();
      public abstract double getPointerY();
      public abstract double[] getLocation();
}