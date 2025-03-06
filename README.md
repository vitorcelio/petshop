# 🐶 PetShop Metaway

> Um sistema para gestão de um pet shop, permitindo cadastro de clientes, animais, contatos, agendamentos e muito mais!

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Hibernate / JPA
- PostgreSQL
- Docker / Docker Compose
- JWT para autenticação
- OpenAPI (Swagger) para documentação
- Java EE 8
- JSF (JavaServer Faces)

## 📦 Como Baixar e configurar o Projeto Localmente

### 1️⃣ **Pré-requisitos**

- Docker e Docker Compose instalados
- Java 17+ instalado
- Maven configurado

### 2️⃣ **Clonar o repositório**

```
git clone https://github.com/vitorcelio/petshop.git
cd petshop
```

### 3️⃣ **Gerar ".jar" do projeto petshop-backend e ".war" do projeto petshop-frontend**

```
mvn clean install
```

### 4️⃣ **Observação SQL**

- Dentro do projeto tem um init.sql, ele já vai ser executado assim que o container do postgres subir, de forma
  automática. Então, não vamos nos preocupar com a parte do banco de dados.

## 🐋 Após baixar o projeto e gerar os arquivos de execução, vamos rodá-los no nosso Docker:

- abra o terminal (na pasta principal dos projetos) e rode o comando:

```
docker-compose up --build -d
```

## 👀 Vamos verificar nosso projeto na web:

- Abra o navegador de sua preferencia e acesse a URL:

```
http://localhost:8080/petshop/
```

- A conta Admin do projeto:

```
CPF: 47135971043
SENHA: 12345
```

## 👨🏼‍💻 Se quiser verificar a nossa API da aplicação Back-end:

- Acesse a URL do Swagger:

```
http://localhost:8081/swagger-ui/index.html
```