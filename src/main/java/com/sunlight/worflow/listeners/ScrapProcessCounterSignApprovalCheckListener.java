package com.sunlight.worflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProcessCounterSignApprovalCheckListener implements ExecutionListener {
	private static final long serialVersionUID = 1042252406170046150L;
	
	private static Logger logger = LoggerFactory.getLogger(ScrapProcessCounterSignApprovalCheckListener.class);

	public ScrapProcessCounterSignApprovalCheckListener() {
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("[ScrapProcessCounterSignApprovalCheckListener.notify] >> {}", execution.getCurrentActivityName());
		logger.info("reviewType : {}", execution.getVariable("reviewType"));
		
		boolean approved = false;
		
		//TODO: Add the calculation here...
		
		execution.setVariable("approved", approved);
	}
}
