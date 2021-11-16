package ch.unisg.tapascommon;

public class ServiceHostAddresses {

    private static final boolean IS_LOCALHOST = false;

    private static final String LOCALHOST_TASKS = "http://127.0.0.1:8081";
    private static final String LOCALHOST_ROSTER = "http://127.0.0.1:8082";
    private static final String LOCALHOST_EXECUTOR_POOL = "http://127.0.0.1:8083";
    private static final String LOCALHOST_EXECUTOR_CALC = "http://127.0.0.1:8084";
    private static final String LOCALHOST_EXECUTOR_ROBOT = "http://127.0.0.1:8085";
    private static final String LOCALHOST_AUCTION_HOUSE = "http://127.0.0.1:8086";

    private static final String PUBLIC_TASKS = "https://tapas-tasks.86-119-35-199.nip.io";
    private static final String PUBLIC_ROSTER = "https://tapas-roster.86-119-35-199.nip.io";
    private static final String PUBLIC_EXECUTOR_POOL = "https://tapas-executor-pool.86-119-35-199.nip.io";
    private static final String PUBLIC_EXECUTOR_CALC = "https://tapas-executor-computaiton.86-119-35-199.nip.io";
    private static final String PUBLIC_EXECUTOR_ROBOT = "https://tapas-executor-bigrobot.86-119-35-199.nip.io";
    private static final String PUBLIC_AUCTION_HOUSE = "https://tapas-auction-house.86-119-35-199.nip.io";

    public static String getTaskServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_TASKS : PUBLIC_TASKS;
    }

    public static String getRosterServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_ROSTER : PUBLIC_ROSTER;
    }

    public static String getExecutorPoolServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_POOL : PUBLIC_EXECUTOR_POOL;
    }

    public static String getExecutorCalcServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_CALC : PUBLIC_EXECUTOR_CALC;
    }

    public static String getExecutorRobotServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_ROBOT : PUBLIC_EXECUTOR_ROBOT;
    }

    public static String getAuctionHouseServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_AUCTION_HOUSE : PUBLIC_AUCTION_HOUSE;
    }
}
