package com.david.n1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.david.n1.services.ProdutoService;

@Controller
public class VendaController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/vendas")
    public String paginaDeVendas(Model model) {
        String primeiroNome = (String) model.getAttribute("usuarioLogado");
        model.addAttribute("primeiroNome", primeiroNome);

        model.addAttribute("produtos", produtoService.listarProdutos()); // Recupera
        return "vendas"; // Retorna a página 'vendas.html'
    }

}
