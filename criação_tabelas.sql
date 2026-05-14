-- 1. Criar a tabela independente primeiro
CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    uf VARCHAR(50),
    cidade VARCHAR(100),
    bairro VARCHAR(100),
    cep VARCHAR(15),
    num INT,
    taxa_entrega DECIMAL(10,2)
);

-- 2. Tabelas que herdam de Usuario (referenciando o Endereco)
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    idade INT,
    email VARCHAR(100),
    telefone VARCHAR(20),
    endereco_id INT REFERENCES endereco(id)
);

CREATE TABLE administrador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    idade INT,
    email VARCHAR(100),
    telefone VARCHAR(20),
    endereco_id INT REFERENCES endereco(id)
);

CREATE TABLE vendedor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    idade INT,
    email VARCHAR(100),
    telefone VARCHAR(20),
    vendas INT DEFAULT 0,
    endereco_id INT REFERENCES endereco(id)
);

-- 3. Tabela de Produto (referenciando Vendedor)
CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    descricao TEXT,
    quantidade INT,
    preco_unitario DECIMAL(10,2),
    grande_porte BOOLEAN,
    vendedor_id INT REFERENCES vendedor(id)
);

-- 4. Tabela de Pedido (referenciando Cliente, Produto e Vendedor)
CREATE TABLE pedido (
    id SERIAL PRIMARY KEY,
    cliente_id INT REFERENCES cliente(id),
    produto_id INT REFERENCES produto(id),
    quantidade_produto INT,
    valor_produto DECIMAL(10,2),
    porte_grande BOOLEAN,
    status VARCHAR(50),
    vendedor_id INT REFERENCES vendedor(id),
    taxa_entrega DECIMAL(10,2)
);