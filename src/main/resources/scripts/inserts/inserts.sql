INSERT INTO usuario (doc, nome, user_role, password, data_nasc) VALUES('99999999999', 'Dskaster', 'ADMIN', 'Dskaster', '1990-01-01');
INSERT INTO usuario (doc, nome, user_role, password, data_nasc) VALUES('09629588919', 'Peter', 'DEFAULT', 'Peter', '1990-01-01');

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
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('SABORIZADA', 4);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('BRUT', 6);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('EXTRA BRUT', 6);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('DEMI SEC', 6);
INSERT INTO subcategoria (nome, categoria_principal_id) VALUES ('SEC', 6);

insert into compra (nota_fiscal, usuario_doc, status_compra, preco_total, data_compra) values (
    'NF20240923DTESTE', '3', 'PENDENTE', 0, '2024-09-23'
);

insert into compra_item (compra_nota_fiscal, produto_id, quantidade_item, preco_total_item) values (
    'NF20240923DTESTE', 2, 2, (2 * (select p.preco from produto p where id = 2))
);

insert into compra_item (compra_nota_fiscal, produto_id, quantidade_item, preco_total_item) values (
   'NF20240923DTESTE', 3, 1, (1 * (select p.preco from produto p where id = 3))
);

insert into compra_item (compra_nota_fiscal, produto_id, quantidade_item, preco_total_item) values (
    'NF20240923DTESTE', 20, 6, (6 * (select p.preco from produto p where id = 20))
);


UPDATE compra c
SET preco_total = (
    SELECT SUM(ci.preco_total_item)
    FROM compra_item ci
    WHERE ci.compra_nota_fiscal = c.nota_fiscal
)
WHERE c.nota_fiscal = 'NF20240923DTESTE';


___ avaliacoes

INSERT INTO avaliacao (produto_id, usuario_doc, nota, data_avaliacao)
VALUES
-- Produto 1 - Jagermeister
(1, '09629588919', 4, NOW()),
(1, '12345612310', 5, NOW()),
(1, '55566688845', 3, NOW()),
(1, '32456325689', 4, NOW()),
(1, '12345612312', 2, NOW()),

-- Produto 2 - Tanqueray 700ml
(2, '09629588919', 5, NOW()),
(2, '12345612310', 5, NOW()),
(2, '55566688845', 5, NOW()),
(2, '32456325689', 2, NOW()),
(2, '12345612312', 5, NOW()),

-- Produto 3 - Blue Label 1L
(3, '09629588919', 4, NOW()),
(3, '12345612310', 2, NOW()),
(3, '55566688845', 4, NOW()),
(3, '32456325689', 3, NOW()),
(3, '12345612312', 5, NOW()),

-- Produto 4 - El Principal
(4, '09629588919', 2, NOW()),
(4, '12345612310', 5, NOW()),
(4, '55566688845', 4, NOW()),
(4, '32456325689', 4, NOW()),
(4, '12345612312', 1, NOW()),

-- Produto 5 - Rocks 1L
(5, '09629588919', 3, NOW()),
(5, '12345612310', 3, NOW()),
(5, '55566688845', 5, NOW()),
(5, '32456325689', 3, NOW()),
(5, '12345612312', 3, NOW()),

-- Produto 6
(6, '09629588919', 4, NOW()),
(6, '12345612310', 4, NOW()),
(6, '55566688845', 2, NOW()),
(6, '32456325689', 2, NOW()),
(6, '12345612312', 5, NOW()),

-- Produto 7
(7, '09629588919', 5, NOW()),
(7, '12345612310', 5, NOW()),
(7, '55566688845', 5, NOW()),
(7, '32456325689', 5, NOW()),
(7, '12345612312', 3, NOW()),

-- Produto 10
(10, '09629588919', 4, NOW()),
(10, '12345612310', 4, NOW()),
(10, '55566688845', 4, NOW()),
(10, '32456325689', 5, NOW()),
(10, '12345612312', 2, NOW()),

-- Produto 12
(12, '09629588919', 5, NOW()),
(12, '12345612310', 5, NOW()),
(12, '55566688845', 4, NOW()),
(12, '32456325689', 2, NOW()),
(12, '12345612312', 1, NOW()),

-- Produto 13
(13, '09629588919', 5, NOW()),
(13, '12345612310', 5, NOW()),
(13, '55566688845', 5, NOW()),
(13, '32456325689', 5, NOW()),
(13, '12345612312', 3, NOW()),

-- Produto 20
(20, '09629588919', 5, NOW()),
(20, '12345612310', 5, NOW()),
(20, '55566688845', 5, NOW()),
(20, '32456325689', 5, NOW()),
(20, '12345612312', 4, NOW());