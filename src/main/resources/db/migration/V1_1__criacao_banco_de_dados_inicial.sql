CREATE TABLE usuario (
	id					BIGSERIAL PRIMARY KEY NOT NULL,
	login				CHARACTER VARYING(100) NOT NULL,
	senha				CHARACTER VARYING(100) NOT NULL,
	nome				CHARACTER VARYING(100) NOT NULL
);
ALTER TABLE usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);

CREATE TABLE usuario_perfil (
	id					BIGSERIAL PRIMARY KEY NOT NULL,
	id_usuario			BIGSERIAL NOT NULL,
	perfil				CHARACTER VARYING(20) NOT NULL,
	CONSTRAINT uk_usuario_perfil UNIQUE (id_usuario, perfil),
	CONSTRAINT fk_usuario_perfil_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id)
);
ALTER TABLE usuario_perfil ALTER COLUMN id SET DEFAULT nextval('usuario_perfil_id_seq'::regclass);

CREATE TABLE produto (
	id					BIGSERIAL PRIMARY KEY NOT NULL,
	codigo				CHARACTER VARYING(20) NOT NULL,
	descricao			CHARACTER VARYING(256) NOT NULL,
	valor				DECIMAL(12, 2) NOT NULL,
	CONSTRAINT uk_produto UNIQUE (codigo)
);
ALTER TABLE produto ALTER COLUMN id SET DEFAULT nextval('produto_id_seq'::regclass);

CREATE TABLE documento (
	id					BIGSERIAL PRIMARY KEY NOT NULL,
	numero				SERIAL NOT NULL,
	valor_total			DECIMAL(12, 2) NOT NULL,
	confirmado			BOOLEAN NOT NULL,
	CONSTRAINT uk_documento UNIQUE (numero)
);
ALTER TABLE documento ALTER COLUMN id SET DEFAULT nextval('documento_id_seq'::regclass);
ALTER TABLE documento ALTER COLUMN numero SET  DEFAULT nextval('documento_numero_seq'::regclass);

CREATE TABLE documento_item (
	id					BIGSERIAL PRIMARY KEY NOT NULL,
	id_produto			BIGSERIAL NOT NULL,
	id_documento		BIGSERIAL NOT NULL,
	valor_unitario		DECIMAL(12, 2) NOT NULL,
	CONSTRAINT uk_documento_item UNIQUE (id_documento, id_produto),
	CONSTRAINT fk_documeto_item_produto FOREIGN KEY (id_produto) REFERENCES produto (id),
	CONSTRAINT fk_documeto_item_documento FOREIGN KEY (id_documento) REFERENCES documento (id)
);
ALTER TABLE documento_item ALTER COLUMN id SET DEFAULT nextval('documento_item_id_seq'::regclass);