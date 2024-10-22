package br.com.jorgelucas.todolist.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name= "tb_user") // nome da tabela no banco de dados
public class UserModel {
    /**
     * precisamos definir os  tipos de dados que cada atributo pode receber
     * String, int, long, double, float, char, boolean, Date
     * precisamos definir os modificadores de acesso
     * public, private, protected
     */
    @Id
    @GeneratedValue(generator="UUID") // geracao do tipo UUID;
    private UUID id; // identificador unico do tipo UUID chave primaria

    @Column(unique = true) // campo unico no banco de dados (não pode repetir)
    private String  username;
    private String name;
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt; // data de criação


}

