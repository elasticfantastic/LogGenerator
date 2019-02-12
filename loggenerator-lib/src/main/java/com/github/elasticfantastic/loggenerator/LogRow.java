package com.github.elasticfantastic.loggenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogRow implements Comparable<LogRow> {

	private String id;
	private String level;
	private LocalDateTime date;
	private String message;

	private DateTimeFormatter formatter;

	public LogRow(String id, String level, LocalDateTime date, String message) {
		this.id = id;
		this.level = level;
		this.date = date;
		this.message = message;

		this.formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("[%s] [%s] [%s] - %s", this.id, this.level, this.date.format(this.formatter),
				this.message);
	}

	@Override
	public int compareTo(LogRow o) {
		return this.date.compareTo(o.getDate());
	}

}
