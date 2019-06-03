import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Rocket extends Projectile
{
      private int damage;
      private int speed;
      private Color color;
      private int length;
      private int width;
      private double[] mid = new double[2];
      private double[] p1 = new double[2];
      private double[] p2 = new double[2];
      private double[] p3 = new double[2];
      private double[] p4 = new double[2];
      private double xa, ya;
      private Spaceship owner;
      private boolean isFromPlayer;
  
      /*======================================================
      ========================================================
      ======================================================*/    
      
      // Params: Damage, Color, Length of rocket, width of rocket, ship's midX, ship's midY, ship pointerX, ship pointerY, isFromPlayer
      public Rocket(int damage, int speed, Color color, int length, int width, double sX, double sY, double pX, double pY, Spaceship owner, boolean isFromPlayer) {
            this.damage = damage;
            this.speed = speed;
            this.color = color;
            this.length = length;
            this.width = width;
            this.owner = owner;
            this.isFromPlayer = isFromPlayer;
            
            this.mid[0] = sX;
            this.mid[1] = sY;
            
            this.xa = pX - sX;
            this.ya = pY - sY;
            
            setFirstPoint();
            setSecondPoint();
            setThirdPoint();
            setFourthPoint();
      }
      
      // Top Left point
      private void setFirstPoint() {
            this.p1[0] = mid[0] - length / 2;
            this.p1[1] = mid[1] - width / 2;
      }
      
      // Bottom Left point
      private void setSecondPoint() {
            this.p2[0] = mid[0] - length / 2;
            this.p2[1] = mid[1] + width / 2;
      }
      
      // Top Right point
      private void setThirdPoint() {
            this.p3[0] = p2[0];
            this.p3[1] = p2[1];
            
            for (int i = 0; i < length; i++) {
                  p3[0] += xa;
                  p3[1] += ya;
            }
      }
      
      // Bottom Right point
      private void setFourthPoint() {
            this.p4[0] = p1[0];
            this.p4[1] = p1[1];
            
            for (int i = 0; i < length; i++) {
                  p4[0] += xa;
                  p4[1] += ya;
            }
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public void paint(Graphics2D g) {
            g.setColor(color);
      
            g.draw(new Line2D.Double(p1[0], p1[1], p2[0], p2[1]));
            g.draw(new Line2D.Double(p2[0], p2[1], p3[0], p3[1]));
            g.draw(new Line2D.Double(p3[0], p3[1], p4[0], p4[1]));
            g.draw(new Line2D.Double(p4[0], p4[1], p1[0], p1[1]));
      }
      
      public void move() {
            for (int i = 0; i < speed; i++) {
                  p1[0] += xa;
                  p2[0] += xa;
                  p3[0] += xa;
                  p4[0] += xa;
            
                  p1[1] += ya;
                  p2[1] += ya;
                  p3[1] += ya;
                  p4[1] += ya;
             }
      }
      
      public void checkBounds() {
            /*if (p3[0] < 0 || p3[0] > Dodge_And_Shoot.GAME_WINDOW_WIDTH || p3[1] < 0 || p3[1] > Dodge_And_Shoot.GAME_WINDOW_HEIGHT) {
                  game.removeProjectile(this);
                        
                  if (isFromPlayer)
                        game.removePlayerProjectile(this);
                  else
                        game.removeEnemyProjectile(this);
            }
            
            Line2D pLine1 = new Line2D.Double(x1, y1, x2, y2);
            
            if (isFromPlayer) {
                  ArrayList<Enemy> enemies = game.getEnemies();
                  
                  for (int i = 0; i < enemies.size(); i++) {
                        Line2D[] sides = enemies.get(i).getSides();
                        
                        for (int n = 0; n < sides.length; n++) {
                              Line2D side = sides[n];
                              
                              if ( pLine.intersectsLine(side) ) {
                                    dealDamage( enemies.get(i) );
                                    game.removeProjectile(this);
                                    game.removePlayerProjectile(this);
                              }
                        }
                  }
            }
            else if (!isFromPlayer) {
                  Line2D[] sides = game.getPlayerSides();
                  
                  for (int i = 0; i < sides.length; i++) {
                        if ( pLine.intersectsLine(sides[i]) ) {
                              dealDamage( game.getPlayer() );
                              game.removeProjectile(this);
                              game.removeEnemyProjectile(this);
                              break;
                        }
                  }
            }*/
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public int getDamage() {
            return damage;
      }
      
      // Not used in class Rocket
      public Line2D getLine() {
            return null;
      }
      
      public Line2D[] getSides() {
            return null;
      }
}