package lab2.util;

public class Step {
    private String State_Stack, Operator_Stack, Input, Action;

    public Step(String State_Stack, String Operator_Stack, String Input, String Action) {
        this.State_Stack = State_Stack;
        this.Operator_Stack = Operator_Stack;
        this.Input = Input;
        this.Action = Action;
    }

    public String getResult() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(State_Stack);
        for (int i = 0; i < 24 - State_Stack.length(); i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(Operator_Stack);
        for (int i = 0; i < 24 - Operator_Stack.length(); i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(Input);
        for (int i = 0; i < 24 - Input.length(); i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(Action);
        return stringBuffer.toString();
    }
}
