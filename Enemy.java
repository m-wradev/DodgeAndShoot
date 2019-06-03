import java.awt.geom.Line2D;

public abstract class Enemy extends Spaceship
{
      public static final int BASE_SPAWN_DELAY = 200;        // Time before another enemy is spawned

      /*======================================================
      ========================================================
      ======================================================*/
      
      protected double[] getRandomSpawnCoordinates() {
            int location = (int)(Math.random() * 4);
            
            switch (location) {
                  case 0:
                        return getSpawnCoordinatesTop();
                  case 1:
                        return getSpawnCoordinatesRight();
                  case 2:
                        return getSpawnCoordinatesBottom();
                  case 3:
                        return getSpawnCoordinatesLeft();
            }
            
            return new double[] {-1, -1};
      }

      /*======================================================
      ========================================================
      ======================================================*/
      
      // Generate random coordinates above the window
      private double[] getSpawnCoordinatesTop() {
            double xRange = (Dodge_And_Shoot.GAME_WINDOW_WIDTH + 1);
            double yRange = 101;
            
            double x = (int)(Math.random() * xRange);
            double y = (int) -(Math.random() * yRange);
            
            return new double[] {x, y};
      }
      
      // Generate random coordinates to the right of the window
      private double[] getSpawnCoordinatesRight() {
            double xRange = ( (Dodge_And_Shoot.GAME_WINDOW_WIDTH + 100) - (Dodge_And_Shoot.GAME_WINDOW_WIDTH) + 1 );
            double yRange = Dodge_And_Shoot.GAME_WINDOW_HEIGHT + 1;
            
            double x = (int)(Math.random() * xRange) + Dodge_And_Shoot.GAME_WINDOW_WIDTH;
            double y = (int)(Math.random() * yRange);
            
            return new double[] {x, y};
      }
      
      // Generate random coordinates below the window
      private double[] getSpawnCoordinatesBottom() {
            double xRange = (Dodge_And_Shoot.GAME_WINDOW_WIDTH + 1);
            double yRange = ( (Dodge_And_Shoot.GAME_WINDOW_HEIGHT + 100) - (Dodge_And_Shoot.GAME_WINDOW_HEIGHT) + 1 );
            
            double x = (int)(Math.random() * xRange);
            double y = (int)(Math.random() * yRange) + Dodge_And_Shoot.GAME_WINDOW_HEIGHT;
            
            return new double[] {x, y};
      }
      
      // Generate random coordinates to the left of the window
      private double[] getSpawnCoordinatesLeft() {
            double xRange = 101;
            double yRange = Dodge_And_Shoot.GAME_WINDOW_HEIGHT + 1;
            
            double x = (int) -(Math.random() * xRange);
            double y = (int)(Math.random() * yRange);
            
            return new double[] {x, y};
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public abstract Line2D[] getSides();
}