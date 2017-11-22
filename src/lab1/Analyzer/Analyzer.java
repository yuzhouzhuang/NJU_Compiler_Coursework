package lab1.Analyzer;

import lab1.Property.State;
import lab1.Property.Type;
import lab1.util.IOHelper;

public class Analyzer {
    private IOHelper ioHelper;
    private State state;
    private StringBuffer identifierTemp;
    private StringBuffer numberTemp;
    private StringBuffer annotationTemp;
    private StringBuffer quotationTemp;
    private char operatorTemp;
    private StringBuffer output;

    public Analyzer() {
        this.ioHelper = new IOHelper();
        this.state = state.DONE;
        this.identifierTemp = new StringBuffer();
        this.numberTemp = new StringBuffer();
        this.annotationTemp = new StringBuffer();
        this.quotationTemp = new StringBuffer();
        this.output = new StringBuffer();
    }


    private void handleCharacter(char c) {
        if (Type.isLetter(c)) {
            identifierTemp.append(c);
            state = state.IDENTIFIER;
        } else if (Type.isDigit(c)) {
            numberTemp.append(c);
            state = state.INT;
        } else {
            switch (c) {
                case '/':
                    state = state.ANNOTATION_START;
                    break;
                case '+':
                case '-':
                case '*':
                case '%':
                case '<':
                case '>':
                case '=':
                case '!':
                case '&':
                case '|':
                    operatorTemp = c;
                    state = state.UNARY_OPERATOR;
                    break;
                case '\'':
                    state = state.SINGLE_QUOTE;
                    ioHelper.addIntoOutputBuffer("Operator           " + c);
                    break;
                case '"':
                    state = state.DOUBLE_QUOTE;
                    ioHelper.addIntoOutputBuffer("Operator           " + c);
                    break;
                case ';':
                case ':':
                case '{':
                case '}':
                case ',':
                case '.':
                case '[':
                case ']':
                case '?':
                case '~':
                case '^':
                    ioHelper.addIntoOutputBuffer("Operator           " + c);
                    break;
                case '(':
                    this.output.append("(");
                    ioHelper.addIntoOutputBuffer("Operator           " + c);
                    break;
                case ')':
                    this.output.append(")");
                    ioHelper.addIntoOutputBuffer("Operator           " + c);
                    break;
                default:
                    if (!(c == ' ' || c == '\t' || c == '\n' || c == '\r')) {
                        ioHelper.addIntoOutputBuffer("Can't recognize " + c);
                    }
                    break;
            }
        }
    }

    public String startAnalyze() {
        while (true) {
            char c = ioHelper.getChar();
            if (c == IOHelper.EOF) {
                break;
            }
            switch (this.state) {
                case DONE:
                    handleCharacter(c);
                    break;
                case UNARY_OPERATOR:
                    if (c == '+' || c == '=' || c == '-' || c == '%' || c == '<' || c == '>' || c == '=' || c == '!' || c == '&' || c == '|') {
                        ioHelper.addIntoOutputBuffer("Binary Operator    " + operatorTemp + c);
                        state = state.DONE;
                    } else {
                        ioHelper.addIntoOutputBuffer("Unary Operator     " + operatorTemp);
                        this.output.append(operatorTemp);
                        state = state.DONE;
                        handleCharacter(c);
                    }
                    break;
                case IDENTIFIER:
                    if (Type.isDigit(c) || Type.isLetter(c)) {
                        identifierTemp.append(c);
                    } else {
                        if (Type.isKeyword(identifierTemp.toString())) {
                            ioHelper.addIntoOutputBuffer("Keyword            " + identifierTemp.toString());
                        } else {
                            ioHelper.addIntoOutputBuffer("Identifier         " + identifierTemp.toString());
                        }
                        state = state.DONE;
                        this.output.append("i");
                        identifierTemp = new StringBuffer();
                        handleCharacter(c);
                    }
                    break;
                case ANNOTATION_START:
                    if (c == '=') {
                        ioHelper.addIntoOutputBuffer("Binary Operator    " + "/" + c);
                        state = state.DONE;
                    } else if (c == '/') {
                        annotationTemp.append("//");
                        state = state.ANNOTATION_ONE_TEMP;
                    } else if (c == '*') {
                        annotationTemp.append("/*");
                        state = state.ANNOTATION_TWO_TEMP_ONE;
                    } else {
                        ioHelper.addIntoOutputBuffer("Unary Operator     " + "/");
                        state = state.DONE;
                        handleCharacter(c);
                    }
                    break;
                case ANNOTATION_ONE_TEMP:
                    if (c == '\n') {  //说明读完了一行注释
                        state = state.DONE;
                        ioHelper.addIntoOutputBuffer("Annotation         " + annotationTemp);
                        annotationTemp = new StringBuffer();
                    } else {
                        annotationTemp.append(c);
                    }
                    break;
                case ANNOTATION_TWO_TEMP_ONE:
                    if (c == '*') {
                        state = state.ANNOTATION_TWO_TEMP_TWO;
                    } else {
                        state = state.ANNOTATION_TWO_TEMP_THREE;
                    }
                    annotationTemp.append(c);
                    break;
                case ANNOTATION_TWO_TEMP_TWO:
                    if (c == '*') {
                        state = state.ANNOTATION_TWO_TEMP_TWO;
                        annotationTemp.append(c);
                    } else if (c == '/') {
                        state = state.DONE;
                        ioHelper.addIntoOutputBuffer("Annotation         " + annotationTemp + "/");
                        annotationTemp = new StringBuffer();
                    } else {
                        state = state.ANNOTATION_TWO_TEMP_THREE;
                        annotationTemp.append(c);
                    }
                    break;
                case ANNOTATION_TWO_TEMP_THREE:
                    if (c == '/') {
                        state = state.ANNOTATION_TWO_TEMP_THREE;
                    } else if (c != '*') {
                        state = state.ANNOTATION_TWO_TEMP_ONE;
                    } else if (c == '*') {
                        ioHelper.findError();
                    }
                    annotationTemp.append(c);
                    break;
                case SINGLE_QUOTE:
                    if (c == '\'') {
                        state = state.DONE;
                        ioHelper.addIntoOutputBuffer("Character          " + quotationTemp);
                        ioHelper.addIntoOutputBuffer("Operator           " + "'");
                        quotationTemp = new StringBuffer();
                    } else {
                        quotationTemp.append(c);
                    }
                    break;
                case DOUBLE_QUOTE:
                    if (c == '"') {
                        state = state.DONE;
                        ioHelper.addIntoOutputBuffer("String             " + quotationTemp);
                        ioHelper.addIntoOutputBuffer("Operator           " + "\"");
                        quotationTemp = new StringBuffer();
                    } else {
                        quotationTemp.append(c);
                    }
                    break;
                case INT_DOT:
                    state = state.DOUBLE;
                    numberTemp.append(c);
                    break;
                case INT:
                    if (Type.isDigit(c)) {
                        numberTemp.append(c);
                    } else if (c == '.') {
                        numberTemp.append(c);
                        state = state.INT_DOT;
                    } else if (Type.isLetter(c)) {
                        ioHelper.findError();
                        state = state.DONE;
                        handleCharacter(c);
                    } else {
                        state = state.DONE;
                        ioHelper.addIntoOutputBuffer("Integer            " + numberTemp);
                        this.output.append("n");
                        numberTemp = new StringBuffer();
                        handleCharacter(c);
                    }
                    break;
                case DOUBLE:
                    if (Type.isDigit(c)) {
                        numberTemp.append(c);
                    } else if (Type.isLetter(c)) {
                        ioHelper.findError();
                        state = state.DONE;
                        handleCharacter(c);
                    } else {
                        state = state.DONE;
                        ioHelper.addIntoOutputBuffer("Double             " + numberTemp);
                        numberTemp = new StringBuffer();
                        handleCharacter(c);
                    }
                    break;
            }
        }

        System.out.println("Start analyze:");
        System.out.println("-------------------------------------------------------------------------------------------");
        ioHelper.startOutput();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("End analyze");
        return this.output.toString();
    }

}
