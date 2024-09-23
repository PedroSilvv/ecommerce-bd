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