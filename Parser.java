//Kuzey Cimen - kc18182 - 1803189

package Assignment2;

// Parser class for CE204 assignment 2, 2020
// eleven methods need to be modified to use your contructor(s) - eight for part 3 and three for part 6
// (these are the static ExpTree methods at the end of the Parser class)
// these methods currently return null to allow this file to compile
// (in order to compile the current version you must have an ExpTree class in a file ExpTree.java
// but it doesn't have to have any attributes or methods)

// a getLine method has been supplied to allow you to read a line of text from the keyboard
// when asking the user if another expression is wanted;
// it strips leading and trailing white space and loops until a non-empty line is input

public class Parser {
    private Lexer lex;

    public Parser() {
        lex = new Lexer();
    }

    public String getLine() {
        return lex.getLine();
    }

    public ExpTree parseLine() {
        ExpTree defs = null;
        lex.init();
        lex.getToken();
        if (lex.token == Lexer.let) {
            lex.getToken();
            defs = parseDefList();
            if (lex.token != Lexer.in)
                throw new ParseException("'in' expected");
            lex.getToken();
        }
        ExpTree result = parseExp(true);
        if (lex.token != Lexer.semico)
            throw new ParseException("semicolon expected");
        if (defs != null)
            result = makeLetTree(defs, result);
        return result;
    }

    private ExpTree parseExp(boolean idsAllowed) {
        ExpTree result = parseTerm(idsAllowed);
        {
            while (lex.token == Lexer.plus || lex.token == Lexer.minus) {
                int op = lex.token;
                lex.getToken();
                if (op == Lexer.plus)
                    result = makePlusTree(result, parseTerm(idsAllowed));
                else
                    result = makeMinusTree(result, parseTerm(idsAllowed));
            }
        }
        return result;
    }

    private ExpTree parseTerm(boolean idsAllowed) {
        ExpTree result = parsePow(idsAllowed);
        {
            while (lex.token == Lexer.times || lex.token == Lexer.div || lex.token == Lexer.mod) {
                int op = lex.token;
                lex.getToken();
                if (op == Lexer.times)
                    result = makeTimesTree(result, parsePow(idsAllowed));
                else if (op == Lexer.div)
                    result = makeDivideTree(result, parsePow(idsAllowed));
                else
                    result = makeModTree(result, parsePow(idsAllowed));
            }
        }
        return result;
    }

    private ExpTree parsePow(boolean idsAllowed) {
        ExpTree result = parseOpd(idsAllowed);
        if (lex.token == Lexer.pow) {
            lex.getToken();
            result = makePowerTree(result, parsePow(idsAllowed));
        }
        return result;
    }

    private ExpTree parseOpd(boolean idsAllowed) {
        ExpTree result;
        switch (lex.token) {
            case Lexer.num:
                result = makeNumberLeaf(lex.numval);
                lex.getToken();
                return result;
            case Lexer.id:
                if (!idsAllowed)
                    throw new ParseException("identifier not allowed in identifier definition");
                result = makeIdLeaf(lex.idval);
                lex.getToken();
                return result;
            case Lexer.lp:
                lex.getToken();
                result = parseExp(idsAllowed);
                if (lex.token != Lexer.rp)
                    throw new ParseException("right parenthesis expected");
                lex.getToken();
                return result;
            default:
                throw new ParseException("invalid operand");
        }
    }

    private ExpTree parseDefList() {
        ExpTree result = parseDef();
        while (lex.token == Lexer.and) {
            lex.getToken();
            result = makeAndTree(result, parseDef());
        }
        return result;
    }

    private ExpTree parseDef() {
        if (lex.token != Lexer.id)
            throw new ParseException("definition must start with identifier");
        String id = lex.idval;
        lex.getToken();
        if (lex.token != Lexer.eq)
            throw new ParseException("'=' expected");
        lex.getToken();
        return makeDefTree(id, parseExp(false));
    }

    static ExpTree makeNumberLeaf(int n) {
//        System.out.println("Number: " + n);
        return new ExpTree(ExpTree.numNode, n, null, null);
    }

    static ExpTree makeIdLeaf(String s) {
//        System.out.println("ID: " + s);
        return new ExpTree(ExpTree.idNode, s, null, null);
    }

    static ExpTree makePlusTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.opNode, '+', l, r);
    }

    static ExpTree makeMinusTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.opNode, '-', l, r);
    }

    static ExpTree makeTimesTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.opNode, '*', l, r);
    }

    static ExpTree makeDivideTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.opNode, '/', l, r);
    }

    static ExpTree makeModTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.opNode, '%', l, r);
    }

    static ExpTree makePowerTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.opNode, '^', l, r);
    }

    static ExpTree makeLetTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.letNode, null, l, r);
    }

    static ExpTree makeAndTree(ExpTree l, ExpTree r) {
        return new ExpTree(ExpTree.andNode, null, l, r);
    }

    static ExpTree makeDefTree(String s, ExpTree t) {
        ExpTree idNode = new ExpTree(ExpTree.idNode, s, null, null);
        return new ExpTree(ExpTree.equalsNode, s, idNode, t);
    }
}