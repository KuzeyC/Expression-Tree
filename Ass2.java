//Kuzey Cimen - kc18182 - 1803189

package Assignment2;

import java.util.HashMap;
import java.util.Scanner;

public class Ass2 {
    static HashMap<String, Integer> vals = null;

    public static void main(String[] args) {
        System.out.println("1803189");
        System.out.println("Welcome to Kuzey's expression evaluation program. Please type an expression.");

        //New Parser class.
        Parser p = new Parser();

        //Boolean to loop if a user requests for entering another expression.
        boolean loop = true;
        do {
            System.out.println("Please enter an expression.");
            try {
                //Get input from keyboard and use the given classes.
                ExpTree t = p.parseLine();

                //Post Order
                System.out.println("Post Order: " + t.createPostOrder());

                //In Order
                String inOrder = t.toString();
                System.out.println("In-Order: " + inOrder);

                //Get the first word from the in order method (to see if it is "let").
                String firstWord = inOrder.split(" ", 2)[0];
                //If the first string until a space is "let", set the HashMap to a new HashMap.
                if (firstWord.equals("let")) {
                    vals = new HashMap<>();
                }

                //Evaluate.
                System.out.println("Evaluation: " + t.evaluate());
            } catch (ParseException e) {
                System.out.println("Error: " + e.toString());
                System.out.println("Try again.");
                continue;
            }

            System.out.println();

            //Ask for another expression.
            System.out.println("Another expression (y/n)?");
            //Request for an input.
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();

            //Keep looping until it is y.
            while (!input.equals("y")) {
                //If it is "n", set loop boolean to false and break from this inner while loop.
                if (input.equals("n")) {
                    loop = false;
                    break;
                }

                //If it is not "y" nor "n", request for a valid answer.
                System.out.println("Enter a valid answer.");
                System.out.println("Another expression (y/n)?");
                input = s.nextLine();
            }

            //If HashMap is not null, clear it.
            if (vals != null) {
                vals = null;
            }
//            if (input.equals("y")) {
//                continue;
//            } else {
//                break;
//            }
        } while (loop);
    }
}
