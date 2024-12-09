package com.david.n1.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.david.n1.entities.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class PagamentoController {

    @PostMapping("/pagamento")
    public String pagamento(@RequestParam("carrinho") String carrinhoJson, Model model) {
        // Converte o JSON do carrinho para uma lista de produtos
        List<Produto> carrinho = new Gson().fromJson(carrinhoJson, new TypeToken<List<Produto>>() {
        }.getType());

        // Calcula o total do carrinho
        double total = carrinho.stream().mapToDouble(Produto::getPreco).sum();

        // Adiciona o carrinho e o total ao modelo
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", total);

        return "pagamento"; // Nome da view que será renderizada
    }
}
