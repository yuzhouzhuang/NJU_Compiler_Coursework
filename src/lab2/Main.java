package lab2;

import lab1.Analyzer.Analyzer;
import lab2.Parser.Parser;
import lab2.util.IOHelper;

public class Main {
    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        String outputTemp = analyzer.startAnalyze();

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("TempExpression          " + outputTemp);
        System.out.println("Start parse:");
        System.out.println("-------------------------------------------------------------------------------------------");
        IOHelper ioHelper = new IOHelper();
        Parser parser = new Parser();
        parser.parse(ioHelper.getPPT(), ioHelper.getProductions(),outputTemp);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("End parse");
    }
}
