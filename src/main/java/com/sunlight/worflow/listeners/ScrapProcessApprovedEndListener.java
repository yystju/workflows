package com.sunlight.worflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProcessApprovedEndListener implements ExecutionListener {
	private static final long serialVersionUID = -382706658721574465L;
	private static Logger logger = LoggerFactory.getLogger(ScrapProcessApprovedEndListener.class);

	public ScrapProcessApprovedEndListener() {
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("[ScrapProcessApprovedEndListener.notify] >> {}", execution.getCurrentActivityName());
		logger.info("reviewType : {}", execution.getVariable("reviewType"));
	}
}
