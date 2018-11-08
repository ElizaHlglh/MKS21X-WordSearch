public class WordSearch{
    private char[][]data;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols){
      try{
        data = new char[rows][cols];
        for (int i = 0; i < rows; i++){
          for (int x = 0; x < cols; x++){
            data[i][x] = '_';
          }
        }
      }
      catch(NegativeArraySizeException e){
        System.out.println("Invalid input");
      }
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int i = 0; i < data.length; i++){
        for (int x = 0; x < data[i].length; x++){
          data[i][x] = '_';
        }
      }
    }

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString(){
      String puzzle = "";
      for (int i = 0; i < data.length; i++){
        for (int x = 0; x < data[i].length; x++){
          puzzle += data[i][x] + "  ";
        }
        puzzle += "\n";
      }
      return puzzle;
    }


    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */
    public boolean addWordHorizontal(String word,int row, int col){
      try{
        if (col + word.length() - 1 < data[row].length){ //testing overlap
          int letter = 0;
          for (int i = col; i < data[row].length && letter < word.length(); i++){
            if (data[row][i] == word.charAt(letter) || data[row][i] == ('_')){
              letter++;
            }
            else{
              return false;
            }
          }

          letter = 0;
          for (int i = col; i < data[row].length && letter < word.length(); i++){
            data[row][i] = word.charAt(letter);
            letter++;
          }
          return true;
        }
        else{
          return false;
        } //in case col is > than the row's length
      }
      catch(IndexOutOfBoundsException e){
        return false;
      }
    }

   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
    public boolean addWordVertical(String word,int row, int col){
      try{
        if (row + word.length() - 1 < data.length){
          int letter = 0;
          for (int i = row; i < data.length && letter < word.length(); i++){
            data[i][col] = word.charAt(letter);
            letter++;
          }
          return true;
        }
        else{
          return false;
        } //in case row is > than the data's length
      }
      catch(IndexOutOfBoundsException e){
        return false;
      }
    }
}
