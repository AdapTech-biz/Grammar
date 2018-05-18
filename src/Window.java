import javax.swing.*;
import java.awt.*;
import java.util.StringTokenizer;
/* Name: Xavier Davis
*  Date: Nov 19, 2017
*  Class: Window.java
*  Purpose: Creates the main JFrame for GUI display. Takes String as a
*           constructor param which contains the details of the JFrame.
*           A StringTokenizer is used to parse through the String.
*           The token counter is used to determine which part of the
*           JFrame to currently construct (name, window size, layout type)*/

/* Class used to create JFrame object for GUI
*  Accepts the entire Window creator line from file*/

public class Window{
    static private JFrame frame;

    public Window(String windowDetails) {
        StringTokenizer tokenizer = new StringTokenizer(windowDetails, "\"(), ");//est. the list of delimiters used in the tokenizer

        while (tokenizer.hasMoreTokens()) { //loops through all tokens
            switch (tokenizer.countTokens()) {  //switch block counts the number of remaining tokens to determine position of Window creator file line

                case 5: // current position indicated by [] -- [Calculator] 200 200 Layout Flow:
                    frame = new JFrame(tokenizer.nextToken());
                    break;
                case 4: // current position indicated by [] -- [200 200] Layout Flow:
                    int width, height;
                    width = Integer.parseInt(tokenizer.nextToken());
                    height = Integer.parseInt(tokenizer.nextToken());
                    frame.setPreferredSize(new Dimension(width, height+50));
//                    frame.setResizable(false);
                    break;
                case 2: // current position indicated by [] -- [Layout] Flow:
                    System.out.println("------" + tokenizer.nextToken());   //case 2 needed to remove String literal 'Layout' from token list
                    break;
                case 1: // current position indicated by [] -- [Flow:]
                    Layout layout = new Layout(tokenizer.nextToken()); // ONLY passes layout details to constructor
                    frame.setLayout(layout.getLayoutManager());
                    break;
                default:    //added for good practice
                    break;
            }
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //sets default frame closing action
    }
    public JFrame getFrame() {
        return frame;   //returns completed frame
    }
}
