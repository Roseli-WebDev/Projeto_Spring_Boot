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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aulabd.bd.model.ItemPedido;
import com.aulabd.bd.model.Pedido;
import com.aulabd.bd.model.Produto;
import com.aulabd.bd.repository.PedidoRepository;
import com.aulabd.bd.repository.ProdutoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoRepository.findAllWithItens();
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("produtosDisponiveis", produtoRepository.findAll());
        model.addAttribute("nomePag", "Cadastrar Pedido");
        model.addAttribute("link", "/pedidos/salvar");
        model.addAttribute("valorBtn", "Cadastrar");
        return "cadastroPedido";
    }

    @PostMapping("/salvar")
    public String salvarPedido(
            @ModelAttribute Pedido pedido,
            @RequestParam(required = false, name = "produtosIds") List<Long> produtosIds,
            RedirectAttributes attributes
    ) {
        if (produtosIds != null) {
            for (Long idProduto : produtosIds) {
                Produto produto = produtoRepository.findById(idProduto).orElseThrow();
                ItemPedido item = new ItemPedido(pedido, produto, 1, produto.getPreco().doubleValue());
                pedido.getItens().add(item);
            }
        }

        pedidoRepository.save(pedido);
        attributes.addFlashAttribute("mensagemSucesso", "Pedido cadastrado com sucesso!");
        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido inválido: " + id));

        model.addAttribute("pedido", pedido);
        model.addAttribute("produtosDisponiveis", produtoRepository.findAll());
        model.addAttribute("nomePag", "Editar Pedido");
        model.addAttribute("link", "/pedidos/atualizar/" + id);
        model.addAttribute("valorBtn", "Atualizar");
        return "cadastroPedido";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarPedido(
            @PathVariable Long id,
            @ModelAttribute("pedido") Pedido pedido,
            RedirectAttributes attributes
    ) {
        pedido.setId(id);
        pedidoRepository.save(pedido);
        attributes.addFlashAttribute("mensagemSucesso", "Pedido atualizado com sucesso!");
        return "redirect:/pedidos";
    }

    @PostMapping("/deletar/{id}")
    public String deletarPedido(@PathVariable Long id, RedirectAttributes attributes) {
        pedidoRepository.deleteById(id);
        attributes.addFlashAttribute("mensagemSucesso", "Pedido excluído com sucesso!");
        return "redirect:/pedidos";
    }
}
