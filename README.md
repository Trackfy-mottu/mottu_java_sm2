# API da Mottu - Sistema de Gerenciamento de Motos

Este é um projeto acadêmico desenvolvido em parceria com a Mottu para gerenciamento de motos em pátios, incluindo controle de usuários, pendências e localização de veículos.

## 📹 Demonstração

Assista à demonstração do projeto no YouTube: [https://youtu.be/e2QLd24y_mE](https://youtu.be/e2QLd24y_mE)

## 👥 Equipe de Desenvolvimento

- **Guilherme Alves Pedroso** - RM555357
- **João Vitor Silva Nascimento** - RM554694  
- **Rafael Souza Bezerra** - RM557888

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **Spring Cache**
- **JWT (JSON Web Tokens)**
- **PostgreSQL** (produção) / **H2 Database** (desenvolvimento)
- **Flyway** (migrações de banco)
- **Maven**
- **Thymeleaf** (interface web)
- **TailwindCSS + DaisyUI** (estilização moderna)

## 📋 Funcionalidades

### 🔐 Autenticação e Autorização
- Sistema de login com JWT
- Controle de acesso baseado em roles (ADMIN, EMPLOYEE)
- Filtros de segurança personalizados

### 🏍️ Gerenciamento de Motos
- **Cadastro de motos** com validação de placa e chassi
- **Status das motos**: Pronto para Uso, Em Uso, Manutenção, Descarte
- **Modelos**: Pop, Sport, E
- **Localização**: Dentro ou Fora do pátio
- **CRUD completo** (Create, Read, Update, Delete)

### 🏢 Gerenciamento de Pátios
- Cadastro e gerenciamento de pátios
- Controle de capacidade máxima
- Código de acesso único para cada pátio
- Contagem automática de motos por pátio

### 📝 Sistema de Pendências
- Criação e acompanhamento de pendências das motos
- **Status das pendências**: Resolvido, Pendente, Em Andamento
- Associação de pendências às motos
- Sistema de numeração e descrição

### 👤 Gerenciamento de Usuários
- Cadastro de usuários com diferentes perfis
- Associação de usuários aos pátios
- Criptografia de senhas com BCrypt

## 🏗️ Arquitetura

```
src/main/java/com/example/challenge_mottu_java/
├── cofig/              # Configurações de segurança
├── controller/
│   ├── api/           # Controllers REST API
│   └── web/           # Controllers Web (Thymeleaf)
├── converter/         # Conversores customizados
├── dto/              # Data Transfer Objects
├── Enums/            # Enumeradores
├── model/            # Entidades JPA
├── repository/       # Repositórios Spring Data
├── service/          # Camada de serviços
└── specification/    # Especificações JPA
```

## 🔧 Configuração e Instalação

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

### Passos para executar

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd challenge-mottu-java
   ```

2. **Instale as dependências**
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

## 🎨 Design System

A interface web utiliza um design system moderno baseado em:

- **TailwindCSS**: Framework CSS utility-first
- **DaisyUI**: Componentes prontos para Tailwind
- **Tema escuro** com acentos em verde
- **Design responsivo** para todos os dispositivos
- **Iconografia SVG** personalizada
- **Animações sutis** e transições suaves

### Paleta de Cores
- **Primária**: Verde (`#16A34A`) - Ações principais
- **Secundária**: Cinza (`#374151`) - Elementos neutros  
- **Fundo**: Preto (`#000000`) - Fundo principal
- **Superficie**: Cinza escuro (`#1F2937`) - Cards e modais
- **Texto**: Branco/Verde - Contraste otimizado


## 💾 Banco de Dados

### Estrutura das Tabelas

O projeto utiliza **Flyway** para controle de versão do banco de dados com as seguintes migrações:

#### V1 - Estrutura inicial (descontinuada)
- Tabelas iniciais com estrutura básica

#### V2 - Adição da tabela Court (descontinuada)  
- Primeira versão da tabela de pátios

#### V3 - **Reestruturação completa** (versão atual)
- **`court`**: Pátios com código de acesso único
- **`bike`**: Motos associadas aos pátios  
- **`users`**: Usuários com roles e pátios
- **`pending`**: Pendências das motos

#### V4 - Dados iniciais
- **Pátios pré-cadastrados** em São Paulo:
  - Pátio Morumbi Shopping (80 vagas)
  - Pátio Jardins (35 vagas) 
  - Pátio Liberdade (40 vagas)
  - Pátio Brooklin Corporate (65 vagas)

## 🎯 Modelo de Dados

### Entidades principais

- **User**: Usuários do sistema com roles e associação a pátios
- **Court**: Pátios com capacidade e código de acesso
- **Bike**: Motos com status, modelo e localização
- **Pending**: Pendências associadas às motos

## 🔒 Segurança

- **JWT Tokens** para autenticação
- **BCrypt** para hash de senhas
- **CORS** configurado para desenvolvimento
- **Roles** para controle de acesso:
  - `ADMIN`: Acesso total
  - `EMPLOYEE`: Acesso limitado

## 📊 Cache

Implementado cache Spring para melhorar performance:
- Cache de bikes por pátio
- Cache de pendências
- Invalidação automática em operações de escrita

## 🌐 Interface Web

Além da API REST, o projeto inclui uma interface web moderna construída com **Thymeleaf + TailwindCSS + DaisyUI** que oferece:

- **Dashboard interativo** com estatísticas em tempo real das motos
- **Design responsivo** com tema escuro e visual moderno
- **Formulários intuitivos** para cadastro e edição
- **Visualização detalhada** das motos e pendências
- **Sistema de login integrado** com validações visuais
- **Gestão completa** de motos, pátios e pendências via interface web


#### Recursos da Interface:
- **Cards informativos** com dados dos pátios
- **Tabelas responsivas** com ações inline
- **Badges coloridos** para status visuais
- **Formulários validados** em tempo real
- **Mensagens de feedback** para ações do usuário

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos em parceria com a Mottu.
