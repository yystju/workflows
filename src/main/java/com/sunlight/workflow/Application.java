package com.sunlight.workflow;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {
        return args -> {
        		String definitionId = "scrapping_approval_process";
        		long count = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).count();
        		
        		if(count == 0) {
        			repositoryService.createDeployment().name(definitionId).addClasspathResource("diagrams/scrapping_approval.bpmn").deploy();
        		}
        		
        		logger.info("Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
        		logger.info("Number of tasks : " + taskService.createTaskQuery().count());
        		
//        		String[] beanNames = ctx.getBeanDefinitionNames();
//              Arrays.sort(beanNames);
//              for (String beanName : beanNames) {
//              		logger.info("beanName : {}", beanName);
//              }
        };

    }
}