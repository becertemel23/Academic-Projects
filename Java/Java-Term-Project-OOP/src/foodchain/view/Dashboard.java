package foodchain.view;

import javax.swing.JPanel;
import foodchain.model.Animal;
import foodchain.model.Simulation;
import java.awt.*;

public class Dashboard extends JPanel {

    private Simulation model;
    private final Color COLOR_BG = new Color(220, 220, 220); 
    private final Color COLOR_INFO_BOX = new Color(240, 240, 240); 
    private final Color COLOR_APEX = new Color(217, 136, 128);   
    private final Color COLOR_PREDATOR = new Color(230, 176, 170); 
    private final Color COLOR_PREY = new Color(169, 204, 227);    
    
    /**
     * Constructor
     * @param model
     */
    public Dashboard(Simulation model) {
        this.model = model;
        setBackground(COLOR_BG);
        setPreferredSize(new Dimension(250, 600)); 
    }
    
    /**
     * Overrides the standard Swing painting method.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (model.getPlayer() == null) {
        	return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int x = 15;
        int y = 20;
        int width = 220;
        
        drawCard(g2d, x, y, width, 75, COLOR_INFO_BOX, "Game Information");
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Round: " + (model.getCurrentRound() + 1) + " / " + model.getTotalRounds(), x + 10, y + 48);
        g2d.drawString("Era: " + model.getPlayer().getEra(), x + 10, y + 68);
        
        y += 95;
        
        Animal apex = null;
        Animal predator = null;
        Animal prey = null;
        
        for(Animal a : model.getAnimals()) {
            if(a.getType() == Animal.Type.APEX_PREDATOR) {
            	apex = a;
            }
            else if(a.getType() == Animal.Type.PREDATOR) {
            	predator = a;
            }
            else if(a.getType() == Animal.Type.PREY) {
            	prey = a;
            }
        }

        if (apex != null) {
            drawRoleCard(g2d, x, y, width, apex, COLOR_APEX, "Apex Predator");
            y += 110;
        }

        if (predator != null) {
            String title = "Predator";
            if (model.getPlayer() == predator) {
            	title += " [YOU]"; 
            }
            
            drawRoleCard(g2d, x, y, width, predator, COLOR_PREDATOR, title);
            y += 110;
        }

        if (prey != null) {
            drawRoleCard(g2d, x, y, width, prey, COLOR_PREY, "Prey");
        }
    }
    /**
     * Draws a customized information block for a specific animal. 
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param bg
     * @param title
     */
    private void drawCard(Graphics2D g, int x, int y, int w, int h, Color bg, String title) {
        g.setColor(new Color(0,0,0,30));
        g.fillRect(x+3, y+3, w, h);
        g.setColor(bg);
        g.fillRect(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(title, x + 10, y + 20);
        g.drawLine(x, y + 25, x + w, y + 25); 
    }
    /**
     * This is the main implementation that handles the detailed visualization of an animal's status.
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param bg
     * @param title
     * @param animal
     */
    private void drawRoleCard(Graphics2D g, int x, int y, int w, int h, Color bg, String title, Animal animal) {
        int height = 90;
        g.setColor(new Color(0,0,0,30));
        g.fillRect(x+3, y+3, w, height);
        g.setColor(bg);
        g.fillRect(x, y, w, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, height);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(title, x + 10, y + 20);
        g.drawLine(x, y + 25, x + w, y + 25); 
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int textY = y + 45;
        
        g.drawString("Name: " + animal.getName(), x + 10, textY);
        textY += 20;
        g.drawString("Score: " + animal.getScore(), x + 10, textY);
        textY += 20;
        if (animal.getEra() == Animal.Era.PRESENT && animal.getType() == Animal.Type.PREDATOR) {
            boolean danger = model.isApexClose(animal);
            
            if (danger) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 12)); 
                g.drawString("Ability: ACTIVE! (RUN!)", x + 10, textY);
            } 
            else {
                g.setColor(Color.GRAY);
                g.drawString("Ability: Passive (Safe)", x + 10, textY);
            }
        } 
        else {
            if (animal.getCooldown() > 0) {
                g.setColor(Color.RED); 
                g.drawString("Ability Cooldown: " + animal.getCooldown(), x + 10, textY);
            } 
            else {
                g.setColor(new Color(0, 100, 0)); 
                g.drawString("Ability: Ready", x + 10, textY);
            }
        }
        g.setColor(Color.BLACK);
    }
    /**
     * A helper method responsible for drawing the static "Game Information" box.
     * @param g
     * @param x
     * @param y
     * @param w
     * @param animal
     * @param bg
     * @param title
     */
    private void drawRoleCard(Graphics2D g, int x, int y, int w, Animal animal, Color bg, String title) {
        drawRoleCard(g, x, y, w, 0, bg, title, animal);
    }
    /**
     * A public method called by the Panel class at the end of every cinematic turn.
     */
    public void refresh() {
        repaint();
    }
}