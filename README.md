# Senior PDV

Projeto de desafio - Aplicação Desktop

## Tecnologias/Frameworks

* Java (10)
* JavaFX
* PostgreSQL
* Flyway (para migração de banco de dados)
* Spring Boot
	* Spring Security
	* Spring Data

# Configurações:

1. Clonar o projeto usando o comando `git clone https://github.com/luiznazari/senior-pdv.git`;
2. Importar como "maven project" no Eclipse ou outra IDE;
3. O projeto foi desenvolvido com Java10, portanto, é necessária uma JDK referente à versão;
4. Em caso de erros com dependências, execute o comando `Maven > update project` e então `Run As > maven install`;
5. Configurar a conexão com banco de dados:
	1. Criar os _schemas_ `senior_pdv` e `senior_pdv_test`;
	2. Configurar URL de conexão, usuário e senha nos arquivos: `src/main/resources/application.yml` para conexão utilizada ao executar a aplicação e `src/main/resources/application.yml` para conexão utilizada ao executar testes de integração;
6. Executar a classe principal `desafio.senior.pdv.SeniorPdvApplication.java`.

## Configurando o `application.yml`:

Propriedades que precisam ser alteradas para configurar a conexão com o banco de dados PostgreSQL:
```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/senior_pdv
    username: postgres
    password: postgres
    # ...

flyway:
  url: jdbc:postgresql://localhost:5432/senior_pdv
  user: postgres
  password: postgres
  
# ...
```

# Usuários pré-cadastrados

> Login: luiz<br>
> Senha: 123123<br>
> Perfis: Administrador, Vendedor

> Login: admin<br>
> Senha: 123<br>
> Perfis: Administrador, Vendedor

> Login: venda<br>
> Senha: 12<br>
> Perfis: Vendedor

> Login: soadmin<br>
> Senha: 123<br>
> Perfis: Administrador

---

Desenvolvido por: Luiz Felipe Nazari (<luiz.nazari.42@gmail.com>).
