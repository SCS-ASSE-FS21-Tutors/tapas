package ch.unisg.executorbase.executor.application.service;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.executorbase.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executorbase.executor.domain.ExecutorType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotifyExecutorPoolService {

	private final NotifyExecutorPoolPort notifyExecutorPoolPort;

	public boolean notifyExecutorPool(ExecutorURI executorURI, ExecutorType executorType) {
		return notifyExecutorPoolPort.notifyExecutorPool(executorURI, executorType);
	}
}
