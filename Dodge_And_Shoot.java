import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.event.*;
import java.util.ArrayList;

public class Dodge_And_Shoot extends JPanel
{
      public static final String GAME_NAME = "Dodge And Shoot";
      public static final int GAME_SPEED = 1;

      public static final int GAME_WINDOW_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      public static final int GAME_WINDOW_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
      public static final int GAME_WINDOW_HEIGHT_OFFSET = 27;
      
      public final InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
      public final ActionMap am = getActionMap();
      
      private Player player = new Player(GAME_WINDOW_WIDTH / 2, GAME_WINDOW_HEIGHT / 2, this);
      
      private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
      private ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
      private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
      
      private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
      private int enemySpawnCooldown = Enemy.BASE_SPAWN_DELAY;
      
      public Dodge_And_Shoot() {
            
            // Up - Move Forward
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "up-released");
            am.put("up-pressed", new AbstractAction() { 

                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.upPressed(true);
                  }
                  
            });
            am.put("up-released", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.upPressed(false);
                  }
                  
            });
            
            // Right - Rotate Right
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right-released");
            am.put("right-pressed", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.rightPressed(true);
                  }
                  
            });
            am.put("right-released", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.rightPressed(false);
                  }
                 
            });
            
            // Down - Move Backward
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down-released");
            am.put("down-pressed", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.downPressed(true);
                  }
                  
            });
            am.put("down-released", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.downPressed(false);
                  }
                  
            });
            
            // Left - Rotate Left
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left-released");
            am.put("left-pressed", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.leftPressed(true);
                  }
                  
            });
            am.put("left-released", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.leftPressed(false);
                  }
                  
            });
            
            // Z - Primary Fire
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "z-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true), "z-released");
            am.put("z-pressed", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.zPressed(true);
                  }
                  
            });
            am.put("z-released", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.zPressed(false);
                  }
                  
            });
            
            // X - Alt Fire
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "x-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, true), "x-released");
            am.put("x-pressed", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.xPressed(true);
                  }
                  
            });
            am.put("x-released", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        player.xPressed(false);
                  }
                  
            });
            
            //*****************************************************************||
            //                         Debug Controls                          ||
            //*****************************************************************||
            
            // Clear all enemies
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false), "1-pressed");
            am.put("1-pressed", new AbstractAction() {
                  
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        enemies.clear();
                  }
                  
            });
            
            // Clear all projectiles from screen
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, false), "2-pressed");
            am.put("2-pressed", new AbstractAction() {
            
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        projectiles.clear();
                  }
            
            });
            
            
            Projectile.setGame(this);     
      }
      
      public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            
            player.paint(g2d);
            
            for (Enemy e : enemies) {
                  e.paint(g2d);
            }
            
            if ( !projectiles.isEmpty() ) {
                  for (Projectile p : projectiles) {
                        p.paint(g2d);
                  }
            }
      }
      
      // Move things around the screen.
      public void move() {
            player.move();
            
            if ( !enemies.isEmpty() ) {
                  for (int i = 0; i < enemies.size(); i++) {
                        enemies.get(i).move();
                  }
            }
            
            if ( !projectiles.isEmpty() ) {
                  for (int i = 0; i < projectiles.size(); i++) {
                        projectiles.get(i).move();
                  }
            }
      }
      
      // Handles all events such as countdowns or cooldowns.
      public void handleEvents() {
            player.handleEvents();
            
            if ( !enemies.isEmpty() ) {
                  for (int i = 0; i < enemies.size(); i++) {
                        enemies.get(i).handleEvents();
                  }
            }
            
            spawnEnemies();
      }
      
      // Add a projectile into the game world.
      public void addProjectile(Projectile p) {
            projectiles.add(p);
      }
      
      // Remove a projectile from the game world.
      public void removeProjectile(Projectile p) {
            projectiles.remove(p);
      }
      
      public void addPlayerProjectile(Projectile p) {
            playerProjectiles.add(p);
      }
      
      public void removePlayerProjectile(Projectile p) {
            playerProjectiles.remove(p);
      }
      
      public void addEnemyProjectile(Projectile p) {
            enemyProjectiles.add(p);
      }
      
      public void removeEnemyProjectile(Projectile p) {
            enemyProjectiles.remove(p);
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      private void spawnEnemies() {
            if (enemySpawnCooldown != 0) {
                  enemySpawnCooldown--;
            }
            else {
                  enemies.add( getRandomEnemyType() );
                  enemySpawnCooldown = Enemy.BASE_SPAWN_DELAY;
            }
      }
      
      private Enemy getRandomEnemyType() {
            int enemyType = (int)(Math.random() * (2 - 1 + 1) + 1);
            
            switch (enemyType) {
                  case 1: return new Lasereer(this);
                  case 2: return new Rocketeer(this);
            }
            
            return null;
      }
      
      public void removeEnemy(Enemy e) {
            enemies.remove(e);
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      
      public Player getPlayer() {
            return player;
      }
      
      public double[] getPlayerLocation() {
            return player.getLocation();
      }
      
      public Line2D[] getPlayerSides() {
            return player.getSides();
      }
      
      public ArrayList<Projectile> getPlayerProjectiles() {
            return playerProjectiles;
      }
      
      public ArrayList<Enemy> getEnemies() {
            return enemies;
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
      

      /*======================================================
      ========================================================
      ======================================================*/
      
      public static void main(String[] args) throws InterruptedException {
            Dodge_And_Shoot game = new Dodge_And_Shoot();
            
            JFrame frame = new JFrame(game.GAME_NAME);
            frame.add(game);
            frame.setSize(game.GAME_WINDOW_WIDTH, game.GAME_WINDOW_HEIGHT);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            while (true) {
                  game.move();
                  game.handleEvents();
                  game.repaint();
                  Thread.sleep(10);
            }
      }
      
      /*======================================================
      ========================================================
      ======================================================*/
}