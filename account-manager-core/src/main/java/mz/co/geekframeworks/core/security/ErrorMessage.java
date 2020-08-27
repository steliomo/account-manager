/**
 *
 */
package mz.co.geekframeworks.core.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Stélio Moiane
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorMessage {

	private int statusCode;

	private String message;

	private String developerMessage;

	public ErrorMessage() {
	}

	public ErrorMessage(final int statusCode, final String message, final String developerMessage) {
		this.statusCode = statusCode;
		this.message = message;
		this.developerMessage = developerMessage;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getMessage() {
		return this.message;
	}

	public String getDeveloperMessage() {
		return this.developerMessage;
	}

}
