public class Data{

    // Instance variables
    private String configuration; // To store configuration
    private int score; // To store score

    // Constructor
    public Data(String config, int score){
        this.configuration = config;
        this.score = score;
    }

    // Method to return the configuration stored in this Data object
    public String getConfiguration(){
        return this.configuration;
    }

    // Method to return the score in this Data
    public int getScore(){
        return this.score;
    }
}
