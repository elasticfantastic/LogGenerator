package com.github.elasticfantastic.loggenerator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LogRow implements Comparable<LogRow> {

	private String id;
	private String level;
	private ZonedDateTime date;
	private String message;

	public LogRow(String id, String level, ZonedDateTime date, String message) {
		this.id = id;
		this.level = level;
		this.date = date;
		this.message = message;
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

	public ZonedDateTime getDate() {
		return this.date;
	}

	public void setDate(ZonedDateTime date) {
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
		//System.out.println(date);
		String dateAsString = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.date);
		//System.out.println(dateAsString);
		return String.format("[%s] [%s] [%s] - %s", this.id, this.level, dateAsString, this.message);
	}

	@Override
	public int compareTo(LogRow o) {
		return this.date.compareTo(o.getDate());
	}

}
