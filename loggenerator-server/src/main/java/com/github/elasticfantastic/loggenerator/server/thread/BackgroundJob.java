package com.github.elasticfantastic.loggenerator.server.thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.core.LogGenerator;
import com.github.elasticfantastic.loggenerator.core.LogRow;
import com.github.elasticfantastic.loggenerator.core.utility.ParameterContainer;

public class BackgroundJob implements Runnable {

	private LogGenerator generator;

	public BackgroundJob(LogGenerator generator) {
		this.generator = generator;
	}

	@Override
	public void run() {
		String logFile = ParameterContainer.getParameter("logFile");
		int millisToSleep = 9423;
		while (true) {
			String level = this.generator.getRandomLevel("WARN", "DEBUG");

			Map<String, Object> inputs = new HashMap<>();
			inputs.put("id", ParameterContainer.getParameter("id"));
			inputs.put("level", level);

			LogRow logRow = null;
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
				logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
				System.out.println(logRow);
				bw.write(logRow + System.getProperty("line.separator"));

				Thread.sleep(millisToSleep);
			} catch (IOException | InterruptedException e) {
				inputs.put("level", "ERROR");
				inputs.put("message", e.getMessage());

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
					logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
					System.out.println(logRow);
					bw.write(logRow + System.getProperty("line.separator"));

					Thread.sleep(millisToSleep);
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
