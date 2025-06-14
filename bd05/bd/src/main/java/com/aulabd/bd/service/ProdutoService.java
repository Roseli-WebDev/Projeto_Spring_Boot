package com.aulabd.bd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aulabd.bd.model.Produto;
import com.aulabd.bd.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public void inserirProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public void atualizarProduto(Produto produto, Long id) {
        produto.setId(id);
        produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Map<String, Object>> puxarTodosProdutos() {
        return produtoRepository.findAll().stream()
                .map(p -> {
                    Map<String, Object> produtoMap = new HashMap<>();
                    produtoMap.put("id", p.getId());
                    produtoMap.put("nome", p.getNome());
                    produtoMap.put("preco", p.getPreco());
                    return produtoMap;
                })
                .collect(Collectors.toList());
    }

    public Map<String, Object> puxarProduto(Long id) {
        return produtoRepository.findById(id)
                .map(p -> {
                    Map<String, Object> produtoMap = new HashMap<>();
                    produtoMap.put("id", p.getId());
                    produtoMap.put("nome", p.getNome());
                    produtoMap.put("preco", p.getPreco());
                    return produtoMap;
                })
                .orElse(null);
    }
}
