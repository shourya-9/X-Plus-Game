import java.util.LinkedList; // importing linked list

class HashDictionary implements DictionaryADT
{
    // Instance variables
    private int size; // Size of hash table
    private LinkedList<Data>[] table;

    // Constructor
    public HashDictionary(int size){
        this.size = size;
        // Initializing the hash table
        this.table = new LinkedList[size];
        // Initializing every spot with a new linked list
        for(int i=0;i<size;i++)
            table[i]=new LinkedList<>();
    }

    // Hash function to generate indices
    private static int hash(String key, int M){
        int x = 11;
        int val = (int)key.charAt(0);
        for(int i=1;i<key.length();i++)
            val = (val * x + (int)key.charAt(i)) % M;
        return val;
    }

    // Method to insert record into table
    @Override
    public int put(Data record) throws DictionaryException {
        String key = record.getConfiguration();
        // Using hash function to find index of the key
        int hashVal = hash(key,size);
        // Getting the linked list at the given index
        LinkedList<Data> chain = table[hashVal];

        for (int i = 0; i < chain.size(); i++) {
            Data data = chain.get(i);
            if(data.getConfiguration().equals(key))
                throw new DictionaryException(); // Throwing exception if key already exists
        }
        // Adding record to the linked list
        chain.add(record);
        // Returning 1 if there are more than one item in the linked list i.e. there is a collison
        if (chain.size() > 1) return 1;
        return 0;

    }

    // Method to remove a record
    @Override
    public void remove(String config) throws DictionaryException {
        // Using hash function to find index of the key
        int hashVal = hash(config, size);
        // Getting the linked list at the given index
        LinkedList<Data> chain = table[hashVal];

        boolean found = false;
        // Using a for loop to iterate through the chain and find the desired configuration
        for (int i = 0; i < chain.size(); i++) {
            Data data = chain.get(i);
            if (data.getConfiguration().equals(config)) {
                chain.remove(i);
                found = true;
                break; // breaking out if found
            }
        }
        if (!found) {
            throw new DictionaryException(); // Throwing exception if not found
        }
    }

    // Method to retrieve the score of a record
    @Override
    public int get(String config) {
        // Using hash function to find the index of the key
        int hashVal = hash(config,size);
        // Getting the linked list at the given index
        LinkedList<Data> chain =  table[hashVal];
        // Using a for loop to iterate through the chain and find the desired configuration
        for (int i = 0; i < chain.size(); i++) {
            Data data = chain.get(i);
            if (data.getConfiguration().equals(config)) {
                return data.getScore(); // returning score if found
            }
        }
        return -1; // returning -1 if not found
    }

    // Method to count total number of records
    @Override
    public int numRecords() {
        int count = 0; // counter variable
        for(int i=0;i<table.length;i++){
            count += table[i].size(); // Adding size of each record
        }
        return count;
    }
}