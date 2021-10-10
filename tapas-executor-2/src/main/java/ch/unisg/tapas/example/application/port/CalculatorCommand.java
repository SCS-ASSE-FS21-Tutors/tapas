package ch.unisg.tapas.example.application.port;

import ch.unisg.tapas.example.SelfValidating;
import ch.unisg.tapas.example.domain.Calculator.InputOperator;
import ch.unisg.tapas.example.domain.Calculator.InputValue;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CalculatorCommand extends SelfValidating<CalculatorCommand>{

    @NotNull
    private final InputValue inputValue;

    @NotNull
    private final InputOperator inputOperator;

    public CalculatorCommand(InputValue inputValue, InputOperator inputOperator){
        this.inputValue = inputValue;
        this.inputOperator = inputOperator;
        this.validateSelf();
    }
}
