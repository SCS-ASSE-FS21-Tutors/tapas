package ch.unisg.executorBase.executor.application.service;

import ch.unisg.executorBase.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executorBase.executor.domain.ExecutorType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotifyExecutorPoolService {

	private final NotifyExecutorPoolPort notifyExecutorPoolPort;

	public boolean notifyExecutorPool(String ip, int port, ExecutorType executorType) {
		return notifyExecutorPoolPort.notifyExecutorPool(ip, port, executorType);
	}
}
