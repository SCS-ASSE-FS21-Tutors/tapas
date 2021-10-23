package ch.unisg.tapasroster.roster.application.port.out;

public interface TaskAssignedInternallyEventPort {
    boolean publishTaskAssignedInternallyEvent(String executorUrl);
}
