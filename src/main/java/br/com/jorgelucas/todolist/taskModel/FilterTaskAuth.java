package br.com.jorgelucas.todolist.taskModel;



import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.jorgelucas.todolist.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var server = request.getServletPath();

        if (server.startsWith("/tasks/")) {
            //pegar o meu usuario e senha
            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim(); // ele vai pegar a partir do basic e vai remover os espaços

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);
            System.out.println("authorization"); // autorização do usuario
            System.out.println(authEncoded); // ele serve para codificar o usuario e senha
            System.out.println(authDecode); // ele serve para decodificar o usuario e senha

            //["jorgelucas" ,"123456"]
            String[] credentials = authString.split(":"); // ele vai separar o usuario e senha
            String username = credentials[0];
            String password = credentials[1];
            System.out.println(username);
            System.out.println(password);

            //validar usuario e senha
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.setStatus(401);
            } else {
                //validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);// vai retornar um true ou false
                } else {
                    response.setStatus(401);
                    response.getWriter().write("user sem autorização");
                    return;
                }
                filterChain.doFilter(request, response);
                // segue o fluxo normal
            }
        }
    }
}