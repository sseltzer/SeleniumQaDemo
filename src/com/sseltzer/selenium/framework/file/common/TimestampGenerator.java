package com.sseltzer.selenium.framework.file.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampGenerator {

	private final String PREFIX_PATTERN = "%PRE";
	private final String TIMESTAMP_PATTERN = "%TIME";
	private final String DIR_PATTERN = PREFIX_PATTERN + "_" + TIMESTAMP_PATTERN;
	private final String TIMESTAMP_FORMAT = "yyyy.DDD.HH.mm.ss.SSS";

	private String prefix;

	public TimestampGenerator() {
		this("");
	}

	public TimestampGenerator(String prefix) {
		this.prefix = prefix;
	}

	public String generateNew() {
		return formatTimestamp(new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date()));
	}

	public String formatTimestamp(String timestamp) {
		return DIR_PATTERN.replace(PREFIX_PATTERN, prefix).replace(TIMESTAMP_PATTERN, timestamp);
	}

	public String extractTime(String name) {
		String[] pattern = DIR_PATTERN.split(TIMESTAMP_PATTERN);
		int bIndex = (pattern.length < 1) ? 0 : name.indexOf(pattern[0]) + TIMESTAMP_PATTERN.length();
		int eIndex = (pattern.length < 2) ? name.length() : name.indexOf(pattern[1]);
		return name.substring(bIndex, eIndex);
	}
}
