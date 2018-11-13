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
        data = new char[rows][cols];
        for (int i = 0; i < rows; i++){
          for (int x = 0; x < cols; x++){
            data[i][x] = '_';
          }
        }
        randgen = new Random();
        seed = randgen.nextInt();
      }
      catch(FileNotFoundException e){
        System.out.println("File not found: " + fileName);
        //e.printStackTrace();
        System.exit(1);
      }
    }

    public WordSearch( int rows, int cols, String fileName, int randSeed){
      try{
        File f = new File(fileName);
        Scanner in = new Scanner(f);
        while(in.hasNext()){
          wordsToAdd.add(in.next());
        }
        data = new char[rows][cols];
        for (int i = 0; i < rows; i++){
          for (int x = 0; x < cols; x++){
            data[i][x] = '_';
          }
        }
        randgen = new Random(randSeed);
        seed = randSeed;
      }
      catch(FileNotFoundException e){
        System.out.println("File not found: " + fileName);
        //e.printStackTrace();
        System.exit(1);
      }
    }

    public String toString(){
      String puzzle = "";
      for (int i = 0; i < data.length; i++){
        puzzle += "| ";
        for (int x = 0; x < data[i].length; x++){
          puzzle += data[i][x] + "  ";
        }
        puzzle += "| \n";
      }
      puzzle += "Words: ";
      for (int i = 0; i < wordsAdded.size(); i++){
        if (i == wordsAdded.size()-1){
          puzzle += wordsAdded.get(i) + "(seed: " + seed + ")";
        }
        else{
          puzzle += wordsAdded.get(i) + ", ";
        }
      }
      return puzzle;
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
    *The word is added in the direction rowIncrement,colIncrement
    *Words must have a corresponding letter to match any letters that it overlaps.
    *
    *@param word is any text to be added to the word grid.
    *@param row is the vertical locaiton of where you want the word to start.
    *@param col is the horizontal location of where you want the word to start.
    *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
    *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
    *@return true when: the word is added successfully.
    *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
    *        OR there are overlapping letters that do not match
    */
   private boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
     try{
       if (rowIncrement == 0 && colIncrement == 0){
         return false;
       }
       int y = row;
       int x = col;
       for (int i = 0; i < word.length(); i++){ //check if addable
         if (data[y][x] == word.charAt(i) || data[y][x] == '_'){
           y+= rowIncrement;
           x+= colIncrement;
         }
         else{
           return false;
         }
       }

       y = row;
       x = col;
       for (int i = 0; i < word.length(); i++){ //each character of word
         data[y][x] = word.charAt(i);
         y+= rowIncrement;
         x+= colIncrement;
       }
       wordsAdded.add(word);
       wordsToAdd.remove(word);
       return true;
     }
     catch(IndexOutOfBoundsException e){
        return false;
      }
   }

   /*[rowIncrement,colIncrement] examples:
    *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
    *[ 1,0] would add downwards because (row+1), with no col change
    *[ 0,-1] would add towards the left because (col - 1), with no row change
    */

    private void addAllWords(){
      for (int z = 0; z < 100 || wordsToAdd.size() == 0; z++){
        //1. random word + increments
        randgen = new Random();
        seed = Math.abs(randgen.nextInt() % wordsToAdd.size());
        String Randword = wordsToAdd.get(seed);
        int  rowIncrement = Math.abs(randgen.nextInt() % 3) -1;
        int  colIncrement = Math.abs(randgen.nextInt() % 3) -1;

        //2. try adding the word at random loc
        for (int i = 0; i < 10; i++){
          int row = Math.abs(randgen.nextInt() % data.length);
          int col = Math.abs(randgen.nextInt() % data[row].length);
          if (addWord(Randword, row, col, rowIncrement, colIncrement)){
            i = 10;
          }
        }
      }
    }
}
