package com.github.elasticfantastic.loggenerator.server.thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.utility.MessageUtility;

public class ThreadTest implements Runnable {

	private static final String LOG_FILE = "log_server.txt";

	private LogGenerator generator;

	public ThreadTest(LogGenerator generator) {
		this.generator = generator;
	}

	@Override
	public void run() {
		while (true) {
			String level = this.generator.getRandomLevel("WARN", "DEBUG");

			Map<String, Object> inputs = new HashMap<>();
			inputs.put("id", "Server");
			inputs.put("level", level);
			inputs.put("message", MessageUtility.getMessage(level, null));

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
				LogRow logRow = this.generator.getLog(LocalDateTime.now(), inputs);
				System.out.println(logRow);
				bw.write(logRow + System.getProperty("line.separator"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
