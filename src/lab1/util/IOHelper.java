package lab1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOHelper {
    private static final String INPUT_FILE = "file.txt";
    private Reader reader = null;
    private List<String> outputBuffer;
    private boolean isError;
    public static final char EOF = (char) 0;

    public IOHelper() {
        outputBuffer = new ArrayList<String>();
        File file = new File(INPUT_FILE);
        try {
            reader = new InputStreamReader(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public char getChar() {
        int tempChar;
        try {
            if ((tempChar = reader.read()) != -1) {
                return (char) tempChar;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EOF;

    }

    public void addIntoOutputBuffer(String string) {
        outputBuffer.add(string);
    }

    public void startOutput() {
        if (isError) {
            System.out.println("Error");
            return;
        }
        for (String string : outputBuffer) {
            System.out.println(string);
        }
    }

    public void findError() {
        isError = true;
    }
}
