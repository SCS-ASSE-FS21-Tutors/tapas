package ch.unisg.tapas.example;

public class CalculationThread extends Thread {

    Calculation calculation;

    public CalculationThread(Calculation calculation){
        this. calculation = calculation;
    }

    public void run() {
        System.out.println(calculation.execute());

        // implement callback to executor pool with the results
    }

}
