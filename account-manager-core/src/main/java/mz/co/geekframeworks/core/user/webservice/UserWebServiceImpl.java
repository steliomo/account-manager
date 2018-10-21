/**
 *
 */
package mz.co.geekframeworks.core.user.webservice;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.JResponse;

import mz.co.geekframeworks.core.security.AuthenticationService;
import mz.co.geekframeworks.core.user.model.User;
import mz.co.geekframeworks.core.user.model.UserContextFactory;
import mz.co.geekframeworks.core.user.service.UserQueryService;
import mz.co.geekframeworks.core.user.service.UserService;
import mz.co.geekframeworks.core.util.UserContextUtil;
import mz.co.mozview.frameworks.core.exception.BusinessException;
import mz.co.mozview.frameworks.core.webservices.adapter.Entry;
import mz.co.mozview.frameworks.core.webservices.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@Service(UserWebService.NAME)
@Path("users")
public class UserWebServiceImpl implements UserWebService {

	@Inject
	private UserQueryService userQueryService;

	@Inject
	private UserService userService;

	@Inject
	private AuthenticationService authenticationService;

	@Override
	public Response findUserBySessionId(final String sessionId) throws BusinessException {
		UserContext userContext = new UserContext();

		try {
			final User user = this.userQueryService.fetchUserByApplicationCodeAndUnitCodeAndUsername(userContext,
			        UserContextUtil.getApplicationCode(sessionId), UserContextUtil.getUnitCode(sessionId),
			        UserContextUtil.getUsername(sessionId));

			userContext = UserContextFactory.getUsercoContext(user);
		}
		catch (final NoResultException e) {
			return Response.noContent().build();
		}

		return Response.ok(userContext).build();
	}

	@Override
	public JResponse<UserContext> updatePassword(final UserContext userContext) throws BusinessException {

		final User user = this.userQueryService.findUserByUuid(userContext.getUuid());
		user.setPassword(userContext.getPassword());
		user.setReset(userContext.getPropertyValue(Entry.RESET));

		this.userService.updateUserPassword(userContext, user);

		return JResponse.ok(userContext).build();
	}

	@Override
	public JResponse<UserContext> login(final UserContext userContext) throws BusinessException {

		final UserContext context = new UserContext();

		final Authentication authentication = this.authenticationService.getAuthentication(userContext.getUsername(),
		        userContext.getPassword());

		final User authenticated = (User) authentication.getPrincipal();

		context.setUsername(authenticated.getUsername());
		context.setFullName(authenticated.getFullName());
		context.setEmail(authenticated.getEmail());
		context.setSessionId(authenticated.getSessionId());
		context.setAccountNonExpired(authenticated.isAccountNonExpired());
		context.setAccountNonLocked(authenticated.isAccountNonLocked());
		context.setCredentialsNonExpired(authenticated.isCredentialsNonExpired());
		context.setEnabled(authenticated.isEnabled());
		context.setUuid(authenticated.getUuid());

		return JResponse.ok(context).build();
	}

	@Override
	public JResponse<User> createUser(final UserContext context) throws BusinessException {

		final User user = new User();
		user.setFullName(context.getFullName());
		user.setEmail(context.getEmail());
		user.setUsername(context.getUsername());
		user.setPassword(context.getPassword());
		user.setUuid(context.getPropertyValue(Entry.UUID));

		this.userService.createUser(context, user);

		return JResponse.ok(user).build();
	}

	@Override
	public JResponse<User> loadUserByUsername(final String username) throws BusinessException {
		final User user = (User) this.userQueryService.loadUserByUsername(username);
		return JResponse.ok(user).build();
	}
}
