package foodchain.model;

import java.io.Serializable;
import java.util.ArrayList;

import foodchain.util.Log;

import java.security.SecureRandom; 

public class Simulation implements Serializable {

    private int boardSize;
    private int totalRounds;
    private int currentRound;
    private Animal.Era currentEra;
    private ArrayList<Food> foods;
    private ArrayList<Animal> animals;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<int[]> deathLocations; 
    private ArrayList<int[]> spawnLocations = new ArrayList<>();
    private ArrayList<MovesRecorder> recentMoves = new ArrayList<>();
    
    /**
     * Constructor
     * @param boardSize
     * @param totalRounds
     * @param era
     */
    public Simulation(int boardSize, int totalRounds, Animal.Era era) {
        this.boardSize = boardSize;
        this.totalRounds = totalRounds;
        this.currentEra = era;
        this.animals = new ArrayList<>();
        this.foods = new ArrayList<>(); 
        this.obstacles = new ArrayList<>();
        this.deathLocations = new ArrayList<>(); 
        
        initializeGame();
    }

    /**
     * It acts as a data structure to record the starting and ending coordinates of an animal's movement
     */
    public static class MovesRecorder implements Serializable {
        public Animal.Type type;
        public int x;
        public int y;
        public int z;
        public int w;
        public MovesRecorder(Animal.Type t, int x, int y, int z, int w) {
            this.type = t; this.x = x; this.y = y; this.z = z; this.w = w;
        }
    }
    /**
     * Resets the simulation to its starting state
     */
    private void initializeGame() {
        this.currentRound = 0;
        this.animals.clear();
        this.foods.clear(); 
        this.obstacles.clear();
        this.deathLocations.clear();
        
        foodchain.util.Log.logSetup(
            (currentEra != null ? currentEra.toString() : "PRESENT"), 
            totalRounds, 
            boardSize);
        foodchain.util.Log.logStart(1); 
        
        generateObstacles();
        
        if (this.currentEra == Animal.Era.PAST) {
            addAnimal(new ApexPredator("T-Rex", 0, 0, currentEra));
            addAnimal(new Predator("Velociraptor", 0, 0, currentEra));
            addAnimal(new Prey("Triceratops", 0, 0, currentEra)); 
        } 
        else if (this.currentEra == Animal.Era.FUTURE) {
            addAnimal(new ApexPredator("Alien", 0, 0, currentEra));
            addAnimal(new Predator("Cyborg", 0, 0, currentEra));
            addAnimal(new Prey("Robot", 0, 0, currentEra)); 
        } 
        else { 
            addAnimal(new ApexPredator("Lion", 0, 0, Animal.Era.PRESENT));
            addAnimal(new Predator("Cheetah", 0, 0, Animal.Era.PRESENT));
            addAnimal(new Prey("Bunny", 0, 0, Animal.Era.PRESENT));
        }
        
        initializePositions();
        
        Food initialFood = new Food("Food", 0, 0);
        foods.add(initialFood);
        respawn(initialFood);
        
        if (spawnLocations != null) {
            spawnLocations.clear();
        }
    }
    /**
     * Orchestrates the AI turn for all Prey entities.
     */
    public void finishPreyMoves() {
        Animal player = getPlayer();
        for (Animal animal : animals) {
            if (animal instanceof Prey) {
            	((Prey) animal).resetEatingStatus();
            }
        }
        for(Animal animal : animals) {
            if (animal.getType() == Animal.Type.PREY) {
                int sx = animal.getX();
                int sy = animal.getY();
                
                animal.reduceCooldown();
                
                Food targetFood = getClosestFood(animal);
                animal.move(player, targetFood, this); 
                
                if (sx != animal.getX() || sy != animal.getY()) {
                    Log.logMove(animal.getName(), sx, sy, animal.getX(), animal.getY());
                }
                
                if (Math.abs(sx - animal.getX()) > 1 || Math.abs(sy - animal.getY()) > 1) {
                    addMoveRecord(animal.getType(), sx, sy, animal.getX(), animal.getY());
                }
            }
        }
    }
    
    /**
     * Orchestrates the AI turn for Apex Predators.
     */
    public void apexMoves() {
        Animal player = getPlayer();
        for (Animal animal : animals) {
            if (animal.getType() == Animal.Type.APEX_PREDATOR) {
                int sx = animal.getX();
                int sy = animal.getY();
                
                animal.reduceCooldown();
                
                Animal target = player; 
                Animal closestPrey = getClosestPrey(animal);
                if (player == null && closestPrey != null) {
                	target = closestPrey;
                }
                else if (player != null && closestPrey != null) {
                    int distToPlayer = Math.abs(animal.getX() - player.getX()) + Math.abs(animal.getY() - player.getY());
                    int distToPrey = Math.abs(animal.getX() - closestPrey.getX()) + Math.abs(animal.getY() - closestPrey.getY());
                    if (distToPrey < distToPlayer) {
                    	target = closestPrey;
                    }
                }
                
                Food targetFood = getClosestFood(animal);
                animal.move(target, targetFood, this);
                
                if (sx != animal.getX() || sy != animal.getY()) {
                    Log.logMove(animal.getName(), sx, sy, animal.getX(), animal.getY());
                }
                
                if (Math.abs(sx - animal.getX()) > 1 || Math.abs(sy - animal.getY()) > 1) {
                    addMoveRecord(animal.getType(), sx, sy, animal.getX(), animal.getY());
                }
            }
        }
    }
    /**
     * Manages the temporal progression of the game.
     */
    public void finalizeRound() {
        if (currentRound >= totalRounds) {
        	return;
        }
        int actualRound = currentRound + 1;
        
        Log.logEnd(actualRound);
        
        currentRound++;
        
        if (currentRound >= totalRounds) {
            calculateWinner();
        } 
        else {
            Log.logStart(currentRound + 1);
        }
    }
    /**
     * Iterates through the animals list to find the highest score
     * @return
     */
    public String getWinnerInfo() {
        if (animals.isEmpty()) {
        	return "No Animals!";
        }
        int maxScore = Integer.MIN_VALUE;
        for(Animal animal : animals) {
            if (animal.getScore() > maxScore) maxScore = animal.getScore();
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Animal animal : animals) {
            if (animal.getScore() == maxScore) {
                if (!first) {
                	sb.append(", ");
                }
                sb.append(animal.getName());
                first = false;
            }
        }
        return sb.toString() + " (" + maxScore + " Pts)";
    }
    /**
     * The core Collision Detection Engine.
     */
    public void checkEatings() {
        ArrayList<Animal> cAnimals = new ArrayList<>(animals);

        for (Animal a1 : cAnimals) {
            for (Animal a2 : cAnimals) {
                if (a1 != a2 && a1.getX() == a2.getX() && a1.getY() == a2.getY()) {
                    if (a1.getType() == Animal.Type.APEX_PREDATOR) {
                    	eatLogic(a1, a2, 1); 
                    }
                    else if (a2.getType() == Animal.Type.APEX_PREDATOR) {
                    	eatLogic(a2, a1, 1);
                    }
                    else if (a1.getType() == Animal.Type.PREDATOR && a2.getType() == Animal.Type.PREY) {
                    	eatLogic(a1, a2, 3); 
                    }
                    else if (a2.getType() == Animal.Type.PREDATOR && a1.getType() == Animal.Type.PREY) {
                    	eatLogic(a2, a1, 3);
                    }
                }
            }
        }
        
        if (!foods.isEmpty()) {
            for (Animal animal : animals) {
                if (animal instanceof Prey) {
                    Prey p = (Prey) animal;
                    if (p.canEat()) {

                        for (Food f : foods) {
                            if (animal.getX() == f.getX() && animal.getY() == f.getY()) {
                                animal.addScore(3); 
                                
                                Log.logScore(animal.getName(), 3, "Ate Food");
                                
                                deathLocations.add(new int[]{f.getX(), f.getY()});
                                respawn(f); 
                                break; 
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Handles the transactional logic of predation.
     * @param hunter
     * @param victim
     * @param points
     */
    private void eatLogic(Animal hunter, Animal victim, int points) {
        if (hunter.getX() != victim.getX() || hunter.getY() != victim.getY()) {
        	return;
        }
        
        Log.logScore(hunter.getName(), points, "Ate " + victim.getName());
        Log.logScore(victim.getName(), -1, "Eaten by " + hunter.getName());
        
        deathLocations.add(new int[]{victim.getX(), victim.getY()});
        
        hunter.addScore(points);
        victim.loseScore(1);
        respawn(victim); 
    }
    /**
     * Returns true if the specified coordinate contains any entity
     * @param x
     * @param y
     * @return
     */
    public boolean isCellFull(int x, int y) {
        if (getAnimalAt(x, y) != null) {
        	return true;
        }
        for (Food f : foods) {
            if (f.getX() == x && f.getY() == y) return true;
        }
        if (hasObstacle(x, y)) {
        	return true;
        }
        return false;
    }
    /**
     * Forces a respawn() for every entity in the game to ensure a fresh, randomized distribution.
     */
    public void initializePositions() {
        for (Animal animal : animals) {
        	respawn(animal);
        }
        for (Food f : foods) {
        	respawn(f);
        }
        
        if (spawnLocations != null) {
            spawnLocations.clear();
        }
    }
    /**
     * ): Implements a Safe Spawning Algorithm.
     * @param type
     */
    public void respawn(Object type) {
        SecureRandom luck = new SecureRandom(); 
        int randomX;
        int randomY;
        do {
            randomX = luck.nextInt(boardSize);
            randomY = luck.nextInt(boardSize);
        } while(isCellFull(randomX, randomY));
        
        if (type instanceof Animal) {
            ((Animal) type).setPosition(randomX, randomY);
            spawnLocations.add(new int[]{randomX, randomY});
        }
        if (type instanceof Food) {
            ((Food) type).setPosition(randomX, randomY);
            spawnLocations.add(new int[]{randomX, randomY});
        }
    }
    /**
     * Returns true if an Apex Predator is within a 1-tile radius.
     * @param currentAnimal
     * @return
     */
    public boolean isApexClose(Animal currentAnimal) {
        for (Animal other : animals) {
            if (other.getType() == Animal.Type.APEX_PREDATOR) {
                int differenceX = Math.abs(currentAnimal.getX() - other.getX());
                int differenceY = Math.abs(currentAnimal.getY() - other.getY());
                
                if (differenceX <= 1 && differenceY <= 1) {
                	return true; 
                }
            }
        }
        return false; 
    }
    /**
     * Randomly populates the board with obstacles
     */
    private void generateObstacles() {
        obstacles.clear();
        int obstacleCounter = (boardSize * boardSize) / 20; 
        SecureRandom luck = new SecureRandom(); 
        
        for (int i = 0; i < obstacleCounter; i++) {
            int x;
            int y;
            do {
                x = luck.nextInt(boardSize);
                y = luck.nextInt(boardSize);
            } while (isCellFull(x, y));
            obstacles.add(new Obstacle(x, y));
        }
    }
    /**
     * Iterates through the obstacles list to determine if a specific coordinate is blocked.
     * @param x
     * @param y
     * @return
     */
    public boolean hasObstacle(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y) {
            	return true;
            }
        }
        return false;
    }
    /**
     * Writes the current game state to a structured text file using BufferedWriter.
     * @param filename
     */
    public void saveGameToText(String filename) {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(filename))) {
            String eraStr = (this.currentEra != null) ? this.currentEra.toString() : "PRESENT";
            writer.write("BIGDATA;" + eraStr + ";" + currentRound + ";" + totalRounds + ";" + boardSize);
            writer.newLine();
            for (Animal a : animals) {
                String line = a.getType() + ";" + a.getName() + ";" + a.getX() + ";" + a.getY() + ";" + a.getScore() + ";" + a.getCooldown();
                writer.write(line);
                writer.newLine();
            }
            for (Food f : foods) {
                writer.write("FOOD;" + f.getName() + ";" + f.getX() + ";" + f.getY());
                writer.newLine();
            }
            for (Obstacle obs : obstacles) {
                writer.write("OBSTACLE;" + obs.getX() + ";" + obs.getY());
                writer.newLine();
            }
            System.out.println("Game saved to: " + filename);
        } catch (java.io.IOException e) {
        	e.printStackTrace(); 
        	}
    }
    /**
     * Reads the text file using BufferedReader.
     * @param filename
     * @return
     */
    public static Simulation loadGameFromText(String filename) {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line = reader.readLine();
            if (line == null || !line.startsWith("METADATA") && !line.startsWith("BIGDATA")) {
            	return null;
            }
            String[] metaParts = line.split(";");
            Animal.Era era = Animal.Era.valueOf(metaParts[1]);
            
            int currentRound = Integer.parseInt(metaParts[2]);
            int totalRounds = Integer.parseInt(metaParts[3]);
            int boardSize = Integer.parseInt(metaParts[4]);

            Simulation newModel = new Simulation(boardSize, totalRounds, era);
            newModel.currentRound = currentRound;
            newModel.getAnimals().clear(); 
            newModel.getFoods().clear(); 
            newModel.getObstacles().clear();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String type = parts[0];
                
                if (type.equals("FOOD")) {
                    Food f = new Food(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    newModel.addFood(f);
                } 
                else if (type.equals("OBSTACLE")) {
                    newModel.getObstacles().add(new Obstacle(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                } 
                else {
                    String realName = parts[1]; 
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    int score = Integer.parseInt(parts[4]);
                    int cd = Integer.parseInt(parts[5]);

                    Animal a = null;
                    if (type.equals("PREDATOR")) {
                    	a = new Predator(realName, x, y, era);
                    }
                    else if (type.equals("PREY")) {
                    	a = new Prey(realName, x, y, era);
                    }
                    else if (type.equals("APEX_PREDATOR")) {
                    	a = new ApexPredator(realName, x, y, era);
                    }
                    
                    if (a != null) {
                        a.addScore(score); 
                        a.setCoolDown(cd);
                        newModel.addAnimal(a);
                    }
                }
            }
            return newModel;
        } catch (Exception e) {
        	e.printStackTrace(); return null; 
        	}
    }
    
    //Helper Methods
    public void clearMoves() {
    	recentMoves.clear(); 
    }
    
    public void addMoveRecord(Animal.Type type, int sx, int sy, int ex, int ey) {
        recentMoves.add(new MovesRecorder(type, sx, sy, ex, ey));
    }
    
    public void clearSpawnLocations() {
    	spawnLocations.clear(); 
    }
    
    public void resetGame() {
        initializeGame();
    }
    
    public void clearDeathLocations() {
        deathLocations.clear();
    }
    
    public void updatePlayerStatus() {
        Animal player = getPlayer();
        if (player != null) player.reduceCooldown();
    }
    
    private void calculateWinner() {
        Log.logWinner(getWinnerInfo());
        Log.logGameOver();
    }
    
    public void addAnimal(Animal animal) {
    	this.animals.add(animal); 
    }
    
    public void addFood(Food f) {
        this.foods.add(f);
    }
    
    //Getters
    public ArrayList<Animal> getAnimals(){
    	return this.animals; 
    }
    
    public ArrayList<Food> getFoods() {
    	return this.foods; 
    }
    
    public int getCurrentRound() {
    	return this.currentRound; 
    }
    
    public int getTotalRounds() {
    	return this.totalRounds; 
    }
    
    public int getBoardSize() {
    	return this.boardSize; 
    }
    
    public Animal.Era getEra() {
    	return this.currentEra; 
    }
    
    public ArrayList<int[]> getDeathLocations() {
        return deathLocations;
    }
    
    public ArrayList<MovesRecorder> getMoves() {
    	return recentMoves; 
    }
    
    public ArrayList<int[]> getSpawnLocations() {
    	return spawnLocations; 
    }
    public ArrayList<Obstacle> getObstacles() {
    	return obstacles; 
    }
    
    private Food getClosestFood(Animal a) {
        if (foods.isEmpty()) return null;
        Food closest = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Food f : foods) {
            int dist = Math.abs(a.getX() - f.getX()) + Math.abs(a.getY() - f.getY());
            if (dist < minDistance) {
                minDistance = dist;
                closest = f;
            }
        }
        return closest;
    }

    private Animal getClosestPrey(Animal apex) {
        Animal closest = null;
        int minDist = Integer.MAX_VALUE;
        for (Animal a : animals) {
            if (a.getType() == Animal.Type.PREY) {
                int dist = Math.abs(apex.getX() - a.getX()) + Math.abs(apex.getY() - a.getY());
                if (dist < minDist) {
                    minDist = dist;
                    closest = a;
                }
            }
        }
        return closest;
    }
    
    public ArrayList<Animal> getWinningAnimals() {
        if (animals.isEmpty()) {
        	return new ArrayList<>();
        }

        ArrayList<Animal> winners = new ArrayList<>();
        
        int maxScore = Integer.MIN_VALUE;
        for (Animal a : animals) {
            if (a.getScore() > maxScore) {
            	maxScore = a.getScore();
            }
        }

        for (Animal a : animals) {
            if (a.getScore() == maxScore) {
                winners.add(a);
            }
        }
        return winners;
    }
    
    public Animal getWinnerAnimal() {
        if (animals.isEmpty()) {
        	return null;
        }

        Animal winner = animals.get(0);
        int maxScore = winner.getScore();

        for (Animal a : animals) {
            if (a.getScore() > maxScore) {
                maxScore = a.getScore();
                winner = a;
            }
        }
        return winner;
    }
    
    public Animal getPlayer() {
        for (Animal animal : animals) {
            if (animal.getType() == Animal.Type.PREDATOR) {
            	return animal;
            }
        }
        return null;
    }
    
    public Animal getAnimalAt(int x, int y) {
        for (Animal animal : animals) {
            if (animal.getX() == x && animal.getY() == y) return animal;
        }
        return null;
    }
    
    public void setFood(Food food) {
    	this.foods.clear();
        this.foods.add(food);
    }
}
