package ch.unisg.tapas.example.application.port;

import ch.unisg.tapas.example.domain.Calculator;

public interface CalculatorUseCase {
    Calculator executeCalculator(CalculatorCommand command);
    Calculator completeCalculator(CalculatorCommand command);
}
