package ch.unisg.tapas.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ExampleEnitity {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private int age;
}
