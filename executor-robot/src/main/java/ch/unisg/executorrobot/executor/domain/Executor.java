package ch.unisg.executorrobot.executor.domain;

import java.util.concurrent.TimeUnit;

import ch.unisg.executorrobot.executor.adapter.out.DeleteUserFromRobotAdapter;
import ch.unisg.executorrobot.executor.adapter.out.InstructionToRobotAdapter;
import ch.unisg.executorrobot.executor.adapter.out.UserToRobotAdapter;
import ch.unisg.executorrobot.executor.application.port.out.DeleteUserFromRobotPort;
import ch.unisg.executorrobot.executor.application.port.out.InstructionToRobotPort;
import ch.unisg.executorrobot.executor.application.port.out.UserToRobotPort;
import ch.unisg.executorbase.executor.domain.ExecutorBase;
import ch.unisg.executorbase.executor.domain.ExecutorType;

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
    String execution(String... input) {

        String key = userToRobotPort.userToRobot();
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean result1 = instructionToRobotPort.instructionToRobot(key);
        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        deleteUserFromRobotPort.deleteUserFromRobot(key);

        return Boolean.toString(result1);
    }

}
