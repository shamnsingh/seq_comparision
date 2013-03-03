/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topleveldemo;

/**
 *
 * @author shamnsingh
 */
public class SequenceAligner {
    /** Respective sequences to be aligned. */
    
    private String inpOne = "";
    private String inpTwo = "";
    int[][] score;
    
    SequenceAligner(String inpOne, String inpTwo) {
        this.inpOne = inpOne;
        this.inpTwo = inpTwo;
        score = new int[inpOne.length() + 1][inpTwo.length() + 1];
    }
    
    int align() {
       
       for (int i = 0; i < inpOne.length() + 1; i += 1) {
           score[i][0] = i;
       }
       
       for (int j = 0; j < inpTwo.length() + 1; j += 1) {
           score[0][j] = j;
       }
       
       for (int i = 1; i < inpOne.length() + 1; i += 1) {
           for (int j = 1; j < inpTwo.length() + 1; j += 1) {
               score[i][j] = Math.min(Math.min(score[i - 1][j] + 1,
                        score[i][j - 1] + 1), score[i - 1][j - 1] + diff(i, j));
           }
       }
      
       return score[inpOne.length()][inpTwo.length()];
    }
    
    private int diff(int i, int j) {
        if (inpOne.charAt(i - 1) == inpTwo.charAt(j - 1)) {
            return 0;
        } else {
            return 1;
        }
    }
}
