package com.david.n1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.n1.entities.Endereco;
import com.david.n1.entities.User;
import com.david.n1.repositories.EnderecoRepository;
import com.david.n1.repositories.UserRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).orElse(null);
    }

    public Endereco updateEndereco(Long userId, Endereco novEndereco) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }
            Endereco endereco = user.getEndereco();
            if (endereco != null) {
                endereco.setLogradouro(novEndereco.getLogradouro());
                endereco.setNumber(novEndereco.getNumber());
                endereco.setQuadra(novEndereco.getQuadra());
                endereco.setLote(novEndereco.getLote());
                endereco.setBairro(novEndereco.getBairro());
                endereco.setCep(novEndereco.getCep());
                endereco.setCidade(novEndereco.getCidade());
                endereco.setEstado(novEndereco.getEstado());

                return enderecoRepository.save(endereco);
            } else {
                novEndereco.setUser(user);
                return enderecoRepository.save(novEndereco);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar endereço: " + e.getMessage());
            return null;
        }
    }

    public Endereco createEndereco(Endereco endereco, Long userId) {
        try {
            // Verificar se o usuário existe
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }

            endereco.setUser(user); // Associar o endereço ao usuário
            return enderecoRepository.save(endereco); // Salvar e retornar o novo endereço
        } catch (Exception e) {
            System.out.println("Erro ao criar endereço: " + e.getMessage());
            return null;
        }
    }
}
