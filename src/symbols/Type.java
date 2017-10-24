package symbols;

import lexer.*;

public class Type extends Word {
    public Type(String s, int tag) {
        super(s, tag);
    }

    public static final Type
            Int = new Type("int", Tag.BASIC),
            Float = new Type("float", Tag.BASIC),
            Char = new Type ("char", Tag.BASIC),
            Bool =  new Type("bool", Tag.BASIC);
}
