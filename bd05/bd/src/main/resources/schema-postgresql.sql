CREATE TABLE IF NOT EXISTS produtos (
    id BIGSERIAL PRIMARY KEY, 
    nome VARCHAR(255) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL 
);

CREATE TABLE IF NOT EXISTS pedidos (
    id BIGSERIAL PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL, 
    status VARCHAR(50) NOT NULL 
);


CREATE TABLE IF NOT EXISTS pedido_produto (
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    preco_unitario NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE CASCADE
);

