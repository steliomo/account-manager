/**
 *
 */
package mz.co.geekframeworks.core.user.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Stélio Moiane
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;

	private String fullName;

	private String username;

	private String email;

	public UserDTO() {
	}

	public UserDTO(final User user) {
		this.uuid = user.getUsername();
		this.fullName = user.getFullName();
		this.username = user.getUsername();
		this.email = user.getEmail();
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}
}
