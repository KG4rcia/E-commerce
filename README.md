# PontoVenda - E- commerce
 
## 👥 Integrantes do Grupo
- Kauan Garcia Dias de Oliveira - RA 42184932
- Murilo da Silva Cardoso - RA 42699517
- Lucas Eduardo Malachias Bagatela - RA 44213948
- Yago Gabriel leal Firmino - RA 42143276
- Ricardo Aguilar Arapa - RA 42618156
 
## 📋 Tema Escolhido
E-commerce
 
## 🎯 Objetivo do Sistema
O objetivo central deste projeto é desenvolver um sistema de e-commerce eficiente e organizado, focado em oferecer uma experiência fluida tanto para o gerenciamento da loja quanto para o cliente final. 

A estrutura busca garantir a segurança e a clareza das informações em cada etapa da compra. 

## 📦 Funcionalidades Principais
1. Cadastrar Produto
2. Listar Produtos
3. Vender Produto
4. Cadastrar Cliente
5. Gerenciar Cliente
 
## 🏗️ Estrutura de Classes (Planejada)
- **Classe 1:** Cliente - Responsável por todas as informações e métodos do cliente
- **Classe 2:** Gerenciador - Responsável por fazer o gerenciamento de todas os clientes e produtos
- **Classe 3:** Pedido - Responsável por representar o pedido, ele determina se foi realizado ou não
- **Classe 4:** Produto - Responsável por representar o produto dentro do sistema, controla quantidade, preço, descrição e etc
- **Classe 5:** Endereço - Representa o endereço, guarda bairro, rua e CEP para que Pedido para possa ser entregue.
 
## 🔄 Regra de Negócio Complexa
A rerga de negócio implementada é a taxa de entrega se o produto for ou não de grande porte. A taxa é feita sobre o valor total do pedido, ela equivale 
a 10% do valor total (Unitário x Quantidade)
