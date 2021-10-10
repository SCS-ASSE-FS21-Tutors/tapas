package ch.unisg.tapas.example.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter

public class Calculator {

    @Getter
    private final UUID calculatorId;

    @Getter
    private InputValue inputValue;

    @Getter
    private InputOperator inputOperator;

    @Getter
    private Answer answer;

    @Getter
    private CalculatorState state;

    public Calculator(InputValue inputValue, InputOperator inputOperator) {
        //this.calculatorId = new CalculatorId(UUID.randomUUID().toString())
        this.calculatorId = UUID.randomUUID();
        this.inputValue = inputValue;
        this.inputOperator = inputOperator;
        this.answer = new Answer("0");
        this.state = CalculatorState.STARTED;
    }

    public void complete() {
        validateState();
        this.state = CalculatorState.COMPLETED;
    }

    private void validateState() {
        if (CalculatorState.COMPLETED.equals(state)) {
            throw new DomainException("The Calculator is in completed state.");
        }
    }

    public UUID getId(){
        return calculatorId;
    }

    public CalculatorState getState(){
        return state;
    }

    @Value
    public static class InputValue {
        private String value;
    }

    @Value
    public static class InputOperator {
        private String value;
    }

    @Value
    public static class Answer {
        private String value;
    }
}



    /*public void setState(ExecutorState s){
        state = s;
    }*/

    /*protected static Calculator createCalculator(InputValue inputValue, InputOperator inputOperator) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Calculator: " + calculatorId)
        return new Task(name,type);
    }*/

    /*@Value
    public static class CalculatorId {
        private String value;
    }*/
