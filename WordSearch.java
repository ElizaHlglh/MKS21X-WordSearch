import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception
public class WordSearch{
    private char[][]data;

    //the random seed used to produce this WordSearch
    private int seed;

    //a random Object to unify your random calls
    private Random randgen;

    //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String>wordsToAdd;

    //all words that were successfully added get moved into wordsAdded.
    private ArrayList<String>wordsAdded;

    //Choose a randSeed using the clock random
    public WordSearch( int rows, int cols, String fileName){
      try{
        File f = new File(fileName);
        Scanner in = new Scanner(f);
        while(in.hasNext()){
          wordsToAdd.add(in.next());
        }

        addWord(String word, row, col, int rowIncrement, int colIncrement);
      }
      catch(FileNotFoundException e){
        System.out.println("File not found: " + fileName);
        //e.printStackTrace();
        System.exit(1);
      }

    }

    public WordSearch( int rows, int cols, String fileName, int randSeed){

    }

    public String toString(){
      String puzzle = "";
      for (int i = 0; i < data.length; i++){
        puzzle += "| "
        for (int x = 0; x < data[i].length; x++){
          puzzle += data[i][x] + "  ";
        }
        puzzle += "| \n";
      }
      puzzle += "Words: ";
      for (int i = 0; i < wordsAdded.length; i++){
        if (i == wordsAdded.length-1){
          puzzle += wordsAdded[i] + "(seed: " + seed + ")";
        }
        else{
          puzzle += wordsAdded[i] + ", ";
        }
      }
      return puzzle;
    }

}
