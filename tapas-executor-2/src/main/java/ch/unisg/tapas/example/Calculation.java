package ch.unisg.tapas.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;


@Getter
@Setter
@RequiredArgsConstructor
public class Calculation {

    @NonNull
    private String taskID;

    @NonNull
    private int[] values;

    /*
    @NonNull
    private String operator;
    */

    public int execute(){
        int sum = 0;

        for(int i =0; i<values.length; i++){
            sum+= values[i];
        }
        try {
            TimeUnit.SECONDS.sleep(4);
        }catch (Exception e){
            System.out.println(e);
        }

        return sum;

    }

}
