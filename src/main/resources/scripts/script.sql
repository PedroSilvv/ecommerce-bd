create database perfumaria;

create table usuario(
	doc VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) not null,
    user_role VARCHAR(20) not null
);


create table produto(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    categoria_id INTEGER,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    quantidade INTEGER not null,
    preco DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE SET NULL
);


create table categoria(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);


create table compra(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    usuario_doc VARCHAR(20),
    status_compra VARCHAR(20) NOT NULL,
    preco_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (usuario_doc) REFERENCES usuario(doc) ON DELETE SET NULL
);


create table compra_item(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	compra_id INTEGER,
    produto_id INTEGER,
    quantidade_item INTEGER,
    FOREIGN KEY (compra_id) REFERENCES compra(id) ON DELETE CASCADE,
	FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE SET NULL
);


insert into usuario values(
	'99999999999',
    'Kaster',
	'ADMIN',
    'Kaster'
);

insert into categoria (nome) values('WHISKY');
insert into categoria (nome) values('VINHO');
insert into categoria (nome) values('LICOR');
insert into categoria (nome) values('VODKA');
insert into categoria (nome) values('GIN');
insert into categoria (nome) values('CHAMPGNE');

alter table usuario add column password VARCHAR(250) not null;

alter table usuario add data_nasc DATE not null;

alter table usuario add constraint ck_data_nasc check (data_nasc <= DATE_SUB(CURDATE(), INTERVAL 18 YEAR));

create table subcategoria(
	id integer AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) not null,
    categoria_principal_id integer not null,
    constraint fk_categoria_principal foreign key (categoria_principal_id) references categoria(id) on delete cascade
);

create table subcategoria_produto(
	id integer AUTO_INCREMENT PRIMARY KEY,
    subcategoria_id integer,
    produto_id integer,
    constraint fk_subcategoria_id foreign key (subcategoria_id) references subcategoria(id),
    constraint fk_produto_id foreign key (produto_id) references produto(id)
);

alter table subcategoria add unique (nome);

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

alter table produto add column marca VARCHAR(100);


SELECT p.nome, p.preco, p.quantidade, c.nome, p.descricao
FROM produto p
JOIN categoria c ON p.categoria_id = c.id
WHERE p.preco >= 400.00 AND p.preco <= 500.00;


SELECT sc.nome
FROM subcategoria sc
JOIN subcategoria_produto sp ON sc.id = sp.subcategoria_id
JOIN produto p ON p.id = sp.produto_id
WHERE p.id = 6;

alter table produto add column marca VARCHAR(50);


SELECT *
FROM produto p
JOIN categoria c ON p.categoria_id = c.id
WHERE p.nome LIKE '%termo%'
OR p.descricao LIKE '%termo%'
OR p.marca LIKE '%termo'
OR c.nome LIKE '%termo%';


alter table produto add column quantidade_vendas INTEGER DEFAULT 0;

alter table compra add column data_compra DATE;

select produto.id ,produto.nome as produto, produto.quantidade from produto order by quantidade_vendas desc;

SELECT p.id, p.nome, SUM(ci.quantidade_item) AS total_vendido, (SUM(ci.quantidade_item) * p.preco) as total_receita
FROM produto p
JOIN compra_item ci ON p.id = ci.produto_id
JOIN compra c ON ci.compra_id = c.id
WHERE c.data_compra BETWEEN '2024-08-26' AND '2024-08-28'
GROUP BY p.id, p.nome
ORDER BY total_receita DESC

