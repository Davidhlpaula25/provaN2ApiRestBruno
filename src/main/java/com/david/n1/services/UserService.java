package com.david.n1.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.n1.entities.Endereco;
import com.david.n1.entities.User;
import com.david.n1.entities.DTO.UserDTO;
import com.david.n1.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User validUser(String email, String password) {
        User usuario = userRepository.findByEmailAndPassword(email, password);

        if (usuario != null) {
            // Captura o nome completo e extrai o primeiro nome
            String nomeCompleto = usuario.getName();
            String[] nomes = nomeCompleto.split(" ");
            String primeiroNome = nomes[0];

            // Armazena o primeiro nome no usuário para ser acessado no controlador
            usuario.setPrimeiroNome(primeiroNome); // Você precisa ter um método setter para isso no User
        }

        return usuario;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado."));
    }

    @Transactional
    public User save(User user, Endereco endereco) {
        // Verifica se o e-mail já existe
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return null; // E-mail já existe, então não salva o usuário
        }

        // Validações básicas
        if (endereco == null) {
            return null; // Endereço não pode ser nulo
        }

        // Relacionar User e Endereco
        endereco.setUser(user); // Define o usuário no endereço
        user.setEndereco(endereco); // Define o endereço no usuário

        // Salva o usuário (com o CascadeType.ALL, o endereço será salvo junto)
        User savedUser = userRepository.save(user);

        System.out.println("Usuário salvo com sucesso: " + savedUser);

        return savedUser;
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado."));
        userRepository.delete(user);
    }

    @Transactional
    public User update(Long id, UserDTO userUpdateDTO) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado."));

            if (existingUser != null) {
                // Atualiza o usuário com os dados do DTO
                userUpdateDTO.updateUserFromDTO(existingUser);
                return userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("Usuário não encontrado para atualizar.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return null;
        }
    }
}
