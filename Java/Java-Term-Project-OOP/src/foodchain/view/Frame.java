package foodchain.view;

import javax.swing.*;
import foodchain.model.Simulation;
import java.awt.BorderLayout;

public class Frame extends JFrame {
    
    private Simulation model;
    private Panel panel;
    
    /**
     * Constructor. Responsible for assembling the main application window. 
     * @param model
     */
    public Frame(Simulation model) {
        this.model = model;
        
        String eraTitle = "";
        if (model.getPlayer() != null) {
            eraTitle = model.getPlayer().getEra().toString();
        }
        
        setTitle("Food Chain Game - " + eraTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        Dashboard infoPanel = new Dashboard(model);
        add(infoPanel, BorderLayout.EAST); 
        
        this.panel = new Panel(model, infoPanel);
        add(panel, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newItem = new JMenuItem("New Game");
        newItem.addActionListener(e -> {
            this.dispose(); 
            new StartScreen(); 
        });
        
        JMenuItem saveItem = new JMenuItem("Save Game");
        saveItem.addActionListener(e -> {
            String path = JOptionPane.showInputDialog("Enter save file name (e.g. save.txt):");
            if(path != null) {
                model.saveGameToText(path);
                JOptionPane.showMessageDialog(this, "Game Saved successfully!");
            }
        });
        
        JMenuItem loadItem = new JMenuItem("Load Game");
        loadItem.addActionListener(e -> {
            String path = JOptionPane.showInputDialog("Enter file name to load (e.g. save.txt):");
            if(path != null) {
                Simulation loadedModel = Simulation.loadGameFromText(path);
                
                if(loadedModel != null) {
                    this.dispose();
                    new Frame(loadedModel).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Load failed! Check file name.");
                }
            }
        });

        gameMenu.add(newItem);
        gameMenu.addSeparator();
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        menuBar.add(gameMenu);
        
        setJMenuBar(menuBar);
        
        pack(); 
        setLocationRelativeTo(null);
    }
}
