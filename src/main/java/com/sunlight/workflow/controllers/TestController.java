package com.sunlight.workflow.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunlight.workflow.Application;
import com.sunlight.workflow.service.WorkflowService;

@RestController
@RequestMapping("/api")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	WorkflowService workflowService;
	
	@RequestMapping("/definitions")
	public List<String> definitions() {
		return workflowService.definitions();
	}
	
	@RequestMapping("/start")
	public String start(@RequestParam(name = "key", required = true) String definitionKey, @RequestParam(required = true) String reviewType, @RequestParam(required = false) String approved, @RequestParam(required = false) String threshold) {
		logger.info("[start] vars : {}, {}, {}", reviewType, approved, threshold);
		
		HashMap<String, Object> variables = new HashMap<String, Object>();
		
		if(reviewType!= null) {
			variables.put("reviewType", reviewType);
		}
		
		if(approved != null) {
			variables.put("approved", approved);
		} else {
			variables.put("approved", "false");
		}
		
		if(threshold != null) {
			variables.put("threshold", threshold);
		} else {
			variables.put("threshold", "false");
		}
		
		return workflowService.startProcess(definitionKey, variables);
	}
	
	@RequestMapping("/next")
	public List<String> next(@RequestParam(name = "pid", required = true) String processInstanceId, @RequestParam(required = true) String reviewType, @RequestParam(required = false) String approved, @RequestParam(required = false) String threshold) {
		logger.info("[next] vars : {}, {}, {}", reviewType, approved, threshold);
		
		HashMap<String, Object> variables = new HashMap<String, Object>();
		
		if(reviewType!= null) {
			variables.put("reviewType", reviewType);
		}
		
		if(approved != null) {
			variables.put("approved", approved);
		} else {
			variables.put("approved", "false");
		}
		
		if(threshold != null) {
			variables.put("threshold", threshold);
		} else {
			variables.put("threshold", "false");
		}
		
		return  workflowService.nextProcess(processInstanceId, "user", variables);
	}
	
	
	@RequestMapping("/status")
    public void status(@RequestParam(name = "pid", required = true) String processInstanceId, HttpServletResponse response) {
		try {
			InputStream ins = workflowService.createInstanceDiagram(processInstanceId);
			
			OutputStream outs = response.getOutputStream();
			
			response.setContentType("image/png");
			
			byte[] buffer = new byte[8 * 1024 * 1024];
			
			int len = -1;
			
			while(-1 != (len = ins.read(buffer))) {
				outs.write(buffer, 0, len);
			}
			
			ins.close();
			outs.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
    }
}
