import java.awt.*;
import java.util.StringTokenizer;

/* Name: Xavier Davis
*  Date: Nov 19, 2017
*  Class: Layout.java
*  Purpose: Construct JPanel layouts for java GUI. Class accepts String
*           as constructor param detailing layout type (grid or flow)
*           StringTokenizer is used to parse through String content
*           After a switch statement is use to direct information to
*           correct creator method, depending on Grid type or Flow type.
*           Layout types are determined by token count:
*           1 token = flow
*           3 || 5 = grid
*           */
public class Layout {

    public enum Layout_Type {   //enum of two types of layouts
        Flow, Grid
    }

    private LayoutManager layoutType;

    public Layout(String layoutDetails) {

        // Layout Type Examples //
        //  Flow                //
        //  Grid(4, 3, 5, 5)    //
        //  Grid(3, 4)          //
        //////////////////////////

        StringTokenizer tokenizer = new StringTokenizer(layoutDetails, " (),:");
        int numTokens= tokenizer.countTokens(); //takes a count of the current tokens passed to class
//        System.out.println(tokenizer.countTokens()); //debugging purposes
        if(numTokens == 1)  //only one token indicates a Flow layout creator
            this.layoutType = createFlowLayout(tokenizer);  //method call for create flow layout
        if(numTokens ==3 || numTokens ==5)  // grind layout creator can contain 3 or 5 tokens
            this.layoutType =  createGridLayout(tokenizer);
    }

    //creates a flow layout -- determined by the token count within the constructor
    private LayoutManager createFlowLayout(StringTokenizer tokenizer){
        System.out.println("Creating flow layout"); //debugging purposes
        String currentToken = tokenizer.nextToken();
        if (currentToken.equalsIgnoreCase(Layout_Type.Flow.toString())) {   //a secondary check to ensure the string literal 'Flow' is passed
            return new FlowLayout();    //returns the newly created flow layout
            //no need to continue token read
        }else System.out.println("Flow Layout Error");  //if token string does not equal flow -- thrown error
        return null;
    }

    //creates a grid layout -- determined by the token count within the constructor
    private LayoutManager createGridLayout(StringTokenizer tokenizer){
        System.out.println("create grid layout");   //debugging
        switch (tokenizer.countTokens()){   //switch block counts the number of tokens passed
            case 3: //3 tokens indicates string format Grid(3, 4)
                while (tokenizer.hasMoreTokens()){
                    String currentToken = tokenizer.nextToken().trim();    //details type of layout
                    if(currentToken.equalsIgnoreCase(Layout_Type.Grid.toString())){ //a secondary check to ensure the string literal 'Grid' is passed
                        int rows, columns;
                        //stores information on row and columns for Grid
                        rows =  Integer.parseInt(tokenizer.nextToken());
                        columns = Integer.parseInt(tokenizer.nextToken());
                        return new GridLayout(rows, columns);   //returns GridLayout object
                    }
                }
                break;
            case 5: //5 tokens indicates string format Grid(x, x, x, x)
                while (tokenizer.hasMoreTokens()){
                    String currentToken = tokenizer.nextToken().trim();    //details type of layout
                    if(currentToken.equalsIgnoreCase(Layout_Type.Grid.toString())){ //a secondary check to ensure the string literal 'Grid' is passed
                        //stores information on row, column, hGap, and vGap for Grid
                        int rows, columns, hGap, vGap;
                        rows =  Integer.parseInt(tokenizer.nextToken());
                        columns = Integer.parseInt(tokenizer.nextToken());
                        hGap = Integer.parseInt(tokenizer.nextToken());
                        vGap = Integer.parseInt(tokenizer.nextToken());
                        return new GridLayout(rows,columns,hGap,vGap);  //returns GridLayout object
                    }
                }
        }
        return null;

    }

    public LayoutManager getLayoutManager() {
        //returns Layout type from class
        return layoutType;
    }
}