package ch.unisg.tapas.tapasexecutor2.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CancellationException;

import static org.junit.jupiter.api.Assertions.*;

class ComputationServiceTest {

    @Test
    public void testComputation(){

        var service = new ComputationService();

        assertEquals("4", service.executeComputation("1+3"));
        assertEquals("84", service.executeComputation("(3+4)*12"));
        assertEquals("21", service.executeComputation("function fibonacci(n) {if (n < 2){return 1;}else{return fibonacci(n-2) + fibonacci(n-1);}};fibonacci(7)"));

    }

    @Test
    public void testSandbox(){
        var service = new ComputationService();

        assertEquals("ERROR", service.executeComputation("while(true){console.log('haha')}"));
    }
}