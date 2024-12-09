package com.david.n1.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotNull(message = "Número é obrigatório")
    private Integer number;

    // Quadra e lote é opcional
    private String quadra;
    private String lote;

    @NotNull(message = "Bairro é obrigatório")
    private String bairro;

    @NotNull(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;

    @NotNull(message = "Cidade é obrigatória")
    private String cidade;

    @NotNull(message = "Estado é obrigatório")
    private String estado;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private User user;

    public Endereco() {
    }

    public Endereco(Long id, String logradouro, Integer number, String quadra, String lote, String bairro, String cep,
            String cidade, String estado, User user) {
        this.id = id;
        this.logradouro = logradouro;
        this.number = number;
        this.quadra = quadra;
        this.lote = lote;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Endereco [id=" + id + ", logradouro=" + logradouro + ", number=" + number + ", quadra=" + quadra
                + ", lote=" + lote + ", bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade + ", estado=" + estado
                + "]";
    }
}
