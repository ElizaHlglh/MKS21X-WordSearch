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

    public static void main(String[]args){
      if (args.length == 0 || args.length == 1 || args.length == 2 || args.length > 5){
        System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
        System.exit(1); //terminate program when there is no input
      }
      try{
        if (Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[1]) <= 0){// if the row/col size are zero
          System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
        }
        else{//when the row/col aren't zero
          if (args.length == 3){
            WordSearch w = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
            System.out.println(w);
          }
          else if (args.length == 4){
            if (Integer.parseInt(args[3]) > 10000 || Integer.parseInt(args[3]) < 0){
              System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
            }
            else{
              WordSearch w = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), "No");
              System.out.println(w);
            }
          }
          else{//when there are five arguments
            if (Integer.parseInt(args[3]) > 10000 || Integer.parseInt(args[3]) < 0){
              System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
            }
            else{
              WordSearch w = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), args[4]);
              System.out.println(w);
            }
          }
        }
      }
      catch(NumberFormatException e){
        System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
      }

    }

    //Choose a randSeed using the clock random
    public WordSearch( int rows, int cols, String fileName){
      try{
        File f = new File(fileName);
        Scanner in = new Scanner(f);
        wordsToAdd = new ArrayList<String>();
        wordsAdded = new ArrayList<String>();
        while(in.hasNext()){
          wordsToAdd.add(in.next());
        }
        data = new char[rows][cols];
        for (int i = 0; i < rows; i++){
          for (int x = 0; x < cols; x++){
            data[i][x] = ' ';
          }
        }
        randgen = new Random();
        seed = Math.abs(randgen.nextInt()%10000);
        randgen = new Random(seed);
        addAllWords();
        fillIn();
      }
      catch(FileNotFoundException e){
        System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
        //e.printStackTrace();
        System.exit(1);
      }
    }

    public WordSearch( int rows, int cols, String fileName, int randSeed, String mode){
      try{
        File f = new File(fileName);
        Scanner in = new Scanner(f);
        wordsToAdd = new ArrayList<String>();
        wordsAdded = new ArrayList<String>();
        while(in.hasNext()){
          wordsToAdd.add(in.next());
        }
        data = new char[rows][cols];
        for (int i = 0; i < rows; i++){
          for (int x = 0; x < cols; x++){
            data[i][x] = ' ';
          }
        }
        randgen = new Random(randSeed);
        seed = randSeed;
        if (mode.equals("key")){
          addAllWords();
        }
        else{
          addAllWords();
          fillIn();
        }

      }
      catch(FileNotFoundException e){
        System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
        //e.printStackTrace();
        System.exit(1);
      }
    }

    public String toString(){
      String puzzle = "";
      for (int i = 0; i < data.length; i++){
        puzzle += "| ";
        for (int x = 0; x < data[i].length; x++){
          puzzle += data[i][x] + " ";
        }
        puzzle += "| \n";
      }
      puzzle += "Words: ";
      for (int i = 0; i < wordsAdded.size(); i++){
        if (i == wordsAdded.size()-1){
          puzzle += wordsAdded.get(i) + " (seed: " + seed + ")";
        }
        else{
          puzzle += wordsAdded.get(i) + ", ";
        }
      }
      return puzzle;
    }

   private boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
     try{
       if (rowIncrement == 0 && colIncrement == 0){
         return false;
       }
       int y = row;
       int x = col;
       for (int i = 0; i < word.length(); i++){ //check if addable
         if (data[y][x] == Character.toUpperCase(word.charAt(i)) || data[y][x] == ' '){
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
         data[y][x] = Character.toUpperCase(word.charAt(i));
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

    private void addAllWords(){
      for (int z = 0; z < 100 && wordsToAdd.size() != 0; z++){
        //1. random word + increments
        int x = Math.abs(randgen.nextInt() % wordsToAdd.size());
        String Randword = wordsToAdd.get(x);
        int  rowIncrement = randgen.nextInt() % 2;
        int  colIncrement = randgen.nextInt() % 2;

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

    private void fillIn(){
      String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      for (int y = 0; y < data.length; y++){
        for (int x = 0; x < data[y].length; x++){
          if (data[y][x] == (' ')){
            data[y][x] = alphabets.charAt(Math.abs(randgen.nextInt()) % alphabets.length());
          }
        }
      }
    }
}
