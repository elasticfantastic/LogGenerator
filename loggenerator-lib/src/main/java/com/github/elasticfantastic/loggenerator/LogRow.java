package com.github.elasticfantastic.loggenerator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Encoder;

import com.github.elasticfantastic.loggenerator.utility.Base64Utility;

public class LogRow implements Comparable<LogRow> {

    private String id;
    private String level;
    private ZonedDateTime date;
    private String payload;
    private String message;

    private Encoder encoder;

    private LogRow() {
        this.encoder = Base64.getEncoder();
    }

    public LogRow(String id, String level, ZonedDateTime date, String message) {
        this();
        this.id = id;
        this.level = level;
        this.date = date;
        this.message = message;

        // Default payload is the message in a JSON object (base 64 encoded)
        String json = "{\"text\" : \"" + this.message + "\"}";
        this.payload = this.encoder.encodeToString(json.getBytes());
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        if (!Base64Utility.isEncoded(payload)) {
            this.payload = new String(this.encoder.encodeToString(payload.getBytes()));
        } else {
            this.payload = payload;
        }
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String dateAsString = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.date);
        return String.format("[%s] [%s] [%s] [%s] - %s", this.id, this.level, dateAsString, this.payload, this.message);
    }

    @Override
    public int compareTo(LogRow o) {
        return this.date.compareTo(o.getDate());
    }

}
