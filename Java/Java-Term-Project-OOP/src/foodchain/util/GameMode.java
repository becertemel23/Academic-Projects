package foodchain.util;

import foodchain.model.Animal;

public enum GameMode {
    PAST("Past (Dinosaur Era)", "src/foodchain/past_animals.txt", Animal.Era.PAST),
    PRESENT("Present (Modern Era)", "src/foodchain/present_animals.txt", Animal.Era.PRESENT),
    FUTURE("Future (Alien Era)", "src/foodchain/future_animals.txt", Animal.Era.FUTURE);

    private final String displayName;
    private final String filePath;
    private final Animal.Era era;
    /**
     * Constructor
     * @param displayName
     * @param filePath
     * @param era
     */
    GameMode(String displayName, String filePath, Animal.Era era) {
        this.displayName = displayName;
        this.filePath = filePath;
        this.era = era;
    }
    //Getters
    public String getFilePath() {
    	return filePath; 
    	}
    public Animal.Era getEra() {
    	return era; 
    	}
    
    @Override
    public String toString() {
        return displayName;
    }
}
