package com.sunlight.worflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProcessThresholdCalculationListener implements ExecutionListener {
	private static final long serialVersionUID = 1042252406170046150L;
	
	private static Logger logger = LoggerFactory.getLogger(ScrapProcessThresholdCalculationListener.class);

	public ScrapProcessThresholdCalculationListener() {
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("[ScrapProcessThresholdCalculateListener.notify] >> {}", execution.getCurrentActivityName());
		logger.info("reviewType : {}", execution.getVariable("reviewType"));
		
		boolean threshold = false;
		
		//TODO: Add the calculation here...
		
		execution.setVariable("threshold", threshold);
	}
}
