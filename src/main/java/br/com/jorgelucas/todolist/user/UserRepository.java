package br.com.jorgelucas.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username); //para buscar um usuario pelo username
}