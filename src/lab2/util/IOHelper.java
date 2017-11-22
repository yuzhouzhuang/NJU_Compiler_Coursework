package lab2.util;

import java.io.*;
import java.util.*;

public class IOHelper {
    private static final String PPT_CSV = "ppt.csv";
    private BufferedReader reader = null;

    public static  <T> String stack_string_parser(Stack<T> stack) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stack.size(); i++) {
            T object = stack.get(i);
            stringBuffer.append(String.valueOf(object) + " ");
        }
        return stringBuffer.toString();
    }

    public Map<Integer, Map<String, String>> getPPT() {
        Map<Integer, Map<String, String>> ppt = new HashMap<Integer, Map<String, String>>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PPT_CSV))));
            reader.readLine();
            String line = reader.readLine();
            ArrayList<String> keyArrayList = new ArrayList<String>();
            String[] keyArray = line.split(",");
            for (int i = 1; i < keyArray.length; i++) {
                keyArrayList.add(keyArray[i]);
            }

            while ((line = reader.readLine()) != null) {
                Map<String, String> pptItem = new HashMap<String, String>();
                keyArray = line.split(",");
                for (int i = 1; i < keyArray.length; i++) {
                    if(!keyArray[i].equals("")){
                        pptItem.put(keyArrayList.get(i - 1), keyArray[i]);
//                        System.out.println("pptItem: " +keyArrayList.get(i - 1)+ ", "+ keyArray[i]);
                    }
                }
                ppt.put(Integer.parseInt(keyArray[0]), pptItem);
//                System.out.println("ppt: " + keyArray[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ppt;
    }

    public List<CFGItem> getProductions(){
        List<CFGItem> CFGItemList = new ArrayList<CFGItem>();
        CFGItemList.add(new CFGItem("S'","S"));
        CFGItemList.add(new CFGItem("S","S+S"));
        CFGItemList.add(new CFGItem("S","S*S"));
        CFGItemList.add(new CFGItem("S","(S)"));
        CFGItemList.add(new CFGItem("S","n"));
        CFGItemList.add(new CFGItem("S","i"));
        return CFGItemList;
    }

//    public static void main(String[] args) {
//        IOHelper ioHelper = new IOHelper();
//        ioHelper.getPPT();
//    }
}
