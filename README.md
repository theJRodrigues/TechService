# TechService

API REST desenvolvida em **Java 21 + Spring Boot** para um sistema SaaS de **gestão de manutenção de equipamentos**.

O projeto simula um cenário real de mercado, com múltiplas empresas utilizando o sistema (multi-tenant), controle de acesso por perfil de usuário e boas práticas de organização de código.

## Tecnologias
- Java 21
- Spring Boot, Spring Security
- Spring Data JPA + Hibernate
- PostgreSQL (Docker)
- JWT para autenticação
- BCrypt para criptografia de senhas

## Principais conceitos aplicados
- Clean Architecture e separação por camadas
- DTOs e Mappers para comunicação entre camadas
- Autenticação com JWT via cookie HTTP-only
- Autorização por roles (ADMIN, TECHNICIAN, ATTENDANT)
- Isolamento de dados por empresa (company)
- Validação de dados e tratamento global de exceções

## Funcionalidades
- Criação de empresa com usuário ADMIN automático
- Login e logoff
- Cadastro de usuários vinculados à empresa autenticada
- Controle de permissões por perfil

## Objetivo
Projeto desenvolvido para **estudo e prática de Java Back-End**, com foco em arquitetura, segurança e padrões utilizados em aplicações reais.

## Como executar
```bash
docker-compose up -d
./mvnw spring-boot:run
