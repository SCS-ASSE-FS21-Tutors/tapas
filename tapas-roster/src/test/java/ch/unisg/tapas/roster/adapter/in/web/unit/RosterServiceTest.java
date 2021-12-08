package ch.unisg.tapas.roster.adapter.in.web.unit;

import ch.unisg.tapas.roster.application.port.out.AuctionHousePort;
import ch.unisg.tapas.roster.application.port.out.ExecutorPoolPort;
import ch.unisg.tapas.roster.application.services.BasicRosterService;
import ch.unisg.tapas.roster.entities.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RosterServiceTest {

    private AuctionHousePort auctionHousePort = Mockito.mock(AuctionHousePort.class);
    private ExecutorPoolPort executorPoolPort = Mockito.mock(ExecutorPoolPort.class);
    private BasicRosterService roster = new BasicRosterService(auctionHousePort, executorPoolPort);

    @Test
    void testRosterServiceInternalTask(){

        // Arrange
        var task = new Task();
        when(executorPoolPort.canExecuteInternally(task)).thenReturn(true);


        // Act
        roster.rostTask(task);

        // Assert
        verify(executorPoolPort, times(1)).executeInternally(task);
        verify(auctionHousePort, never()).executeExternally(any());
    }

    @Test
    void testRosterServiceExternalTask(){

        // Arrange
        var task = new Task();
        when(executorPoolPort.canExecuteInternally(task)).thenReturn(false);

        // Act
        roster.rostTask(task);

        // Assert
        verify(executorPoolPort, never()).executeInternally(any());
        verify(auctionHousePort, times(1)).executeExternally(task);
    }
}
