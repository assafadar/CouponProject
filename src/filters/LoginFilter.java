package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import utils.DataValidationUtils;
import utils.UserUtils;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest)req;
			String path = request.getRequestURI();
			if(path.contains("/login") || path.contains("/register") || path.contains("/activate")) {
				chain.doFilter(req, res);
			}
			else {
				Cookie [] cookies = request.getCookies();
				if(cookies!=null) {
					User user = UserUtils.getUserFromCookie(cookies);
					if(DataValidationUtils.checkPermissions(path, user.getUserType())) {
						chain.doFilter(req, res);
					}
					else {
						HttpServletResponse response = (HttpServletResponse)res;
						response.sendRedirect("/");
					}
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();

		}
	}



	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
