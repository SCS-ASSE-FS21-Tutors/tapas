package ch.unisg.tapasadditionexecutor;

import org.springframework.stereotype.Component;

@Component
public class AddNumbersService implements AddNumbersUseCase {
    public float addNumbers(AddNumbersCommand command) {
        return command.getOperator1() + command.getOperator2();

    }
}
