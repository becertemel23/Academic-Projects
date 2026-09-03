package foodchain.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;
import javax.swing.JOptionPane;
import foodchain.model.Animal;
import foodchain.model.ApexPredator;
import foodchain.model.Food;
import foodchain.model.Simulation;
import foodchain.model.Predator;
import foodchain.model.Prey;

public class Reader {
	
	/**
	 * This method orchestrates the entire setup process for a new game session.
	 * @param model
	 * @param filePath
	 * @param era
	 */
    public static void loadFile(Simulation model, String filePath, Animal.Era era) {
        List<String> foodChains = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Food Chain")) {
                    foodChains.add(line);
                }
            }
        } 
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error reading file: " + filePath + "\nCheck if file exists!");
            return;
        }

        if (foodChains.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No valid Food Chains found in " + filePath);
            return;
        }

        SecureRandom luck = new SecureRandom();
        String selectedChain = foodChains.get(luck.nextInt(foodChains.size()));
        
        System.out.println("Selected Chain: " + selectedChain);
        Log.log("Map Loaded: " + filePath + " | Selected: " + selectedChain);

        try {
            String content = selectedChain.split(":")[1].trim();
            String[] names = content.split(",");

            String apexName = names[0].trim();
            String predatorName = names[1].trim();
            String preyName = names[2].trim();
            String foodName = names[3].trim();

            model.getAnimals().clear();
            model.setFood(null);

            model.addAnimal(new ApexPredator(apexName, 0, 0, era));
            model.addAnimal(new Predator(predatorName, 0, 0, era));
            model.addAnimal(new Prey(preyName, 0, 0, era));
            model.setFood(new Food(foodName, 0, 0));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Malformed Data in file! \nChain: " + selectedChain);
        }
    }
}
