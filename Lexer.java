//Kuzey Cimen - kc18182 - 1803189

package Assignment2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Lexer {
    static final int err = 0, num = 1, id = 2, plus = 3, minus = 4, times = 5, div = 6, mod = 7,
            lp = 8, rp = 9, semico = 10, let = 11, and = 12, in = 13, eq = 14, pow = 15;
    int token;
    String idval;
    int numval;
    private String line = "";
    private BufferedReader buf;

    Lexer() {
        buf = new BufferedReader(new InputStreamReader(System.in));
    }

    String getLine() {
        init();
        return (line);
    }

    void init() {
        do
            try {
                line = buf.readLine().trim();
            } catch (Exception e) {
                System.out.println("Unexpected error in input");
                System.exit(1);
            }
        while (line.length() == 0);
    }

    void getToken() {
        if (line.length() == 0)
            token = err;
        else switch (line.charAt(0)) {
            case '+':
                token = plus;
                line = line.substring(1).trim();
                break;
            case '-':
                token = minus;
                line = line.substring(1).trim();
                break;
            case '*':
                token = times;
                line = line.substring(1).trim();
                break;
            case '/':
                token = div;
                line = line.substring(1).trim();
                break;
            case '^':
                token = pow;
                line = line.substring(1).trim();
                break;
            case '%':
                token = mod;
                line = line.substring(1).trim();
                break;
            case '(':
                token = lp;
                line = line.substring(1).trim();
                break;
            case ')':
                token = rp;
                line = line.substring(1).trim();
                break;
            case ';':
                token = semico;
                line = line.substring(1).trim();
                break;
            case '=':
                token = eq;
                line = line.substring(1).trim();
                break;
            default:
                if (Character.isDigit(line.charAt(0))) {
                    token = num;
                    numval = line.charAt(0) - '0';
                    int i = 1;
                    while (i < line.length() && Character.isDigit(line.charAt(i))) {
                        numval = numval * 10 + line.charAt(i) - '0';
                        i++;
                    }
                    line = line.substring(i).trim();
                } else if (Character.isUpperCase(line.charAt(0))) {
                    token = id;
                    int i = 0;
                    while (i < line.length() && Character.isUpperCase(line.charAt(i)))
                        i++;
                    idval = line.substring(0, i);
                    line = line.substring(i).trim();
                } else if (line.length() >= 3 && line.charAt(0) == 'a' && line.charAt(1) == 'n' && line.charAt(2) == 'd' && (line.length() == 3 || !Character.isLowerCase(line.charAt(3)))) {
                    token = and;
                    line = line.substring(3).trim();
                } else if (line.length() >= 3 && line.charAt(0) == 'l' && line.charAt(1) == 'e' && line.charAt(2) == 't' && (line.length() == 3 || !Character.isLowerCase(line.charAt(3)))) {
                    token = let;
                    line = line.substring(3).trim();
                } else if (line.length() >= 2 && line.charAt(0) == 'i' && line.charAt(1) == 'n' && (line.length() == 2 || !Character.isLowerCase(line.charAt(2)))) {
                    token = in;
                    line = line.substring(2).trim();
                } else
                    token = err;
        }
    }
}
