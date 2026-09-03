package foodchain.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO; // Resim okumak için gerekli

import foodchain.model.Animal;
import foodchain.model.Simulation;
import foodchain.util.Reader;

public class StartScreen extends JFrame {


    private final Color COLOR_BG_START = new Color(44, 62, 80);
    private final Color COLOR_BG_END = new Color(0, 0, 0);
    private final Color COLOR_CARD_BG = new Color(0, 0, 0, 150); 
    private final Color COLOR_TEXT = new Color(236, 240, 241);
    private final Color COLOR_BTN_START = new Color(39, 174, 96);
    private final Color COLOR_BTN_LOAD = new Color(230, 126, 34);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 32);
    private final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 14); 
    private final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 14);
    private JComboBox<String> sizeBox;
    private JSpinner roundSpinner;
    private JComboBox<Animal.Era> eraBox;
    private Image backgroundImage;
    
    /**
     * Constructor. Initializes the application's entry window
     */
    public StartScreen() {
        setTitle("Food Chain Game - Setup");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("images/backgrounds/background.png")); 
        } 
        catch (IOException e) {
            System.err.println("Background picture not found! (images/backgrounds/background.png)");
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                } 
                else {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    GradientPaint gp = new GradientPaint(0, 0, COLOR_BG_START, 0, getHeight(), COLOR_BG_END);
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout()); 
        setContentPane(mainPanel);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(COLOR_CARD_BG);
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); 

        JLabel titleLabel = new JLabel("FOOD CHAIN");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Survival of the Fittest");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = new JPanel(new GridLayout(6, 1, 5, 10));
        inputPanel.setOpaque(false); 

        inputPanel.add(createLabel("Select Grid Size:"));
        String[] sizeOptions = {"10x10", "15x15", "20x20"};
        sizeBox = new JComboBox<>(sizeOptions);
        sizeBox.setSelectedIndex(1);
        styleComboBox(sizeBox);
        inputPanel.add(sizeBox);

        inputPanel.add(createLabel("Total Rounds:"));
        roundSpinner = new JSpinner(new SpinnerNumberModel(20, 10, 100, 5));
        styleSpinner(roundSpinner);
        inputPanel.add(roundSpinner);

        inputPanel.add(createLabel("Select Era (Mode):"));
        eraBox = new JComboBox<>(Animal.Era.values());
        styleComboBox(eraBox);
        inputPanel.add(eraBox);

        ModernButton btnStart = new ModernButton("START NEW GAME", COLOR_BTN_START);
        ModernButton btnLoad = new ModernButton("LOAD SAVED GAME", COLOR_BTN_LOAD);

        btnStart.addActionListener(e -> startGame());
        btnLoad.addActionListener(e -> loadGame());

        cardPanel.add(titleLabel);
        cardPanel.add(subtitleLabel);
        cardPanel.add(Box.createVerticalStrut(30)); 
        cardPanel.add(inputPanel);
        cardPanel.add(Box.createVerticalStrut(30)); 
        cardPanel.add(btnStart);
        cardPanel.add(Box.createVerticalStrut(15)); 
        cardPanel.add(btnLoad);

        mainPanel.add(cardPanel);
        setVisible(true);
    }

    /**
     * Generates JLabel objects with pre-defined font (Segoe UI) and color properties, ensuring typography consistency across the menu.
     * @param text
     * @return
     */
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(COLOR_TEXT);
        return lbl;
    }
    /**
     * Component Styling. 
     * @param box
     */
    private void styleComboBox(JComboBox<?> box) {
        box.setFont(FONT_BTN);
        box.setBackground(Color.WHITE);
        ((JComponent) box.getRenderer()).setOpaque(true);
    }
    /**
     * This private helper method ensures visual consistency for the numerical input component. 
     * @param spinner
     */
    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(FONT_BTN);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setBackground(Color.WHITE);
            ((JSpinner.DefaultEditor) editor).getTextField().setForeground(Color.BLACK);
        }
    }
    /**
     * Triggered when the "Start Simulation" button is clicked.
     */
    private void startGame() {
        try {
            String selectedSizeStr = (String) sizeBox.getSelectedItem();
            int size = Integer.parseInt(selectedSizeStr.split("x")[0]);
            int rounds = (int) roundSpinner.getValue();
            
            if (rounds < 10) {
                JOptionPane.showMessageDialog(this, "There are too few rounds! There should be at least 10 rounds.", "Error", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            Animal.Era selectedEra = (Animal.Era) eraBox.getSelectedItem();

            Simulation model = new Simulation(size, rounds, selectedEra);
            
            String basePath = "src/foodchain/"; 
            String filePath = "";
            if (selectedEra == Animal.Era.PAST) {
            	filePath = basePath + "past_animals.txt";
            }
            else if (selectedEra == Animal.Era.PRESENT) {
            	filePath = basePath + "present_animals.txt";
            }
            else {
            	filePath = basePath + "future_animals.txt";
            }

            Reader.loadFile(model, filePath, selectedEra);
            model.initializePositions();

            this.dispose();
            new Frame(model).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    /**
     * Triggered by the "Load Saved Game" button.
     */
    private void loadGame() {
        try {
            Simulation loadedModel = Simulation.loadGameFromText("saved_game.txt");
            if (loadedModel != null) {
                this.dispose();
                new Frame(loadedModel).setVisible(true);
            } 
            else {
                JOptionPane.showMessageDialog(this, "Save file (saved_game.txt) not found!");
            }
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage());
        }
    }
    /**
     * Extends JButton to replace the standard OS-style buttons with rounded, flat-design buttons.
     */
    private class ModernButton extends JButton {
        private Color baseColor;
        private boolean whenApproached = false;

        public ModernButton(String text, Color color) {
            super(text);
            this.baseColor = color;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(FONT_BTN);
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setAlignmentX(Component.CENTER_ALIGNMENT);
            
            setMaximumSize(new Dimension(280, 45)); 

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    whenApproached = true;
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    whenApproached = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (whenApproached) {
                g2d.setColor(baseColor.brighter());
            } 
            else {
                g2d.setColor(baseColor);
            }

            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 3;
            g2d.setColor(Color.WHITE);
            g2d.drawString(getText(), x, y);
        }
    }

    
}
