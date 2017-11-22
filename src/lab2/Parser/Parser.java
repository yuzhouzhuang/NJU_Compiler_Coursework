package lab2.Parser;

import lab2.util.IOHelper;
import lab2.util.Step;
import lab2.util.CFGItem;

import java.util.*;

public class Parser {

    public Map<Integer, Map<String, String>> ppt;
    public List<CFGItem> CFGItems;

    String input;
    Stack<Integer> S_Stack = new Stack<>();
    Stack<Character> O_Stack = new Stack<>();
    ArrayList<Step> steps = new ArrayList<Step>();

    //pase core method
    public void parse(Map<Integer, Map<String, String>> ppt, List<CFGItem> CFGItems, String input) {
        this.input = input;
        this.ppt = ppt;
        this.CFGItems = CFGItems;
        this.startParse();
    }

    private void startParse() {
        boolean parseResult = parseStep();
        System.out.println("StateStack              OperatorStack           InputStream             ActionType");
        for (Step step : steps) {
            System.out.println(step.getResult());
        }
        if (parseResult) {
            System.out.println("Parse success!");
        } else {
            System.out.println("Parse error!");
        }
    }

    private boolean parseStep() {
        input += "$";
        S_Stack.push(0);
        O_Stack.push('$');

        while (input.length() > 0) {
            String nextChar = String.valueOf(input.charAt(0));
            int state = S_Stack.peek();
            String ppt_item = ppt.get(state).get(nextChar);

            if (ppt_item == null) {
                return false;
            }

            if (ppt_item.equals("accept")) {
                steps.add(new Step(IOHelper.stack_string_parser(S_Stack),
                        IOHelper.stack_string_parser(O_Stack), input, "reduce by " + CFGItems.get(0).getValue()));

                return true;
            } else if (ppt_item.startsWith("S")) {
                steps.add(new Step(IOHelper.stack_string_parser(S_Stack),
                        IOHelper.stack_string_parser(O_Stack), input, "Shift"));

                int nextState = Integer.valueOf(ppt_item.substring(1));
                S_Stack.push(nextState);
                O_Stack.push(nextChar.charAt(0));
                input = input.substring(1);
            } else {
                int ppt_item_index = Integer.valueOf(ppt_item.substring(1));
                steps.add(new Step(IOHelper.stack_string_parser(S_Stack),
                        IOHelper.stack_string_parser(O_Stack), input, "reduce by " + CFGItems.get(ppt_item_index).getValue()));

                String leftValue = CFGItems.get(ppt_item_index).getLeftValue();
                String rightValue = CFGItems.get(ppt_item_index).getRightValue();
                for (int i = 0; i < rightValue.length(); i++) {
                    S_Stack.pop();
                    O_Stack.pop();
                }
                O_Stack.push(leftValue.charAt(0));
                if (ppt.get(S_Stack.peek()).get(leftValue) == null) {
                    return false;
                }
                int nextState = Integer.valueOf(ppt.get(S_Stack.peek()).get(leftValue));
                S_Stack.push(nextState);
            }
        }

        return false;
    }


}