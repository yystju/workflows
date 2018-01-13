package com.sunlight.worflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProcessRejectedEndListener implements ExecutionListener {
	private static final long serialVersionUID = 5327763329952684400L;
	private static Logger logger = LoggerFactory.getLogger(ScrapProcessApprovedEndListener.class);

	public ScrapProcessRejectedEndListener() {
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("[ScrapProcessRejectedEndListener.notify] >> {}", execution.getCurrentActivityName());
		logger.info("reviewType : {}", execution.getVariable("reviewType"));
	}
}
