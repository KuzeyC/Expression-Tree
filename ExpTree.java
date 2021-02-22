//Kuzey Cimen - kc18182 - 1803189

package Assignment2;

public class ExpTree {
    private int kind;
    private Object value;
    private ExpTree left, right;
    //Nodes
    public static final int numNode = 0, idNode = 1, opNode = 2, letNode = 3, andNode = 4, equalsNode = 5;
    //Alphabet to get the index position if the expression does not start with a "let".
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //To check if a definition is set in the "let".
    private static boolean added = false;

    ExpTree(int knd, Object val, ExpTree l, ExpTree r) {
        kind = knd;
        value = val;
        left = l;
        right = r;
    }

    //This method is for part 2 of the assignment. It recursively orders the expression to make it a post-order.
    public String createPostOrder() {
        if (this.kind == opNode) {
            //Return the expression in post order.
            return this.left.createPostOrder() + " " + this.right.createPostOrder() + " " + this.value;
        } else if (this.kind == letNode) {
            //Return the expression.
            return this.right.createPostOrder();
        }
        //Return the value.
        return this.value.toString();
    }

    //This method is for part 6 of the assignment. It recursively evaluates the expression.
    public int evaluate() {
        if (this.kind == numNode) { //If it is a num node.
            return (int) this.value; //Return the value of the num node.
        } else if (this.kind == idNode) { //If it is an ID node.
            if (Ass2.vals != null) { //If the HashMap is not null (starting with let).
                if (Ass2.vals.containsKey(this.value.toString())) { //Check if the id has a set value in the HashMap.
                    added = true;
                    return Ass2.vals.get(this.value.toString()); //Return the value set to the id node.
                } else {
                    if (added) { //Only enters this if statement after the addition to the HashMap has been made.
                        System.out.println("Warning: " + this.value + " has not been defined.");
                    }
                    return 0;
                }
            } else { //HashMap is null (
                //Return the 25 - (position of the id in the string)
                return 25 - alphabet.indexOf(this.value.toString());
            }
        } else if (this.kind == letNode) { //If it is a let node.
            this.left.evaluate(); //Evaluate left first.
            return this.right.evaluate(); //Evaluate right node then return it (returns the expression result).

        } else if (this.kind == andNode) { //If it is an and node.
            this.left.evaluate(); //Evaluate left first.
            this.right.evaluate(); //Evaluate right node.
            return 1;

        } else if (this.kind == equalsNode) { //If it is an equals node.
            this.left.evaluate(); //Evaluate left first.
            int right = this.right.evaluate(); //Evaluate right node.
//            System.out.println(this.left.value + " = " + right); //Display the defined variables.
            if (Ass2.vals != null) { //If the HashMap is not null (starting with let).
                Ass2.vals.put((String) this.left.value, right); //Add the value of the expression to the HashMap with the key being the id node.
            }
            return 1;

        } else { //If it is an operator node.
            int leftSum = this.left.evaluate(); //Evaluate left first.
            int rightSum = this.right.evaluate(); //Evaluate right node.

            //Switch case for evaluating the operator node and doing the calculation with that operator.
            switch ((char) this.value) {
                case '^':
                    if (rightSum < 0) { //Throw exception because cant be to the power of 0.
                        throw new ArithmeticException("Cannot not be to the power of " + rightSum);
                    }
                    return (int) Math.pow(leftSum, rightSum);
                case '%':
                    return leftSum % rightSum;
                case '*':
                    return leftSum * rightSum;
                case '/':
                    if (rightSum == 0) { //Throw exception because cant be divided by 0.
                        throw new ArithmeticException("Cannot be divided by zero.");
                    }
                    return leftSum / rightSum;
                case '-':
                    return leftSum - rightSum;
                case '+':
                    return leftSum + rightSum;
                default:
                    return 0;
            }
        }
    }

    //This method is for part 5 of the assignment. It recursively orders the expression to make it an in-order.
    @Override
    public String toString() {
        if (this.kind == opNode) {
            //Return the expression in brackets.
            return "(" + this.left.toString() + " " + this.value + " " + this.right.toString() + ")";
        } else if (this.kind == letNode) {
            //Return the let node and the definitions of the variables, then the expression in brackets.
            return "let " + this.left.toString() + " in " + this.right.toString();
        } else if (this.kind == equalsNode) {
            //Return the expression variable and its defined value.
            return this.left.toString() + " = " + this.right.toString();
        } else if (this.kind == andNode) {
            //Return the and node, the left and the right.
            return this.left.toString() + " and " + this.right.toString();
        }
        //Return the value
        return this.value.toString();
    }
}
