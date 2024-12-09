package com.david.n1.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.david.n1.entities.Produto;
import com.david.n1.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    private final Path rootLocation = Path.of("src/main/resources/static/images");

    public Produto adicionarProdutoComImagem(Produto produto, MultipartFile imagem) throws IOException {
        // Salvar a imagem no diretório 'static/images'
        if (imagem != null && !imagem.isEmpty()) {
            String imagemNome = imagem.getOriginalFilename();
            Path destino = rootLocation.resolve(imagemNome);
            Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            // Salvar o nome da imagem no banco de dados
            produto.setImagem(imagemNome);
        }

        // Salvar o produto com a imagem no banco
        return produtoRepository.save(produto);
    }

    // Listar todos os produtos
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Buscar produto por ID
    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    // Atualizar um produto existente
    public Produto atualizarProduto(Long id, Produto produtoAtualizado, MultipartFile novaImagem) throws IOException {
        Produto produtoExistente = produtoRepository.findById(id).orElse(null);
        if (produtoExistente == null) {
            return null; // Produto não encontrado
        }

        // Atualizar os dados do produto
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPreco(produtoAtualizado.getPreco());

        // Se a nova imagem for fornecida, salva a imagem
        if (novaImagem != null && !novaImagem.isEmpty()) {
            // Exclui a imagem antiga, se houver
            Path imagemAntiga = rootLocation.resolve(produtoExistente.getImagem());
            if (Files.exists(imagemAntiga)) {
                Files.delete(imagemAntiga); // Deleta a imagem antiga
            }

            // Salva a nova imagem
            String novaImagemNome = novaImagem.getOriginalFilename();
            Path destino = rootLocation.resolve(novaImagemNome);
            Files.copy(novaImagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            produtoExistente.setImagem(novaImagemNome); // Atualiza o nome da imagem
        }

        return produtoRepository.save(produtoExistente); // Atualiza o produto no banco
    }

    // Excluir um produto
    public boolean excluirProduto(Long id) throws IOException {
        Produto produtoExistente = produtoRepository.findById(id).orElse(null);
        if (produtoExistente == null) {
            return false; // Produto não encontrado
        }

        // Excluir a imagem associada ao produto
        Path imagemArquivo = rootLocation.resolve(produtoExistente.getImagem());
        if (Files.exists(imagemArquivo)) {
            Files.delete(imagemArquivo); // Deleta a imagem do sistema
        }

        // Excluir o produto do banco de dados
        produtoRepository.delete(produtoExistente);
        return true; // Produto excluído com sucesso
    }
}
