package ch.unisg.tapas.example.adapter;

import ch.unisg.tapas.example.domain.Calculator;
import org.json.JSONObject;

public class CalculatorMediaType {
    public static final String TASK_MEDIA_TYPE = "application/json";

    public static String serialize(Calculator calculator) {
        JSONObject payload = new JSONObject();

        payload.put("calculatorId", calculator.getCalculatorId());
        payload.put("inputValue", calculator.getInputValue().getValue());
        payload.put("inputOperator", calculator.getInputOperator().getValue());
        payload.put("answer", calculator.getAnswer().getValue());
        payload.put("state", calculator.getState());

        return payload.toString();
    }

    private CalculatorMediaType() { }
}
