CREATE TABLE usuario (
                         doc VARCHAR(20) PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         user_role VARCHAR(20) NOT NULL,
                         password VARCHAR(250) NOT NULL,
                         data_nasc DATE NOT NULL,
                         CONSTRAINT ck_data_nasc CHECK (data_nasc <= CURRENT_DATE - INTERVAL '18 YEARS')
    );

CREATE TABLE categoria (
                           id SERIAL PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL
);

CREATE TABLE produto (
                         id SERIAL PRIMARY KEY,
                         categoria_id INTEGER,
                         nome VARCHAR(100) NOT NULL,
                         descricao VARCHAR(500),
                         quantidade INTEGER NOT NULL,
                         preco DECIMAL(10, 2) NOT NULL,
                         marca VARCHAR(50),
                         quantidade_vendas INTEGER DEFAULT 0,
                         constraint fk_categoria_id FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE SET NULL
);


CREATE TABLE compra (
                        nota_fiscal VARCHAR PRIMARY KEY,
                        usuario_doc VARCHAR(20),
                        status_compra VARCHAR(20) NOT NULL,
                        preco_total DECIMAL(10, 2) NOT NULL,
                        data_compra DATE,
                        constraint fk_usuario_doc FOREIGN KEY (usuario_doc) REFERENCES usuario(doc) ON DELETE SET NULL
);


CREATE TABLE compra_item (
                             id SERIAL PRIMARY KEY,
                             compra_nota_fiscal VARCHAR,
                             produto_id INTEGER,
                             quantidade_item INTEGER,
                             constraint fk_compra_nota_fiscal FOREIGN KEY (compra_nota_fiscal) REFERENCES compra(nota_fiscal) ON DELETE CASCADE,
                             constraint fk_produto_id FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE SET NULL
);


CREATE TABLE subcategoria (
                              id SERIAL PRIMARY KEY,
                              nome VARCHAR(100) NOT NULL UNIQUE,
                              categoria_principal_id INTEGER NOT NULL,
                              constraint fk_categoria_id FOREIGN KEY (categoria_principal_id) REFERENCES categoria(id) ON DELETE CASCADE
);



CREATE TABLE subcategoria_produto (
                                      id SERIAL PRIMARY KEY,
                                      subcategoria_id INTEGER,
                                      produto_id INTEGER,
                                      constraint fk_subcategoria_id FOREIGN KEY (subcategoria_id) REFERENCES subcategoria(id) on delete CASCADE,
                                      constraint fk_produto_id FOREIGN KEY (produto_id) REFERENCES produto(id) on delete CASCADE
);



select * from subcategoria_produto;

INSERT INTO usuario (doc, nome, user_role, password, data_nasc) VALUES('99999999999', 'Kaster', 'ADMIN', 'password123', '1990-01-01');

INSERT INTO categoria (nome) VALUES ('WHISKY'), ('VINHO'), ('LICOR'), ('VODKA'), ('GIN'), ('CHAMPGNE');


INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('BLENDED MALT', 1);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('BLENDED SCOTCH', 1);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('BOURBON', 1);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('SINGLE MALT', 1);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('WHISKY JAPÃO', 1);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('BRANCO', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('BRANCO DOCE', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('TINTO', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('ROSE', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('PORTO', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('DOCE', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('SECO', 2);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('COQUETEL', 3);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('APERITIVO', 3);



SELECT Produto.nome AS produto, Categoria.nome AS categoria
FROM Produto
         INNER JOIN Categoria ON Produto.categoria_id = Categoria.id;

SELECT p.nome, p.preco, p.quantidade, c.nome, p.descricao
FROM produto p
         JOIN categoria c ON p.categoria_id = c.id
WHERE p.preco BETWEEN 400.00 AND 500.00;

SELECT sc.nome
FROM subcategoria sc
         JOIN subcategoria_produto sp ON sc.id = sp.subcategoria_id
         JOIN produto p ON p.id = sp.produto_id
WHERE p.id = 6;

SELECT *
FROM produto p
         JOIN categoria c ON p.categoria_id = c.id
WHERE p.nome ILIKE '%vodka%'
OR p.descricao ILIKE '%vodka%'
OR p.marca ILIKE '%vodka%'
OR c.nome ILIKE '%vodka%';

SELECT produto.id, produto.nome AS produto, produto.quantidade_vendas
FROM produto
ORDER BY quantidade_vendas DESC;

SELECT p.id, p.nome, SUM(ci.quantidade_item) AS total_vendido,
       (SUM(ci.quantidade_item) * p.preco) AS total_receita
FROM produto p
         JOIN compra_item ci ON p.id = ci.produto_id
         JOIN compra c ON ci.compra_nota_fiscal = c.nota_fiscal
WHERE c.data_compra BETWEEN '2024-08-30' AND '2024-09-01'
GROUP BY p.id, p.nome
ORDER BY total_receita DESC;


select * from produto

select * from compra

select * from compra_item

select * from subcategoria_produto

select * from subcategoria

    INSERT INTO produto (categoria_id, nome, descricao, quantidade, preco) VALUES (4, 'Askov Morango', 'Vodka Askov Morango 1L', 91, 20.00) RETURNING id;

