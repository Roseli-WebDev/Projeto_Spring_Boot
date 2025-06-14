package com.aulabd.bd.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cliente;
    private String status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(String cliente, String status) {
        this.cliente = cliente;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Transient
    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(item -> BigDecimal.valueOf(item.getPrecoUnitario() * item.getQuantidade()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(Produto produto, Integer quantidade, Double precoUnitario) {
        ItemPedido item = new ItemPedido(this, produto, quantidade, precoUnitario);
        this.itens.add(item);
    }

    public void removeItem(Produto produto) {
        this.itens.removeIf(item -> item.getProduto().equals(produto));
    }
}
