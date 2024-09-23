CREATE TABLE usuario (
                         doc VARCHAR(20) PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         user_role VARCHAR(20) NOT NULL,
                         password VARCHAR(250) NOT NULL,
                         data_nasc DATE,
                         CONSTRAINT ck_data_nasc CHECK (data_nasc <= CURRENT_DATE - INTERVAL '18 YEARS'),
                         CONSTRAINT ck_usuario_role CHECK (user_role IN ('ADMIN', 'DEFAULT'))
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
                             preco_total_item DECIMAL(10, 2),
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

CREATE TABLE avaliacao (
                           id SERIAL PRIMARY KEY,
                           produto_id INTEGER NOT NULL,
                           usuario_doc VARCHAR NOT NULL,
                           nota INTEGER,
                           data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           constraint fk_produto_id FOREIGN KEY (produto_id) REFERENCES produto(id) on delete cascade,
                           constraint fk_usuario_doc FOREIGN KEY (usuario_doc) REFERENCES usuario(doc) on delete cascade,
                           constraint ck_range_nota check (nota >= 0 AND nota <= 5)
);
