package com.sunlight.worflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProcessStartListener implements ExecutionListener {
	private static final long serialVersionUID = 1042252406170046150L;
	
	private static Logger logger = LoggerFactory.getLogger(ScrapProcessStartListener.class);

	public ScrapProcessStartListener() {
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("[ScrapProcessStartListener.notify] >> {}", execution.getCurrentActivityName());
		logger.info("reviewType : {}", execution.getVariable("reviewType"));
	}
}
