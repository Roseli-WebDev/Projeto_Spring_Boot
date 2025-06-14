package com.aulabd.bd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aulabd.bd.model.Produto;
import com.aulabd.bd.repository.ProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtos);
        return "lista";
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("nomePag", "Cadastro de Produto");
        model.addAttribute("valorBtn", "Cadastrar Novo Produto");
        model.addAttribute("link", "/produtos/salvar");
        return "cadastroProduto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto, RedirectAttributes attributes) {
        produtoRepository.save(produto);
        attributes.addFlashAttribute("mensagemSucesso", "Produto cadastrado com sucesso!");
        return "redirect:/produtos/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido:" + id));
        model.addAttribute("produto", produto);
        model.addAttribute("nomePag", "Editar Produto");
        model.addAttribute("valorBtn", "Salvar Alterações");
        model.addAttribute("link", "/produtos/atualizar/" + id);
        return "cadastroProduto";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProduto(@PathVariable Long id, @ModelAttribute Produto produto, RedirectAttributes attributes) {
        produto.setId(id);
        produtoRepository.save(produto);
        attributes.addFlashAttribute("mensagemSucesso", "Produto atualizado com sucesso!");
        return "redirect:/produtos/listar";
    }

    @PostMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id, RedirectAttributes attributes) {
        produtoRepository.deleteById(id);
        attributes.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso!");
        return "redirect:/produtos/listar";
    }
}
