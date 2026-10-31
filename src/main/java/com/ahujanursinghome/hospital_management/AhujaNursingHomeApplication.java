package com.ahujanursinghome.hospital_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ahujanursinghome.hospital_management.BankAccount.BankcAccountProcess;

@SpringBootApplication
@EnableScheduling
public class AhujaNursingHomeApplication extends SpringBootServletInitializer {
	BankcAccountProcess sbi = new BankcAccountProcess();
	Runnable task = new Runnable() {
		@Override
		public void run() {
			Thread t = new Thread(task, "Thread 1");
			Thread t1 = new Thread(task, "Thread 2");
			t1.start();
			t.start();
		};
	};

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AhujaNursingHomeApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(AhujaNursingHomeApplication.class, args);
	}

}
