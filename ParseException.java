//Kuzey Cimen - kc18182 - 1803189

package Assignment2;

import java.io.*;

class ParseException extends RuntimeException {
    public ParseException(String s) {
        super("Invalid expression: " + s);
    }
}