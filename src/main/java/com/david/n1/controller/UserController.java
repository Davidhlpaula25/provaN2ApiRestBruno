package com.david.n1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.n1.entities.Endereco;
import com.david.n1.entities.User;
import com.david.n1.entities.DTO.UserDTO;
import com.david.n1.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Buscar todos os usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Atualizar usuário'
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userUpdateDTO) {
        try {
            User existingUser = userService.findById(id);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Atualizar os dados do usuário usando o DTO
            userUpdateDTO.updateUserFromDTO(existingUser);

            // Atualizar o endereço
            Endereco endereco = userUpdateDTO.getEndereco();
            if (endereco != null) {
                endereco.setUser(existingUser); // Relação bidirecional
                existingUser.setEndereco(endereco);
            }
            // UserDTO userDTO = new UserDTO(existingUser);
            // Salvar as alterações
            User updatedUser = userService.save(existingUser, endereco);
            return ResponseEntity.ok(updatedUser);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }
}
