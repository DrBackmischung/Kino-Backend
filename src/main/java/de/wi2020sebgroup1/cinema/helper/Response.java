package de.wi2020sebgroup1.cinema.helper;

import org.springframework.lang.Nullable;

/**
 * Enumeration of HTTP status codes.
 *
 * <p>The HTTP status code series can be retrieved via {@link #series()}.
 *
 * @author Mathis Neunzig
 * @see ResponseSeries
 * @see <a href="https://www.iana.org/assignments/http-status-codes">HTTP Status Code Registry</a>
 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes - Wikipedia</a>
 */

public enum Response {
	
	/**
	 * {@code 100 Continue}.
	 */
	CONTINUE(100, ResponseSeries.INFORMATIONAL, "Continue"),
	/**
	 * {@code 200 OK}.
	 */
	OK(200, ResponseSeries.SUCCESSFUL, "OK"),
	/**
	 * {@code 200 Continue}.
	 */
	DELETED(200, ResponseSeries.SUCCESSFUL, "Deleted"),
	/**
	 * {@code 200 Changed}.
	 */
	CHANGED(200, ResponseSeries.SUCCESSFUL, "Changed"),
	/**
	 * {@code 201 Continue}.
	 */
	CREATED(201, ResponseSeries.SUCCESSFUL, "Created"),
	/**
	 * {@code 202 Accepted}.
	 */
	ACCEPTED(202, ResponseSeries.SUCCESSFUL, "Accepted"),
	/**
	 * {@code 307 Temporary Redirected}.
	 */
	TEMPORARY_REDIRECTED(307, ResponseSeries.REDIRECTION, "Temporary Redirected"),
	/**
	 * {@code 308 Permanent Redirected}.
	 */
	PERMANENT_REDIRECTED(308, ResponseSeries.REDIRECTION, "Permanent Redirected"),
	/**
	 * {@code 400 Bad Request}.
	 */
	BAD_REQUEST(400, ResponseSeries.CLIENT_ERROR, "Bad Request"),
	/**
	 * {@code 401 Unauthorized}.
	 */
	UNAUTHORIZED(401, ResponseSeries.CLIENT_ERROR, "Unauthorized"),
	/**
	 * {@code 403 Forbidden}.
	 */
	FORBIDDEN(403, ResponseSeries.CLIENT_ERROR, "Forbidden"),
	/**
	 * {@code 404 Not Found}.
	 */
	NOT_FOUND(404, ResponseSeries.CLIENT_ERROR, "Not Found"),
	/**
	 * {@code 406 Not Acceptable}.
	 */
	NOT_ACCEPTABLE(406, ResponseSeries.CLIENT_ERROR, "Not Acceptable"),
	/**
	 * {@code 418 Everything else}.
	 */
	I_AM_A_TEAPOT(418, ResponseSeries.CLIENT_ERROR, "I'm a teapot"),
	/**
	 * {@code 500 Internal Server Error}.
	 */
	INTERNAL_SERVER_ERROR(500, ResponseSeries.SERVER_ERROR, "Internal Server Error"),
	/**
	 * {@code 501 Not Implemented}.
	 */
	NOT_IMPLEMENTED(501, ResponseSeries.SERVER_ERROR, "Not Implemented"),
	/**
	 * {@code 503 Service Unavailable}.
	 */
	SERVICE_UNAVAILABLE(503, ResponseSeries.SERVER_ERROR, "Service Unavailable");
	
	private static final Response[] VALUES;

	static { 
		VALUES = values();
	}


	private final int value;

	private final ResponseSeries series;

	private final String reasonPhrase;

	Response(int value, ResponseSeries series, String reasonPhrase) {
		this.value = value;
		this.series = series;
		this.reasonPhrase = reasonPhrase;
	}
	
	public int value() {
		return this.value;
	}

	/**
	 * @see ResponseSeries
	 */
	public ResponseSeries series() {
		return this.series;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}
	
	public boolean is1xxInformational() {
		return (series() == ResponseSeries.INFORMATIONAL);
	}

	public boolean is2xxSuccessful() {
		return (series() == ResponseSeries.SUCCESSFUL);
	}

	public boolean is3xxRedirection() {
		return (series() == ResponseSeries.REDIRECTION);
	}
	
	public boolean is4xxClientError() {
		return (series() == ResponseSeries.CLIENT_ERROR);
	}
	
	public boolean is5xxServerError() {
		return (series() == ResponseSeries.SERVER_ERROR);
	}

	public boolean isError() {
		return (is4xxClientError() || is5xxServerError());
	}

	@Override
	public String toString() {
		return this.value + " " + name();
	}

	/**
	 * Return the {@code Response} enum constant with the specified numeric value.
	 * @param statusCode the numeric value of the enum to be returned
	 * @return the enum constant with the specified numeric value
	 * @throws IllegalArgumentException
	 */
	public static Response valueOf(int statusCode) {
		Response status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		}
		return status;
	}

	/**
	 * Resolve the given status code to an {@code Response}, if possible.
	 * @param statusCode the HTTP status code 
	 * @return the corresponding {@code Response}, or {@code null} if not found
	 */
	@Nullable
	public static Response resolve(int statusCode) {
		// Use cached VALUES instead of values() to prevent array allocation.
		for (Response status : VALUES) {
			if (status.value == statusCode) {
				return status;
			}
		}
		return null;
	}

}
