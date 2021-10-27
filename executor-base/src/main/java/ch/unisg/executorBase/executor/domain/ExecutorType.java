package ch.unisg.executorbase.executor.domain;

public enum ExecutorType {
    ADDITION, ROBOT;

    public static boolean contains(String test) {

        for (ExecutorType x : ExecutorType.values()) {
            if (x.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}



