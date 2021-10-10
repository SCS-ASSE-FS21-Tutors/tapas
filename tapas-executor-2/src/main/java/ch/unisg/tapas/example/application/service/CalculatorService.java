package ch.unisg.tapas.example.application.service;

import ch.unisg.tapas.example.application.port.CalculatorCommand;
import ch.unisg.tapas.example.application.port.CalculatorUseCase;
import ch.unisg.tapas.example.domain.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional

public class CalculatorService implements CalculatorUseCase{
    //private final CalculatorPort calculatorPort;
    public Calculator newCalculator;

    @Override
    public Calculator executeCalculator(CalculatorCommand command){
        newCalculator = new Calculator(command.getInputValue(), command.getInputOperator());
        return newCalculator;
    }

    @Override
    public Calculator completeCalculator(CalculatorCommand command){
        newCalculator.complete();
        return newCalculator;
    }

}


/*public interface CalculatorService{
    void completeCalculation(CalculatorCommand command);
}
*/


/*
private final CalculatorPort calculatorPort;

    @Override
    public Calculator executeCalculator(CalculatorCommand command){
        Calculator calculator = new Calculator(command.getInputValue(), command.getInputOperator())
    }
 */
