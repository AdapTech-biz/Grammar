/* Name: Xavier Davis
*  Date: Nov 19, 2017
*  Class: Syntax.java
*  Purpose: Exception error thrown when the file is not constructed properly.
*           Class accepts a String value to pass the exception message back to
*           the super class Exception.java*/
public class Syntax extends Exception {
    public Syntax(String message) {
        super(message);
    }
}
