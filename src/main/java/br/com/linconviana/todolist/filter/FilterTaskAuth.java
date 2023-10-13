package br.com.linconviana.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.linconviana.todolist.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		var url = request.getRequestURI().split("/")[1];
		//if(url.contains("tasks")) {
		var servletPath = request.getServletPath();
		
		if(servletPath.startsWith("/tasks")) {
			
			var authorization = request.getHeader("Authorization");
			
			var authEncoded = authorization.substring("Basic".length()).trim();
			
			byte[] authDecode = Base64.getDecoder().decode(authEncoded);
			
			var authString = new String(authDecode);
			
			String[] credentials = authString.split(":");
			var useranme = credentials[0];
			var password = credentials[1]; 
			
			var user = userRepository.findByUserName(useranme);
			if(user == null) {
				response.sendError(401, "Usuario sem autorização");
			} else {
				
				var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
				if(passwordVerify.verified) {
					request.setAttribute("user", user);
					filterChain.doFilter(request, response);
				} else {
					response.sendError(401, "Usuario sem autorização");
				}
			}
		} else {
			
			filterChain.doFilter(request, response);
		}
	}
}
