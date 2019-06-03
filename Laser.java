import java.awt.Color;
import java.awt.Paint;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Laser extends Projectile
{
      private int length;
      private int speed;
      private double x1, y1, x2, y2;
      private double xa, ya;
      private Color color;
      private int damage;
      private Spaceship owner;
      private boolean isFromPlayer;
            
      /*======================================================
      ========================================================
      ======================================================*/
            
      // Params: Damage, Color, Length of laser, ship's midX, ship's midY, ship pointerX, ship pointerY
      public Laser(int damage, int speed, Color color, int length, double sX, double sY, double pX, double pY, Spaceship owner, boolean isFromPlayer) {
            this.damage = damage;
            this.speed = speed;
            this.color = color;
            this.length = length;
            this.owner = owner;
            this.isFromPlayer = isFromPlayer;
                  
            this.x1 = sX;
            this.y1 = sY;
            this.x2 = sX;
            this.y2 = sY;
                  
            this.xa = pX - sX;
            this.ya = pY - sY;
                  
            setSecondPointCoordinates();
      } 
            
      // Paint to JPanel
      public void paint(Graphics2D g) {
            g.setColor(color);
            g.draw( new Line2D.Double(x1, y1, x2, y2) );
      }
            
      public void checkBounds() {
            if (x1 < 0 || x1 > Dodge_And_Shoot.GAME_WINDOW_WIDTH || y1 < 0 || y1 > Dodge_And_Shoot.GAME_WINDOW_HEIGHT) {
                  game.removeProjectile(this);
                        
                  if (isFromPlayer)
                        game.removePlayerProjectile(this);
                  else
                        game.removeEnemyProjectile(this);
            }
            
            Line2D pLine = new Line2D.Double(x1, y1, x2, y2);
            
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
            }
      }
            
      // Move laser
      public void move() {
            checkBounds();
            for (int i = 0; i < speed; i++) {
                  x1 += xa;
                  y1 += ya;
                        
                  x2 += xa;
                  y2 += ya;
            }
      }
            
      public Line2D getLine() {
            return new Line2D.Double(x1, y1, x2, y1);
      }
         
      // Not used in class Laser   
      public Line2D[] getSides() {
            return null;
      }
            
      public int getDamage() {
            return damage;
      }
            
      /*======================================================
      ========================================================
      ======================================================*/
            
      // Increments x2 and y2 by xa and ya <length> times.  xa and ya act as slope of line (y / x)
      private void setSecondPointCoordinates() {
            for (int i = 0; i < length; i++) {
                  x2 += xa;
                  y2 += ya;
            }
      }

      private void dealDamage(Player p) {
            p.takeDamage(damage);
      }
      
      private void dealDamage(Enemy e) {
            e.takeDamage(damage);
      }
}