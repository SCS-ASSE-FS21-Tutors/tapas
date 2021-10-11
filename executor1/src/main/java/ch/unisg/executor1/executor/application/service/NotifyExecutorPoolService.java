package ch.unisg.executor1.executor.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.unisg.executor1.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executor1.executor.domain.ExecutorType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotifyExecutorPoolService {

	private final NotifyExecutorPoolPort notifyExecutorPoolPort;

	public boolean notifyExecutorPool(String ip, int port, ExecutorType executorType) {
		return notifyExecutorPoolPort.notifyExecutorPool(ip, port, executorType);
	}
}
