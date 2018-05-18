import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/* Name: Xavier Davis
*  Date: Nov 19, 2017
*  Class: Main.java
*  Purpose: Reads the text file containing the details of the GUI and processes
*           each line of the file using the while-loop. Before any GUI elements
*           are created, the syntax of the file line must be error-free and in the
*           correct syntax pattern. Once the entire file has been read, the final
*           JFrame is constructed and all widgets and panels are assembled together
*           for display*/

public class Main {
    private static Window mainFrame = null;
    private static Container currentAddTo = null;
    private static Map<Container, ArrayList<JPanel>> nestedPanels = new LinkedHashMap<>();
    private static LinkedList<String> tokenTracker = new LinkedList<>();
    private static LinkedList<Container> parents = new LinkedList<>();
    private static LinkedList<Widget> children = new LinkedList<>();
    private static int lineNumber = 1;

    public static void main(String[] args) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
            while (reader.ready()) {
                //sets up the StringTokenizer for the lines read from the file
                String currentLine = reader.readLine().trim();
                StringTokenizer tokenizer = new StringTokenizer(currentLine, " ;\\.");
                String currentToken = tokenizer.nextToken();

                //switch block to examine the initial token of the String line
                //a match search is run to see if the file line conforms to the syntax grammar provided in assignment
                switch (currentToken.toUpperCase()) {
                    case "WINDOW":
                        if (currentLine.matches("(Window)\\s([\"A-z])+\\s\\(\\d+\\,\\s\\d+\\)\\s(Layout)\\s((Flow)|((Grid)(\\(\\d+\\,\\s\\d+\\)|\\(\\d+\\,\\s\\d+\\,\\s\\d+\\,\\s\\d+\\))))\\:")) {
                            System.out.println("*****Window Match found!!!!");  //debugging purposes
                            mainFrame = new Window(tokenizer.nextToken(":"));   //passes the String info to Window.java w/o the semi-colon
                            //Since the Frame is the first element created it is marked as the first parent element to add to
                            currentAddTo = mainFrame.getFrame();
                            tokenTracker.push(currentToken);
                        } else {
                            throw new Syntax("Syntax error found in Window creation file line: " + lineNumber);
                        }

                        break;
                    case "PANEL":   //case for JPanel creation
                        String layoutType = currentLine.trim();
                        if (currentLine.matches("(Panel)\\s(Layout)\\s((Flow)|((Grid)(\\(\\d+\\,\\s\\d+\\)|\\(\\d+\\,\\s\\d+\\,\\s\\d+\\,\\s\\d+\\))))\\:")) {
                            System.out.println("*******Panel match found!!!");
                            //cuts off the unneeded info from Panel String line
                            //substring passes { ex: Grid(4, 3, 5, 5): }
                            Layout layout = new Layout(layoutType.trim().substring(12, layoutType.length() - 1));
                            //marks the created JPanel as the current parent for widgets to add to
                            JPanel panel = new JPanel(layout.getLayoutManager());
                            panel.setBorder(BorderFactory.createLineBorder(Color.RED));
                            if (tokenTracker.peek().equalsIgnoreCase("panel")) {
                                //needs to be nested--nested panels are stored in key-value map
                                if(!nestedPanels.containsKey(currentAddTo)){    //new nesting host element
                                    ArrayList<JPanel> panels = new ArrayList<>();   //adds child nodes to List
                                    panels.add(panel);
                                    nestedPanels.put(currentAddTo, panels);
                                }else{
                                    nestedPanels.get(currentAddTo).add(panel);
                                }
                                currentAddTo = panel;
                            } else {  //independent panel without nesting
                                currentAddTo = panel;
                                parents.add(currentAddTo);  //adds JPanel to parents list
                            }
                            tokenTracker.push(currentToken);
                        } else {
                            throw new Syntax("Syntax error found in Panel creation file line: " + lineNumber);
                        }

                        break;
                    case "GROUP":   //case for ButtonGroup creation
                        Widget.groupOpen = true; //sets boolean flag to open a new ButtonGroup to true
                        Widget.createBtnGroup();    //creates ButtonGroup object in Widget.java
                        tokenTracker.push(currentToken);
                        break;
                    case "END": //case for end tokens -- used to flag the closure of any currently open ButtonGroups
                        if (Widget.groupOpen) {
                            Widget.groupOpen = false;
                        }
                        tokenTracker.pop();
                        break;
                    default: //button, Group,  Label, Textfield
                        if (currentLine.matches("((Button)|(Label)|(Textfield)|(Radio))\\s([\"A-z|\\d])+\\;")) {
                            System.out.println("*******Widget found!!!");
                            Widget widget = new Widget(currentLine);
                            children.add(widget);
                            widget.setParent(currentAddTo);
                        } else {
                            throw new Syntax("Syntax error found in Widget creation file line: " + lineNumber);
                        }
                        break;
                }
                System.out.println(tokenTracker.toString());
                lineNumber++;
            }
            assembleFrame();    //final step in assembling GUI frame
        } catch (IOException | Syntax e) {
            System.out.println(e.getMessage()); //generic error handling
        }
    }

    /* Complete assembly of the GUI frame. Iterating through the list of created widgets
     * the get method is called for the parent container of the widget to which it is added.
     * Once all widgets are added to their parent elements, a for loop is employed to add
     * the parent containers to the main GUI frame. */
    private static void assembleFrame() {
        Iterator<Widget> widgetIterator = children.iterator();  //sets up widget iterator

        //forEach used to address each widget in the list
        widgetIterator.forEachRemaining(widget -> widget.getParent().add(widget.getWidget()));
        //checks to see if any parent elements were created from text file
        if (parents.size() > 0) {

            if (!nestedPanels.isEmpty()) {
                Set<Container> containers = nestedPanels.keySet();
                containers.forEach(container -> {
                    ArrayList<JPanel> children = nestedPanels.get(container);
                    children.forEach(child -> {
                        container.add(child);
                    });
                });
            }
            //loops through the parent elements and adding them to the frame in reverse order
            for (int i = parents.size() - 1; i >= 0; i--) {
                mainFrame.getFrame().add(parents.get(i));
            }
        }
        mainFrame.getFrame().pack();
        mainFrame.getFrame().setVisible(true);
    }

}

