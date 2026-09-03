package foodchain.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import foodchain.model.Animal;
import foodchain.model.Food;
import foodchain.model.Simulation;
import foodchain.util.Log;
import foodchain.model.Predator;

public class Panel extends JPanel {
    
    private Simulation model;
    private Dashboard infoPanel; 
    final int SCREEN_SIZE = 600; 
    int unitSize;
    private Image imgBackground;
    private Image imgObstacle;
    private Image imgFood;
    private Image imgApex;      
    private Image imgPredator;  
    private Image imgPrey;
    private boolean isProcessingTurn = false;
    /**
     * ): Constructor. Initializes the panel's dimensions based on the SCREEN_SIZE constant
     * @param model
     * @param infoPanel
     */
    public Panel(Simulation model, Dashboard infoPanel) {
        this.model = model;
        this.infoPanel = infoPanel;
        
        unitSize = SCREEN_SIZE / model.getBoardSize();
        this.setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addMouseListener(new MyMouseAdapter());
        this.addKeyListener(new MyKeyAdapter());
        
        loadImages();
    }
    /**
     * Identifies the current Era from the model and loads the corresponding theme assets 
     */
    private void loadImages() {
        try {
            Animal player = model.getPlayer();
            Animal.Era currentEra = Animal.Era.PRESENT; 
            if (player != null) {
                currentEra = player.getEra();
            }

            Animal apexAnimal = null;
            Animal predatorAnimal = null;
            Animal preyAnimal = null;
            
            for (Animal a : model.getAnimals()) {
                if (a.getType() == Animal.Type.APEX_PREDATOR) apexAnimal = a;
                else if (a.getType() == Animal.Type.PREDATOR) predatorAnimal = a;
                else if (a.getType() == Animal.Type.PREY) preyAnimal = a;
            }
            
            String apexName = (apexAnimal != null) ? apexAnimal.getName() : "";
            String predatorName = (predatorAnimal != null) ? predatorAnimal.getName() : "";
            String preyName = (preyAnimal != null) ? preyAnimal.getName() : "";

            if (currentEra == Animal.Era.PRESENT) {
                imgBackground = loadSmartImage("present_bg"); 
                imgObstacle = loadSmartImage("obstacle_present");

                if (preyName.equalsIgnoreCase("Deer") || preyName.equalsIgnoreCase("Eder")) {
                	imgFood = loadSmartImage("food_shrubs_present");
                }
                else {
                	imgFood = loadSmartImage("food_grass_present");
                }

                if (apexName.equalsIgnoreCase("Lion")) {
                	imgApex = loadSmartImage("apex_lion_present"); 
                }
                else if (apexName.equalsIgnoreCase("Polar Bear")) {
                	imgApex = loadSmartImage("apex_polar_bear_present");
                }
                else {
                	imgApex = loadSmartImage("apex");
                }
                
                if (predatorName.equalsIgnoreCase("Cheetah")) {
                	imgPredator = loadSmartImage("predator_cheetah_present"); 
                }
                else if (predatorName.equalsIgnoreCase("Wolf")) {
                	imgPredator = loadSmartImage("predator_wolf_present");
                }
                else {
                	imgPredator = loadSmartImage("predator");
                }
                
                if (preyName.equalsIgnoreCase("Bunny")) {
                	imgPrey = loadSmartImage("prey_bunny_present");
                }
                else if (preyName.equalsIgnoreCase("Deer") || preyName.equalsIgnoreCase("Eder")) {
                	imgPrey = loadSmartImage("prey_deer_present");
                }
                else {
                	imgPrey = loadSmartImage("prey");
                }
                
            } 
            else if (currentEra == Animal.Era.PAST) {
                imgBackground = loadSmartImage("past_bg");
                imgObstacle = loadSmartImage("obstacle_past");
                
                if (preyName.equalsIgnoreCase("Stegosaurus")) {
                	imgFood = loadSmartImage("food_cycads_past");
                }
                else {
                	imgFood = loadSmartImage("food_ferns_past"); 
                }

                if (apexName.equalsIgnoreCase("T-Rex") || apexName.equalsIgnoreCase("Tyrannosaurus")) {
                	imgApex = loadSmartImage("apex_trex_past");
                }
                else if (apexName.equalsIgnoreCase("Giganotosaurus")) {
                	imgApex = loadSmartImage("apex_giganotosaurus_past");
                }
                else {
                	imgApex = loadSmartImage("apex_past");
                }

                if (predatorName.equalsIgnoreCase("Velociraptor")) {
                	imgPredator = loadSmartImage("predator_velociraptor_past");
                }
                else if (predatorName.equalsIgnoreCase("Allosaurus")) {
                	imgPredator = loadSmartImage("predator_allosaurus_past");
                }
                else {
                	imgPredator = loadSmartImage("predator_past");
                }

                if (preyName.equalsIgnoreCase("Triceratops")) {
                	imgPrey = loadSmartImage("prey_triceratops_past");
                }
                else if (preyName.equalsIgnoreCase("Stegosaurus")) {
                	imgPrey = loadSmartImage("prey_stegosaurus_past");
                }
                else {
                	imgPrey = loadSmartImage("prey_past");
                }

            } 
            else {
                imgBackground = loadSmartImage("future_bg"); 
                imgObstacle = loadSmartImage("obstacle_future");

                if (preyName.equalsIgnoreCase("Robot")) {
                	imgFood = loadSmartImage("food_energy_future");
                }
                else if (preyName.equalsIgnoreCase("Human")) {
                	imgFood = loadSmartImage("food_cow_future");
                }
                else {
                	imgFood = loadSmartImage("food_energy_future");
                }

                if (apexName.equalsIgnoreCase("Leviathan")) {
                	imgApex = loadSmartImage("apex_leviathan_future");
                }
                else if (apexName.equalsIgnoreCase("Alien")) {
                	imgApex = loadSmartImage("apex_alien_future");
                }
                else {
                	imgApex = loadSmartImage("apex_alien_future");
                }

                if (predatorName.equalsIgnoreCase("Cyborg")) {
                	imgPredator = loadSmartImage("predator_cyborg_future");
                }
                else if (predatorName.equalsIgnoreCase("Alien")) {
                	imgPredator = loadSmartImage("predator_alien_future");
                }
                else {
                	imgPredator = loadSmartImage("predator_cyborg_future");
                }

                if (preyName.equalsIgnoreCase("Human")) {
                	imgPrey = loadSmartImage("prey_human_future");
                }
                else if (preyName.equalsIgnoreCase("Robot")) {
                	imgPrey = loadSmartImage("prey_robot_future");
                }
                else {
                	imgPrey = loadSmartImage("prey_robot_future");
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Image loading error.");
        }
    }
    /**
     * . Iterates through multiple directory paths and file extensions (.png, .jpg, .jpeg) to find and load an image file
     * @param baseName
     * @return
     */
    private Image loadSmartImage(String baseName) {
        String[] folderPaths = {"images/backgrounds/","images/obstacles/","images/foods/","images/apex/","images/predators/","images/prey/","images/"};
        String[] extensions = {".png", ".jpg", ".jpeg", ".JPG", ".JPEG"};
        
        for (String folder : folderPaths) {
            for (String ext : extensions) {
                String fullPath = folder + baseName + ext;
                File file = new File(fullPath);
                if (file.exists()) {
                    try {
                        return ImageIO.read(file);
                    } 
                    catch (IOException e) { }
                }
            }
        }
        return null;
    }
    /**
     * Overrides the standard Swing painting method to draw the game state frame-by-frame
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    /**
     * This public method encapsulates the core drawing logic, separating it from the Swing component lifecycle for better code organization.
     * @param g
     */
    public void draw(Graphics g) {
        if (imgBackground != null) {
            g.drawImage(imgBackground, 0, 0, SCREEN_SIZE, SCREEN_SIZE, null);
        }
       
        g.setColor(new Color(255, 255, 255, 40)); 
        for (int i = 0; i < model.getBoardSize(); i++) {
            g.drawLine(i * unitSize, 0, i * unitSize, SCREEN_SIZE);
            g.drawLine(0, i * unitSize, SCREEN_SIZE, i * unitSize);
        }
        
        for (foodchain.model.Obstacle obs : model.getObstacles()) {
            if (imgObstacle != null) {
                g.drawImage(imgObstacle, obs.getX() * unitSize, obs.getY() * unitSize, unitSize, unitSize, null);
            } else {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(obs.getX() * unitSize, obs.getY() * unitSize, unitSize, unitSize);
            }
        }

        Animal player = model.getPlayer();
        if (player instanceof Predator && !isProcessingTurn) { 
            Predator pred = (Predator) player;
            
            ArrayList<int[]> moves = pred.getMoves(model.getBoardSize(), model);
            
            for (int[] move : moves) {
                int mx = move[0];
                int my = move[1];
                boolean isSpecial = (move[2] == 1); 
                
                if (isSpecial) g.setColor(new Color(255, 255, 0, 100)); // Sarı
                else g.setColor(new Color(0, 255, 0, 100)); // Yeşil
                
                g.fillRect(mx * unitSize, my * unitSize, unitSize, unitSize);
                g.setColor(Color.WHITE);
                g.drawRect(mx * unitSize, my * unitSize, unitSize, unitSize);
            }
        }

        for (Food food : model.getFoods()) {
            int fx = food.getX() * unitSize;
            int fy = food.getY() * unitSize;

            g.setColor(new Color(255, 215, 0, 150)); 
            g.fillOval(fx - 5, fy - 5, unitSize + 10, unitSize + 10);
            
            g.setColor(Color.YELLOW);
            g.drawOval(fx - 5, fy - 5, unitSize + 10, unitSize + 10);

            if (imgFood != null) {
                g.drawImage(imgFood, fx, fy, unitSize, unitSize, null);
            } else {
                g.setColor(Color.GREEN);
                g.fillOval(fx, fy, unitSize, unitSize);
            }
        }
        
        for (Animal animal : model.getAnimals()) {
            Image currentImg = null;
            Color backupColor = Color.WHITE;
            Color nameColor = Color.WHITE;

            if (animal.getType().equals(Animal.Type.APEX_PREDATOR)) {
                currentImg = imgApex;
                backupColor = Color.RED;
                nameColor = Color.RED; 
            } 
            else if (animal.getType().equals(Animal.Type.PREDATOR)) {
                currentImg = imgPredator;
                backupColor = Color.ORANGE;
                nameColor = Color.GREEN; 
            } 
            else if (animal.getType().equals(Animal.Type.PREY)) {
                currentImg = imgPrey;
                backupColor = Color.BLUE;
                nameColor = Color.CYAN; 
            }
            
            if (currentImg != null) {
                g.drawImage(currentImg, animal.getX() * unitSize, animal.getY() * unitSize, unitSize, unitSize, null);
            } 
            else {
                g.setColor(backupColor);
                g.fillRect(animal.getX() * unitSize, animal.getY() * unitSize, unitSize, unitSize);
            }
            
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.BLACK); 
            g.drawString(animal.getName(), animal.getX() * unitSize + 3, animal.getY() * unitSize + 13);
            g.setColor(nameColor);   
            g.drawString(animal.getName(), animal.getX() * unitSize + 2, animal.getY() * unitSize + 12);
        }
        
        if (model.getDeathLocations() != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new java.awt.BasicStroke(3)); 
            g2.setColor(Color.RED);
            
            for (int[] p : model.getDeathLocations()) {
                int dx = p[0] * unitSize;
                int dy = p[1] * unitSize;
                
                g2.drawLine(dx + 5, dy + 5, dx + unitSize - 5, dy + unitSize - 5);
                g2.drawLine(dx + unitSize - 5, dy + 5, dx + 5, dy + unitSize - 5);
            }
        }
        
        if (!model.getMoves().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new java.awt.BasicStroke(3, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));

            for (Simulation.MovesRecorder move : model.getMoves()) {
                int sx = move.x * unitSize;
                int sy = move.y * unitSize;
                int ex = move.z * unitSize;
                int ey = move.w * unitSize;
                
                Color trailColor = Color.WHITE;
                Image ghostImg = null;
                
                if (move.type == Animal.Type.PREDATOR) {
                    trailColor = new Color(0, 255, 255, 150); 
                    ghostImg = imgPredator;
                } 
                else if (move.type == Animal.Type.APEX_PREDATOR) {
                    trailColor = new Color(255, 50, 50, 150); 
                    ghostImg = imgApex;
                } 
                else {
                    trailColor = new Color(200, 200, 255, 100); 
                    ghostImg = imgPrey;
                }
                
                if (ghostImg != null) {
                    java.awt.Composite originalComposite = g2.getComposite();
                    g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.4f));
                    g2.drawImage(ghostImg, sx, sy, unitSize, unitSize, null);
                    g2.setComposite(originalComposite); 
                }

                g2.setColor(trailColor);
                g2.drawLine(sx + unitSize/2, sy + unitSize/2, ex + unitSize/2, ey + unitSize/2);
                g2.fillOval(sx + unitSize/2 - 4, sy + unitSize/2 - 4, 8, 8);
            }
        }

        if (model.getSpawnLocations() != null && !model.getSpawnLocations().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new java.awt.BasicStroke(3)); 
            g2.setColor(new Color(0, 255, 255)); 
            
            for (int[] p : model.getSpawnLocations()) {
                int dx = p[0] * unitSize;
                int dy = p[1] * unitSize;
                
                g2.drawOval(dx + 2, dy + 2, unitSize - 4, unitSize - 4);
                
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                g2.setColor(Color.WHITE);
                g2.drawString("SPAWN", dx + 2, dy - 2); 
                g2.setColor(new Color(0, 255, 255)); 
            }
        }
    }
    /**
     * This method solves the problem of UI freezing during complex calculations.
     */
    private void runCinematicTurn() {
        isProcessingTurn = true; 
        
        new Thread(() -> {
            try {
                Thread.sleep(400); 
                model.clearMoves(); 
                refreshCanvas();
                
                model.updatePlayerStatus(); 
                model.checkEatings(); 
                handleVisuals(); 

                model.finishPreyMoves(); 
                model.checkEatings(); 
                handleVisuals(); 
                
                if (!model.getMoves().isEmpty()) {
                    refreshCanvas(); Thread.sleep(400); model.clearMoves(); refreshCanvas();    
                } 
                else {
                    refreshCanvas();
                }
                

                model.apexMoves();
                model.checkEatings();
                handleVisuals(); 

                if (!model.getMoves().isEmpty()) {
                    refreshCanvas(); Thread.sleep(400); model.clearMoves(); refreshCanvas(); 
                } 
                else {
                    refreshCanvas();
                }
                
                Thread.sleep(100);
                model.finalizeRound();
                
                SwingUtilities.invokeLater(() -> {
                    repaint();
                    infoPanel.refresh();
                    checkGameOver();
                    isProcessingTurn = false; 
                });
                
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    /**
     * A helper method used within the animation thread.
     * @throws InterruptedException
     */
    private void handleVisuals() throws InterruptedException {
        boolean hasDeath = !model.getDeathLocations().isEmpty();
        boolean hasSpawn = !model.getSpawnLocations().isEmpty();

        if (hasDeath || hasSpawn) {
            refreshCanvas(); 
            Thread.sleep(300); 
            model.clearDeathLocations();
            model.clearSpawnLocations();
            refreshCanvas(); 
        }
    }
    //Helper method
    private void refreshCanvas() {
        SwingUtilities.invokeLater(this::repaint);
    }
    /**
     * End-Game Logic. Checks if the win/loss conditions are met
     */
    private void checkGameOver() {
        boolean roundOver = model.getCurrentRound() >= model.getTotalRounds();
        boolean playerAlive = false;
        
        Animal player = model.getPlayer();
        if (player != null) {
            for (Animal a : model.getAnimals()) {
                if (a == player) {
                    playerAlive = true;
                    break;
                }
            }
        }

        if (roundOver || !playerAlive) {
            gameOverScreen(playerAlive);
        }
    }
    /**
     * Custom End-Game UI
     * @param playerAlive
     */
    private void gameOverScreen(boolean playerAlive) {
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog(parentFrame, "Game Over", true);
        dialog.setUndecorated(true);
        dialog.setSize(500, 400); 
        dialog.setLayout(new java.awt.BorderLayout());
        
        java.util.ArrayList<Animal> winners = model.getWinningAnimals();
        Image bgToDraw = null;
        
        if (winners.size() == 1) {
            Animal winner = winners.get(0);
            
            String typePrefix = "";
            if (winner.getType() == Animal.Type.APEX_PREDATOR) {
            	typePrefix = "apex";
            }
            else if (winner.getType() == Animal.Type.PREDATOR) {
            	typePrefix = "predator";
            }
            else {
            	typePrefix = "prey";
            }
            
            String safeName = winner.getName().trim().toLowerCase()
                                    .replace(" ", "_")
                                    .replace("-", ""); 
            String eraSuffix = winner.getEra().toString().toLowerCase();
            
            String imgName = typePrefix + "_" + safeName + "_" + eraSuffix;
            bgToDraw = loadSmartImage(imgName);
            
            if (bgToDraw == null && safeName.contains("alien")) {
                bgToDraw = loadSmartImage(typePrefix + "_alien_" + eraSuffix);
            }
        } 
        else if (winners.size() > 1) {
            Animal.Era era = model.getEra();
            if (era == Animal.Era.PAST) {
            	bgToDraw = loadSmartImage("past_bg");
            }
            else if (era == Animal.Era.FUTURE) {
            	bgToDraw = loadSmartImage("future_bg");
            }
            else {
            	bgToDraw = loadSmartImage("present_bg");
            }
        }

        final Image finalBg = bgToDraw;

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (finalBg != null) {
                    g.drawImage(finalBg, 0, 0, getWidth(), getHeight(), null);
                    g.setColor(new Color(0, 0, 0, 120)); 
                    g.fillRect(0, 0, getWidth(), getHeight());
                } 
                else {
                    g.setColor(new Color(44, 62, 80));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        
        JLabel lblTitle = new JLabel();
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); 
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        
        if (winners.size() > 1) {
            lblTitle.setText(" DRAW! ️"); 
            lblTitle.setForeground(new Color(255, 165, 0)); 
        } 
        else if (playerAlive) {
            lblTitle.setText(" GAME OVER! ");
            lblTitle.setForeground(new Color(46, 204, 113)); 
        } 
        else {
            lblTitle.setText(" YOU DIED! ");
            lblTitle.setForeground(new Color(231, 76, 60)); 
        }
        
        JLabel lblWinner = new JLabel("WINNER: " + model.getWinnerInfo()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textX = (getWidth() - textWidth) / 2;
                
                g2.setColor(Color.BLACK);
                g2.drawString(getText(), textX + 2, getHeight() - 4);
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), textX, getHeight() - 6);
            }
        };
        lblWinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWinner.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        lblWinner.setMaximumSize(new Dimension(500, 60)); 
        
        String scoreText = (model.getPlayer() != null) ? "Your Score: " + model.getPlayer().getScore() : "Score: 0";
        JLabel lblScore = new JLabel(scoreText) {
             @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textX = (getWidth() - textWidth) / 2;
                
                g2.setColor(Color.BLACK);
                g2.drawString(getText(), textX + 2, getHeight() - 4);
                g2.setColor(new Color(241, 196, 15)); 
                g2.drawString(getText(), textX, getHeight() - 6);
            }
        };
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblScore.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblScore.setMaximumSize(new Dimension(500, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        
        JButton btnRestart = button("🔄 Play Again", new Color(52, 152, 219));
        btnRestart.addActionListener(e -> {
            dialog.dispose();
            model.resetGame(); 
            try { 
                java.lang.reflect.Method m = this.getClass().getDeclaredMethod("loadImages");
                m.setAccessible(true);
                m.invoke(this);
            } 
            catch (Exception ex) {} 
            
            repaint();
            infoPanel.refresh();
        });

        JButton btnExit = button("❌ Exit", new Color(192, 57, 43));
        btnExit.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnRestart);
        buttonPanel.add(Box.createHorizontalStrut(20)); 
        buttonPanel.add(btnExit);

        contentPanel.add(Box.createVerticalStrut(40)); 
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(lblWinner);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(lblScore);
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(20)); 

        dialog.add(contentPanel);
        dialog.setLocationRelativeTo(parentFrame); 
        dialog.setVisible(true);
    }
    /**
     * UI Factory Helper
     * @param text
     * @param bgColor
     * @return
     */
    private JButton button(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }
    /**
     * Captures mouse clicks on the panel
     */
    public class MyMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent mouse) {
            if (isProcessingTurn) {
            	return;
            }
            if (model.getCurrentRound() >= model.getTotalRounds()) {
            	return;
            }
            
            int clickedX = mouse.getX() / unitSize;
            int clickedY = mouse.getY() / unitSize;
            
            Animal animalPlayer = model.getPlayer();
            
            if (animalPlayer instanceof Predator) {
                Predator player = (Predator) animalPlayer;
                
                if (clickedX == player.getX() && clickedY == player.getY()) {
                    runCinematicTurn();
                    return;
                }

                ArrayList<int[]> possibleMoves = player.getMoves(model.getBoardSize(), model);
                
                boolean isValidClick = false;
                boolean isSpecialMove = false;

                for (int[] move : possibleMoves) {
                    if (move[0] == clickedX && move[1] == clickedY) {
                        isValidClick = true;
                        isSpecialMove = (move[2] == 1); 
                        break;
                    }
                }

                if (isValidClick) {
                    int startX = player.getX();
                    int startY = player.getY();
                    
                    player.setPosition(clickedX, clickedY);
                    
                    String playerName = "Player (" + player.getName() + ")";
                    Log.logMove(playerName, startX, startY, clickedX, clickedY);
                    
                    if (isSpecialMove) {
                        model.addMoveRecord(player.getType(), startX, startY, clickedX, clickedY);
                        
                        int cd = 3; 
                        if (player.getEra() == Animal.Era.PRESENT) {
                        	cd = 0;
                        }
                        player.setCoolDown(cd);
                    }

                    repaint();
                    runCinematicTurn();
                }
            }
        }
    }
    /**
     * . It maps specific keys (e.g., 'S') to high-level system functions like saving the game state to a text file.
     */
    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent key) {
            if (isProcessingTurn) {
            	return; 
            }
            
            int code = key.getKeyCode();
            
            if (code == KeyEvent.VK_S) {
                model.saveGameToText("saved_game.txt"); 
                JOptionPane.showMessageDialog(null, "Game Saved successfully to saved_game.txt!");
            }
            
            if (code == KeyEvent.VK_L) {
                JOptionPane.showMessageDialog(null, "Please use 'LOAD GAME' button in the Main Menu.");
            }
        }
    }
}


