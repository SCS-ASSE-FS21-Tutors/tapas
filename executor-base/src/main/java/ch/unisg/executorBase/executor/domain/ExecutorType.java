package ch.unisg.executorbase.executor.domain;

public enum ExecutorType {
    ADDITION, ROBOT;

    /**
    *   Checks if the give executor type exists.
    *   @return Wheter the given executor type exists
    **/
    public static boolean contains(String test) {

        for (ExecutorType x : ExecutorType.values()) {
            if (x.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}



