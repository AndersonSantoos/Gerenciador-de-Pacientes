# Sistema de Gerenciamento de Pacientes (PMS - Patient Management System)

## Descrição

O Sistema de Gerenciamento de Pacientes (PMS) é uma aplicação desenvolvida para gerenciar informações de pacientes, histórico médico, agendamentos, prescrições e relatórios de saúde. Através de uma interface intuitiva, os usuários podem facilmente realizar operações CRUD (Criar, Ler, Atualizar, Deletar) em diferentes entidades, como Pacientes, Médicos, Consultas e Administradores.

## Tecnologias

- **Backend:**
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [Hibernate](https://hibernate.org/)
  - [MySQL](https://www.mysql.com/)
  

- **Frontend:**
  - [Thymeleaf](https://www.thymeleaf.org/)

-  **Documentação SWAGGER:**
  - [SWAGGER](https://swagger.io/)
  - [DOCUMENTAÇÃO] http://localhost:8080/swagger-ui/index.html#/

## Funcionalidades

- **Cadastro de Pacientes:** 
  - Realizar o cadastro de novos pacientes.
  
- **Registro de Consultas, Diagnósticos e Prescrições:** 
  - Registrar informações detalhadas sobre as consultas realizadas, diagnósticos e prescrições de medicamentos.

- **Agendamento de Consultas:** 
  - Permitir que os usuários agendem consultas com médicos e gerenciem horários disponíveis.

- **Relatórios de Histórico Médico:** 
  - Gerar relatórios detalhados sobre o histórico médico dos pacientes.

- **Integração com APIs de Saúde:** 
  - Integrar com APIs de saúde para consultas externas e informações adicionais sobre pacientes.

## Estrutura do Projeto

O projeto está organizado nas seguintes pastas:

- **Model:** Contém as classes que representam as entidades do sistema.
  - `Paciente`
  - `Consulta`
  - `Prescricao`
  - `Agendamento`
  - `Medico`
  - `Administrador`

- **Controller:** Contém as classes responsáveis por lidar com as requisições HTTP.
  - `PacienteController`
  - `ConsultaController`
  - `MedicoController`
  - `AdministradorController`

- **Service:** Contém a lógica de negócios do sistema.
  - `PacienteService`
  - `ConsultaService`
  - `MedicoService`
  - `AdministradorService`

- **Repository:** Contém as interfaces que gerenciam a persistência de dados.
  - `PacienteRepository`
  - `ConsultaRepository`
  - `MedicoRepository`
  - `AdministradorRepository`

- **Routes:** Contém as classes que definem as rotas da aplicação.
  - `PacienteRoutes`
  - `ConsultaRoutes`
  - `MedicoRoutes`
  - `AdministradorRoutes`

## Futuras atualizações
 - Implementar segurança e autenticação.
 - Melhorar a interface do usuário. 
 - Integrar com novas APIs de saúde.
 - Adicionar novas funcionalidades conforme necessário.

## Instruções de Instalação

1. **Clone o repositório:**
   ```bash
   - Git clone <https://github.com/AndersonSantoos/Gerenciador-de-Pacientes.>
   - Mysql - Baixar e instalar o software. Além disso, uma vez instalado, criar um banco com o nome gestao.

   
