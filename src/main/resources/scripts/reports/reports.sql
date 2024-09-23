
----------------------------------------------------------
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


---------------------------------
SELECT *
FROM produto p
         JOIN categoria c ON p.categoria_id = c.id
WHERE p.nome ILIKE '%vodka%'
OR p.descricao ILIKE '%vodka%'
OR p.marca ILIKE '%vodka%'
OR c.nome ILIKE '%vodka%';

------------------------------------------
SELECT produto.id, produto.nome AS produto, produto.quantidade_vendas
FROM produto
ORDER BY quantidade_vendas DESC;



---------------------------------------
SELECT p.id, p.nome, SUM(ci.quantidade_item) AS total_vendido,
       (SUM(ci.quantidade_item) * p.preco) AS total_receita
FROM produto p
         JOIN compra_item ci ON p.id = ci.produto_id
         JOIN compra c ON ci.compra_nota_fiscal = c.nota_fiscal
WHERE c.data_compra BETWEEN '2024-08-30' AND '2024-09-01'
GROUP BY p.id, p.nome
ORDER BY total_receita DESC;

--------------------------------------------------

WITH compras_ordenadas AS (
    SELECT usuario_doc,
           data_compra,
           LAG(data_compra) OVER (PARTITION BY usuario_doc ORDER BY data_compra) AS compra_anterior
    FROM compra
),
     tempo_entre_compras AS (
         SELECT usuario_doc,
                EXTRACT(EPOCH FROM (data_compra - compra_anterior))/86400 AS dias_entre_compras
         FROM compras_ordenadas
         WHERE compra_anterior IS NOT NULL
     )


SELECT CASE
           WHEN EXTRACT(YEAR FROM AGE(data_nasc)) BETWEEN 18 AND 25 THEN '18-25'
           WHEN EXTRACT(YEAR FROM AGE(data_nasc)) BETWEEN 26 AND 35 THEN '26-35'
           WHEN EXTRACT(YEAR FROM AGE(data_nasc)) BETWEEN 36 AND 45 THEN '36-45'
           WHEN EXTRACT(YEAR FROM AGE(data_nasc)) > 45 THEN '46+'
           ELSE 'Desconhecido'
           END AS faixa_etaria,
       AVG(dias_entre_compras) AS tempo_medio_entre_compras
FROM tempo_entre_compras
         JOIN usuario ON tempo_entre_compras.usuario_doc = usuario.doc
GROUP BY faixa_etaria
ORDER BY faixa_etaria;



------------------------------------------------------------------------

SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM avaliacao where usuario_doc = '1' and produto_id = 2;

--------------------------------------------------------------------------


select p.nome as produto, (SELECT SUM(a.nota) / COUNT(*) FROM avaliacao a WHERE a.produto_id = p.id) as media
from produto p
WHERE exists ( SELECT 1 FROM avaliacao a2 WHERE a2.produto_id = p.id)
order by media asc;

SELECT id, nome, quantidade_vendas FROM produto WHERE quantidade_vendas > 0 ORDER BY quantidade_vendas desc