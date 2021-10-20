package ch.unisg.tapascommon;

public class ServiceApiAddresses {

    private static final boolean IS_LOCALHOST = true;

    private static final String LOCALHOST_TASK_SERVICE_API = "http://127.0.0.1:8081";
    private static final String LOCALHOST_ROSTER_SERVICE_API = "http://127.0.0.1:8082";
    private static final String LOCALHOST_EXECUTOR_POOL_SERVICE_API = "http://127.0.0.1:8083";
    private static final String LOCALHOST_EXECUTOR_API_CALC = "http://127.0.0.1:8084";
    private static final String LOCALHOST_EXECUTOR_API_ROBOT = "http://127.0.0.1:8085";

    private static final String PUBLIC_TASK_SERVICE_API = "https://tapas-tasks.86-119-35-199.nip.io";
    private static final String PUBLIC_ROSTER_SERVICE_API = "https://tapas-roster.86-119-35-199.nip.io";
    private static final String PUBLIC_EXECUTOR_POOL_SERVICE_API = "https://tapas-executorpool.86-119-35-199.nip.io";
    private static final String PUBLIC_EXECUTOR_API_CALC = "https://tapas-executorcalc.86-119-35-199.nip.io";
    private static final String PUBLIC_EXECUTOR_API_ROBOT = "https://tapas-executorrobot.86-119-35-199.nip.io";

    public static final String getTaskServiceApiUrl() {
        return IS_LOCALHOST ? LOCALHOST_TASK_SERVICE_API : PUBLIC_TASK_SERVICE_API;
    }

    public static final String getRosterServiceApiUrl() {
        return IS_LOCALHOST ? LOCALHOST_ROSTER_SERVICE_API : PUBLIC_ROSTER_SERVICE_API;
    }

    public static final String getExecutorPoolServiceApiUrl() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_POOL_SERVICE_API : PUBLIC_EXECUTOR_POOL_SERVICE_API;
    }

    public static final String getExecutorCalcServiceApiUrl() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_API_CALC : PUBLIC_EXECUTOR_API_CALC;
    }

    public static final String getExecutorRobotServiceApiUrl() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_API_ROBOT : PUBLIC_EXECUTOR_API_ROBOT;
    }
}
