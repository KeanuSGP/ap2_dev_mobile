
# CONTROLE DE FINANÇAS PESSOAIS
## Um aplicativo para controle de receitas e despesas com a criação de contas financeiras, categorias e lançamento de transações.



### Tecnologias

* Kotlin
* Java
* SpringBoot
* MySQL


### Execução

* Clonar repositório
* Abrir a pasta "android" no Android Studio
* Abrir a pasta "backend" no Intellij
* Sincronizar dependências em ambos os projetos
* Configurar conexão com o banco de dados (use MySQL Workbench para rodar o banco)
* Iniciar backend
* Iniciar aplicatino no Android Studio

### Imagens

<img width="300" alt="Screenshot_20260615_050314" src="https://github.com/user-attachments/assets/b97563a4-a87c-4c75-b8b0-149fc1ec8bd2" />
<img width="300" alt="Screenshot_20260615_050346" src="https://github.com/user-attachments/assets/973847ef-c4e5-4057-8f03-1c7c24ffe496" />
<img width="300" alt="Screenshot_20260615_050404" src="https://github.com/user-attachments/assets/ff25b0b7-5f6d-40e5-9f9d-218e87f1da31" />
<img width="300" alt="Screenshot_20260615_050412" src="https://github.com/user-attachments/assets/8f7c38f7-89c4-4229-9ec0-58de354a5363" />
<img width="300" alt="Screenshot_20260615_050424" src="https://github.com/user-attachments/assets/cad9210e-7248-48fc-8ad9-95673ec9c56a" />


### Descrição API

Desenvolvida em SpringBoot com autenticação via token JWT. Utiliza de arquitetura em camadas para organizar pacotes e classes. 

Entidades:
- Usuario
- Conta Finaceira
- Categoria
- Transação
- Pagamento


Um usuário cria uma conta financeira com nome e saldo -> O usuário cria categorias para suas transações -> O usuário cria transações para serem pagas em suas contas -> O usuário paga ou estorna suas transações

Swagger (requer api rodando): http://localhost:8080/swagger-ui.html




