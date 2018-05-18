import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.StringTokenizer;
/* Name: Xavier Davis
*  Date: Nov 19, 2017
*  Class: Widget.java
*  Purpose: Creates the widgets (Buttons, TextFields, Labels, and Radio Buttons).
*           StringTokenizer is used to parse the String information passed to class
*           Then a switch statement is used to recognize the type of widget to create.
*           Once all buttons are created they are stored in the widget variable*/

public class Widget {
    private Component widget;
    private Container parent;
    private static LinkedList<ButtonGroup> buttonGroups = new LinkedList<>();
    public static boolean groupOpen = false;


    public Widget(String widgetType) {  //Constructor accepts string of the type of widget to create in the switch statement
        StringTokenizer tokenizer = new StringTokenizer(widgetType.trim(), " \";");
        while (tokenizer.hasMoreTokens()){
            String currentToken = tokenizer.nextToken();    //assigns the current token to variable
            System.out.println("Widget.java " +currentToken);   //debugging
            switch (currentToken.toUpperCase()){
                case "BUTTON":
                    //handles the creation of a Button widget -- text for button is provided in the nextToken method call
                    this.widget= new JButton(tokenizer.nextToken());
                    System.out.println("button created...");    //debugging
                    break;
                case "TEXTFIELD":
                    //handles the creation of a Textfield widget -- text for button is provided in the nextToken method call
                    this.widget = createTextField(tokenizer.nextToken()); //sending width
                    System.out.println("text field created...");    //debugging
                    break;
                case "RADIO":
                    //handles the creation of a Radio Button widget -- text for button is provided in the nextToken method call
                    JRadioButton radioButton = new JRadioButton(tokenizer.nextToken());
                    this.widget = radioButton;
                    if(groupOpen){  //checks to see if the radio button needs to be added to a Button Group
                       buttonGroups.getLast().add(radioButton);
                    }
                    System.out.println("Radio Button created"); //debugging
                    break;
                case "LABEL":
                    //handles the creation of a Label widget
                    if(tokenizer.hasMoreTokens())   //text for button is provided in the nextToken method call
                        this.widget = new JLabel(tokenizer.nextToken());
                    else    //no text is provided for label
                        this.widget = new JLabel(" ");
                    break;
                default:
                    break;
            }
        }
    }

    //method creates a textfield with the provide width and default height
    private Component createTextField(String width){
        System.out.println("Creating text field");  //debugging
        int size = Integer.parseInt(width); //converts the width size to number value

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(size*10, 30));
        return textField;   //returns created textfield
    }

    public Component getWidget() {
        //returns the created widget
        return widget;
    }

    public void setParent(Container parent){
        //sets the parent container to add the widget to during frame creation
        this.parent = parent;
    }

    public Container getParent(){
        //getter for widget's parent container
        return parent;
    }

    public static void createBtnGroup(){
        ButtonGroup btnGroup = new ButtonGroup();
        buttonGroups.add(btnGroup);
    }

}