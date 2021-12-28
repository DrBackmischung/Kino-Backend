package de.wi2020sebgroup1.cinema.helper;

import org.springframework.lang.Nullable;

public enum ResponseSeries {

	INFORMATIONAL(1),
	SUCCESSFUL(2),
	REDIRECTION(3),
	CLIENT_ERROR(4),
	SERVER_ERROR(5),
	CUSTOM_ERROR(9);

	private final int value;

	ResponseSeries(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}

	/**
	 * Resolve the given status code to an {@code ResponseSeries}, if possible.
	 * @param statusCode the HTTP status code 
	 * @return the corresponding {@code ResponseSeries}, or {@code null} if not found
	 */
	@Nullable
	public static ResponseSeries resolve(int statusCode) {
		int seriesCode = statusCode / 100;
		for (ResponseSeries series : values()) {
			if (series.value == seriesCode) {
				return series;
			}
		}
		return null;
	}

	/**
	 * Return the {@code ResponseSeries} enum constant for the supplied status code.
	 * @param statusCode the HTTP status code 
	 * @return the {@code ResponseSeries} enum constant
	 * @throws IllegalArgumentException
	 */
	public static ResponseSeries valueOf(int statusCode) {
		ResponseSeries series = resolve(statusCode);
		if (series == null) {
			throw new IllegalArgumentException("No matching constant for <" + statusCode + ">");
		}
		return series;
	}

}
