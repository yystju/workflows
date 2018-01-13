package com.sunlight.workflow.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	RepositoryService repositoryService;
	
	private DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator(1.0);
	
	public InputStream createInstanceDiagram(String processInstanceId) {
		String definitionId = null;
		List<String> activities = null;

		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

		if (pi == null) {
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

			definitionId = hpi.getProcessDefinitionId();

			List<HistoricActivityInstance> htasks = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().desc().list();

			activities = htasks.size() > 0 ? Arrays.asList(new String[] { htasks.get(0).getActivityId() }): Arrays.asList(new String[] {});
		} else {
			definitionId = repositoryService.createProcessDefinitionQuery().processDefinitionKey(pi.getProcessDefinitionKey()).singleResult().getId();

			activities = runtimeService.getActiveActivityIds(processInstanceId);
		}

		BpmnModel model = repositoryService.getBpmnModel(definitionId);

		InputStream is = generator.generateDiagram(model, "png", activities);

		return is;
	}
	
	public List<String> definitions() {
		List<String> ret = new ArrayList<>();
		
		List<ProcessDefinition> pds = repositoryService.createProcessDefinitionQuery().active().orderByProcessDefinitionKey().asc().list();
		
		for(ProcessDefinition pd : pds) {
			ret.add(pd.getKey());
		}
		
		return ret;
	}
	
	
	public String startProcess(String definitionKey, Map<String, Object> variables) {
		return runtimeService.startProcessInstanceByKey(definitionKey, variables).getId();
	}
	
	public List<String> nextProcess(String pid, String userId, Map<String, Object> variables) {
		List<String> ret = new ArrayList<>();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pid).active().orderByTaskId().asc().list();
		
		for(Task task : tasks) {
			taskService.claim(task.getId(), userId);
			taskService.complete(task.getId(), variables);
			ret.add(task.getId());
		}
		
		return ret;
	}
}
