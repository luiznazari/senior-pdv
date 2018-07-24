INSERT INTO usuario (id, login, senha, nome) VALUES 
	(1, 'luiz', '123123', 'Luiz Felipe Nazari'),
	(2, 'admin', '123', 'Administrador'),
	(3, 'venda', '123', 'Vendedor'),
	(4, 'soadmin', '123', 'Apenas Administrador');
	
INSERT INTO usuario_perfil (id, id_usuario, perfil) VALUES
	(1, 1, 'ADMINISTRADOR'),
	(2, 1, 'VENDEDOR'),
	(3, 2, 'ADMINISTRADOR'),
	(4, 2, 'VENDEDOR'),
	(5, 3, 'VENDEDOR'),
	(6, 4, 'ADMINISTRADOR');

INSERT INTO produto (codigo, descricao, valor) VALUES
	('422628', 'Clean Code: A Handbook of Agile Software Craftsmanship', 91.09),
	('424042', 'Metro 2033', 89.83),
	('424446', 'Metro 2034', 81.83),
	('424648', 'Metro 2035', 96.50);