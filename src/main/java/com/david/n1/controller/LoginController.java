package com.david.n1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.david.n1.entities.User;
import com.david.n1.services.ProdutoService;
import com.david.n1.services.UserService;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/login")
    public String login() {
        return "login"; // O nome do arquivo HTML sem a extensão
    }

    // @PostMapping("/confirmarLogin")
    // public String confirmarLogin(String email, String password,
    // RedirectAttributes redirect, Model model) {

    // User usuarioLogado = userService.validUser(email, password);

    // if (usuarioLogado != null) {
    // // menssagem de sucesso
    // redirect.addFlashAttribute("mensagem", "Logado com sucesso!");
    // return "redirect:/vendas";
    // } else {
    // // menssagem de erro
    // redirect.addFlashAttribute("mensagemErro", "Erro ao logar Credenciais
    // inválidas!");
    // return "redirect:/login";
    // }
    // }
    @PostMapping("/confirmarLogin")
    public String confirmarLogin(String email, String password, RedirectAttributes redirect, Model model) {
        User usuarioLogado = userService.validUser(email, password);

        if (usuarioLogado != null) {
            // Mensagem de sucesso
            redirect.addFlashAttribute("mensagem", "Logado com sucesso!");

            // Captura o primeiro nome
            String nomeCompleto = usuarioLogado.getName();
            String[] nomes = nomeCompleto.split(" ");
            String primeiroNome = nomes[0];

            // Passa a variável do primeiro nome no model
            model.addAttribute("primeiroNome", primeiroNome);

            // Passa os produtos no model
            model.addAttribute("produtos", produtoService.listarProdutos());

            return "vendas"; // Retorna a página 'vendas.html' com os dados
        } else {
            // Mensagem de erro
            redirect.addFlashAttribute("mensagemErro", "Erro ao logar. Credenciais inválidas!");
            return "redirect:/login"; // Redireciona para a página de login
        }
    }

}
