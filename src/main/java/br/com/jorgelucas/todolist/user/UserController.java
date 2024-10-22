package br.com.jorgelucas.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * modificadorres
 * public // visivel para todos
 * private // visivel apenas para a classe
 * protected // visivel para a classe e suas subclasses
 */
@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * tipos de retorno para o metodo
     * void // sem retorno
     * String // retorna uma string
     * List<String> // retorna uma lista de strings
     * User // retorna um objeto do tipo User
     * List<User> // retorna uma lista de objetos do tipo User
     * ResponseEntity<User> // retorna um objeto do tipo User com status http
     * ResponseEntity<List<User>> // retorna uma lista de objetos do tipo User com status http
     * char // retorna um caractere
     * int // retorna um inteiro
     * long // retorna um long
     * double // retorna um double
     * date // retorna uma data
     */
    @PostMapping("/") // por que o post? porque estamos criando um novo usuario, qual a função? criar
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        // logica para retornar um erro
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exists");
        }
        var passwordHashred =BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()); // gera uma senha criptografada
            userModel.setPassword(passwordHashred);
        // adiciona o usuario no banco de dados
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}