package ch.unisg.tapascommon.communication;

public class ServiceHostAddresses {

    public static final boolean IS_LOCALHOST = false;

    public static final String GROUP_NAME = "tapas-group4";
    public static final int GROUP_NUMBER = 4;

    public static final String LOCALHOST_TASKS = "http://127.0.0.1:8081";
    public static final String LOCALHOST_ROSTER = "http://127.0.0.1:8082";
    public static final String LOCALHOST_EXECUTOR_POOL = "http://127.0.0.1:8083";
    public static final String LOCALHOST_EXECUTOR_COMPUTATION = "http://127.0.0.1:8084";
    public static final String LOCALHOST_EXECUTOR_BIGROBOT = "http://127.0.0.1:8085";
    public static final String LOCALHOST_EXECUTOR_MIRO = "http://127.0.0.1:8087";
    public static final String LOCALHOST_AUCTION_HOUSE = "http://127.0.0.1:8086";

    public static final String PUBLIC_TASKS = "https://tapas-tasks.86-119-35-199.nip.io";
    public static final String PUBLIC_ROSTER = "https://tapas-roster.86-119-35-199.nip.io";
    public static final String PUBLIC_EXECUTOR_POOL = "https://tapas-executor-pool.86-119-35-199.nip.io";
    public static final String PUBLIC_EXECUTOR_COMPUTATION = "https://tapas-executor-computation.86-119-35-199.nip.io";
    public static final String PUBLIC_EXECUTOR_BIGROBOT = "https://tapas-executor-bigrobot.86-119-35-199.nip.io";
    public static final String PUBLIC_EXECUTOR_MIRO = "https://tapas-executor-miro.86-119-35-199.nip.io";
    public static final String PUBLIC_AUCTION_HOUSE = "https://tapas-auction-house.86-119-35-199.nip.io";

    public static String getTaskServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_TASKS : PUBLIC_TASKS;
    }

    public static String getRosterServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_ROSTER : PUBLIC_ROSTER;
    }

    public static String getExecutorPoolServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_POOL : PUBLIC_EXECUTOR_POOL;
    }

    public static String getExecutorComputationServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_COMPUTATION : PUBLIC_EXECUTOR_COMPUTATION;
    }

    public static String getExecutorBigrobotServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_EXECUTOR_BIGROBOT : PUBLIC_EXECUTOR_BIGROBOT;
    }

    public static String getAuctionHouseServiceHostAddress() {
        return IS_LOCALHOST ? LOCALHOST_AUCTION_HOUSE : PUBLIC_AUCTION_HOUSE;
    }
}
