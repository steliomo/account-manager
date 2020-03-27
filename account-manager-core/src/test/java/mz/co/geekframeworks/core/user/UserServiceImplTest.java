package mz.co.geekframeworks.core.user;

import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import mz.co.geekframeworks.core.framework.fixturefactory.util.FixtureFactoryConstants;
import mz.co.geekframeworks.core.framework.test.AbstractSpringContextTests;
import mz.co.geekframeworks.core.framework.test.util.TestUtils;
import mz.co.geekframeworks.core.user.dao.UserDAO;
import mz.co.geekframeworks.core.user.model.User;
import mz.co.geekframeworks.core.user.service.UserService;
import mz.co.mozview.frameworks.core.exception.BusinessException;
import mz.co.mozview.frameworks.core.fixtureFactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class UserServiceImplTest extends AbstractSpringContextTests {

	@Inject
	private UserService userService;

	@Inject
	private UserDAO userDAO;

	@Inject
	private PasswordEncoder passwordEncoder;

	private User user;

	@Override
	public void setUp() {
		this.user = EntityFactory.gimme(User.class, FixtureFactoryConstants.VALID_OBJECT);
		this.user.setEmail(this.user.getEmail() + "" + this.randomicInt());
	}

	@Test
	public void shouldCreateUser() throws BusinessException {
		this.userService.createUser(this.getUserContext(), this.user);

		TestUtils.assertCreation(this.user);
	}

	@Test
	public void shouldUpdateUser() throws BusinessException {
		User createdUser = this.userService.createUser(this.getUserContext(), this.user);

		createdUser.setPassword("stelioklesio");
		this.passwordEncoder.encode(createdUser.getPassword());

		this.userService.updateUser(this.getUserContext(), createdUser);

		TestUtils.assertUpdate(this.user);

	}

	@Test
	public void shouldDeleteUser() throws BusinessException {
		User createdUser = this.userService.createUser(this.getUserContext(), this.user);

		this.userService.deleteUser(this.getUserContext(), createdUser);

		assertNull(this.userDAO.findById(createdUser.getId()));
	}

	@Test
	public void shouldUpdateUserPassword() throws BusinessException {

		User createdUser = this.userService.createUser(this.getUserContext(), this.user);

		String newPassword = "stelio";

		createdUser.setPassword(newPassword);

		this.userService.updateUserPassword(this.getUserContext(), createdUser);

		TestUtils.assertUpdate(createdUser);
	}
}
