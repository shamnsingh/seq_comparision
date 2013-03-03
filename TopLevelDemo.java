/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topleveldemo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.io.*;
import java.util.Scanner;
import java.text.*;


/**
 *
 * @author shamnsingh
 */
public class TopLevelDemo extends JPanel implements ActionListener {
    
    private int counter = 0;
    private String[] input = new String[2];
    protected JTextField textField;
    protected JTextArea textArea;
    final protected JFileChooser openFile = new JFileChooser();
    private final static String newline = "\n";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("SAT - Sequence Alignment Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.add(new TopLevelDemo());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public TopLevelDemo() {
        super(new GridBagLayout());

        //textField = new JTextField(50);
        
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        //add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        openFile.addActionListener(this);
        
        int numFiles = 2;
        
        while (numFiles-- > 0) {
            int returnVal = openFile.showOpenDialog(this);
        }
    }
    
    public void actionPerformed(ActionEvent evt) {
        counter += 1;
        Scanner src = null;
        File file = null;
        
        try {
            file = openFile.getSelectedFile();
            src = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        String text = src.next();
        input[counter - 1] = text;
        textArea.append(file.getName() + ": " + file.getParent() + "/"
                    + file.getName() +  newline);
        //textField.selectAll();
        
        if (counter == 2) {
            counter = 0;
            
            System.out.println(newline);
            
            System.out.println(input[0] + newline + input[1]);
            
            textArea.append(newline + ">> Computing proper alignment..." + newline + newline);
            SequenceAligner seqRun = new SequenceAligner(input[0], input[1]);
            int result = seqRun.align();
            textArea.append("Misalignments: " + result + newline + newline);
            int[][] data = seqRun.score;
            String spacer = "  ";
             
           for (int i = 0; i <= (data[0].length - input[1].length()) + 1; i += 1) {
                spacer += " ";
            }
            
            System.out.println(spacer + ".");
            
            textArea.append("\t" + spacer + addSpace(input[1], 2) + newline);
            DecimalFormat myFormatter = new DecimalFormat("000");
            
            input[0] = " " + input[0];
            for (int i = 0; i < data.length; i += 1) {
                textArea.append(input[0].charAt(i) + "\t");
                textArea.append("[ ");
                for (int j = 0; j < data[0].length; j += 1) {
                    if (j != data[0].length - 1) {
                        textArea.append(myFormatter.format(data[i][j]) + ", ");
                    } else {
                        textArea.append(myFormatter.format(data[i][j]) + "");
                    }
                }
                textArea.append("]");
                textArea.append(newline);
            }
            
            System.out.println(newline);
        }

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    private String addSpace(String input, int space) {
        String result = "        " + input.charAt(0);
        int character = 1;
        
        while (character < input.length()) {
            for (int i = 0; i < space; i += 1) {
                result += "   ";
            }
            result += input.charAt(character);
            character += 1;
        }
        
        return result;
    }
}
