package ch.unisg.executor1.executor.domain;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import ch.unisg.executor1.executor.adapter.out.DeleteUserFromRobotAdapter;
import ch.unisg.executor1.executor.adapter.out.InstructionToRobotAdapter;
import ch.unisg.executor1.executor.adapter.out.UserToRobotAdapter;
import ch.unisg.executor1.executor.application.port.out.DeleteUserFromRobotPort;
import ch.unisg.executor1.executor.application.port.out.InstructionToRobotPort;
import ch.unisg.executor1.executor.application.port.out.UserToRobotPort;
import ch.unisg.executorBase.executor.domain.ExecutorBase;
import ch.unisg.executorBase.executor.domain.ExecutorType;

public class Executor extends ExecutorBase {
    
    private static final Executor executor = new Executor(ExecutorType.ROBOT);
    private final UserToRobotPort userToRobotPort = new UserToRobotAdapter();
    private final InstructionToRobotPort instructionToRobotPort = new InstructionToRobotAdapter();
    private final DeleteUserFromRobotPort deleteUserFromRobotPort = new DeleteUserFromRobotAdapter();

    public static Executor getExecutor() {
        return executor;
    }

    private Executor(ExecutorType executorType) {
        super(executorType);
    }

    @Override
    protected
    String execution() {
        
        String key = userToRobotPort.userToRobot();
        boolean result1 = instructionToRobotPort.instructionToRobot(key);
        deleteUserFromRobotPort.deleteUserFromRobot(key);

        return Boolean.toString(result1);
    }

}