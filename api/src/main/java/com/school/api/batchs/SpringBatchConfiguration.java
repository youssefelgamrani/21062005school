package com.school.api.batchs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

	@Autowired
	private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory steps;
	
	@Bean
	protected Step readLines() {
		return steps
				.get("readLines")
				.tasklet(new LinesReader())
				.build();
	}
	
	protected Step processLines() {
		return steps
				.get("processLines")
				.tasklet(new LinesProcessor())
				.build();
	}
	protected Step writeLines() {
		return steps
				.get("wireLines")
				.tasklet(new LinesWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return jobs
				.get("ETL-file")
				.start(readLines())
				.next(processLines())
				.next(writeLines())
				.build();
	}
	
//	@Bean
//	public Job job(JobBuilderFactory jobBuilderFactory,
//			StepBuilderFactory stepBuilderFactory) {
//		
//		Step step = stepBuilderFactory.get("ETL-file-load")
//				     .chunk(100);
//		
//		return jobBuilderFactory.get("ETL-Load")
//		   .incrementer(new RunIdIncrementer())
//		   .start(step)
//		   .build();
//	}
}

