package ch.unisg.executor1.executor.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.unisg.executor1.executor.application.port.out.NotifyExecutorPoolPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotifyExecutorPoolService {

	private final NotifyExecutorPoolPort notifyExecutorPoolPort;

	public boolean notifyExecutorPool(String ip, int port, String executorType) {
		return notifyExecutorPoolPort.notifyExecutorPool(ip, port, executorType);
	}
}
