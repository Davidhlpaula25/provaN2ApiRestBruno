package com.david.n1.entities.DTO;

import java.time.LocalDate;

import com.david.n1.entities.Endereco;
import com.david.n1.entities.User;

public class UserDTO {
    private String name;
    private String email;
    private String password;
    private LocalDate birthDate;
    private Endereco endereco; // reutilizar a entidade Endereco

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.birthDate = user.getBirthDate();
        this.endereco = user.getEndereco();
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setBirthDate(this.birthDate);
        user.setEndereco(this.endereco); // Caso o endereço esteja presente
        return user;
    }

    public void updateUserFromDTO(User existingUser) {
        existingUser.setName(this.name);
        existingUser.setEmail(this.email);
        existingUser.setPassword(this.password);
        existingUser.setBirthDate(this.birthDate);

        if (this.endereco != null) {
            if (existingUser.getEndereco() == null) {
                existingUser.setEndereco(this.endereco);
            } else {
                // Atualiza os campos do endereço existente
                existingUser.getEndereco().setLogradouro(this.endereco.getLogradouro());
                existingUser.getEndereco().setNumber(this.endereco.getNumber());
                existingUser.getEndereco().setQuadra(this.endereco.getQuadra());
                existingUser.getEndereco().setLote(this.endereco.getLote());
                existingUser.getEndereco().setBairro(this.endereco.getBairro());
                existingUser.getEndereco().setCep(this.endereco.getCep());
                existingUser.getEndereco().setCidade(this.endereco.getCidade());
                existingUser.getEndereco().setEstado(this.endereco.getEstado());
            }
        }
    }
}
