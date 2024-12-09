package com.david.n1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.david.n1.entities.Produto;
import com.david.n1.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    // Endpoint para adicionar produto com imagem
    @PostMapping("/adicionarProduto")
    public Produto adicionarProduto(@RequestParam String nome,
            @RequestParam String modelo,
            @RequestParam String marca,
            @RequestParam String cor,
            @RequestParam Double preco,
            @RequestParam MultipartFile imagem) throws IOException {

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setModelo(modelo);
        produto.setMarca(marca);
        produto.setCor(cor);
        produto.setPreco(preco);

        // Chama o serviço para salvar o produto e a imagem
        return produtoService.adicionarProdutoComImagem(produto, imagem);
    }

    // Endpoint para listar todos os produtos
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos(); // Retorna todos os produtos do banco
    }

    // Endpoint para buscar um produto por ID
    @GetMapping("/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id); // Retorna o produto com o ID fornecido
    }

    // Endpoint para atualizar um produto existente
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Double preco,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Produto produtoExistente = produtoService.buscarProdutoPorId(id);
        if (produtoExistente == null) {
            return null; // Produto não encontrado
        }

        // Atualizando os campos do produto, se os parâmetros forem fornecidos
        if (nome != null)
            produtoExistente.setNome(nome);
        if (modelo != null)
            produtoExistente.setModelo(modelo);
        if (marca != null)
            produtoExistente.setMarca(marca);
        if (cor != null)
            produtoExistente.setCor(cor);
        if (preco != null)
            produtoExistente.setPreco(preco);

        // Se uma nova imagem for fornecida, atualiza a imagem
        if (imagem != null && !imagem.isEmpty()) {
            produtoExistente = produtoService.atualizarProduto(id, produtoExistente, imagem);
        }

        return produtoExistente;
    }

    // Endpoint para excluir um produto
    @DeleteMapping("/{id}")
    public String excluirProduto(@PathVariable Long id) throws IOException {
        boolean sucesso = produtoService.excluirProduto(id);
        if (sucesso) {
            return "Produto excluído com sucesso!";
        } else {
            return "Produto não encontrado!";
        }
    }
}
