# Projeto Cloud
Vocês tem a tarefa de implementar um aplicativo que autoriza uma transação (cartão de credito) para uma conta específica após um conjunto de regras predefinidas.

## Operações
O programa lida com tipos de operações, decidindo qual de acordo com a linha que está sendo processada:
1. Criação de conta
2. Autorização de transação
3. Notificação de transação

## Regras de Negócio

Você deve implementar as seguintes regras, tendo em mente que novas regras aparecerão no futuro:
- O valor da transação não deve exceder o limite disponível: limite insuficiente
- Nenhuma transação deve ser aceita quando o cartão não está ativo: cartão não ativo
- Não deve haver mais de 3 transações em um intervalo de 2 minutos: alta-frequência-pequeno-intervalo
- Não deve haver mais de 2 transações semelhantes (mesmo valor e comerciante) em um intervalo de 2 minutos: transação duplicada

# Casos de Uso

### CU01 - Criar Conta
**Descrição**: Esse caso de uso permite que o usuário crie uma nova conta no sistema, associada a um cartão de crédito.

**Ator(es)**: Usuário, Sistema.

**Pré-condição**: O usuário deve fornecer os dados necessários, como nome, CPF, e dados do cartão.

**Fluxo Principal**:
- O usuário acessa o sistema e escolhe a opção de criar uma nova conta.
- O usuário preenche os dados necessários para a criação da conta.
- O sistema valida os dados fornecidos (e.g., CPF válido, dados do cartão corretos).
- O sistema cria a conta e associa o cartão de crédito ao usuário.
- O sistema retorna uma confirmação ao usuário indicando que a conta foi criada com sucesso.

**Pós-condição**: A conta e o cartão de crédito são registrados no sistema.

### CU02 - Atualizar Informações da Conta
**Descrição**: Esse caso de uso permite que o usuário atualize as informações da conta, como nome, limite de crédito ou status do cartão.

**Ator(es)**: Usuário, Sistema.

**Pré-condição**: A conta deve existir no sistema.

**Fluxo Principal**:
- O usuário acessa o sistema e seleciona a opção de atualizar as informações da conta.
- O usuário modifica as informações desejadas (e.g., nome, limite de crédito, status do cartão).
- O sistema valida as novas informações (e.g., se o novo limite de crédito está dentro dos parâmetros permitidos).
- O sistema atualiza as informações da conta.
- O sistema retorna uma confirmação ao usuário indicando que as informações foram atualizadas com sucesso.

**Pós-condição**: As informações da conta são atualizadas no sistema.

### CU03 - Autorizar Transação
**Descrição**: Esse caso de uso lida com a autorização de uma transação de cartão de crédito, verificando se a transação atende às regras de negócio definidas.

**Ator(es)**: Comerciante, Sistema.

**Pré-condição**: A conta e o cartão devem existir e estar ativos.

**Fluxo Principal**:
- O comerciante envia uma solicitação de transação ao sistema.
- O sistema verifica o status do cartão (ativo/inativo).
- O sistema verifica se o valor da transação está dentro do limite disponível na conta.
- O sistema verifica se há mais de 3 transações no intervalo de 2 minutos.
- O sistema verifica se há mais de 2 transações com o mesmo valor e comerciante no intervalo de 2 minutos.
- O sistema autoriza ou nega a transação com base nas regras de negócio.
- O sistema retorna o resultado da autorização ao comerciante.

**Pós-condição**: A transação é registrada como autorizada ou negada no sistema.

### CU04 - Realizar Transação
**Descrição**: Esse caso de uso lida com o processamento de uma transação que foi previamente autorizada.

**Ator(es)**: Comerciante, Sistema.

**Pré-condição**: A transação deve ter sido autorizada previamente.

**Fluxo Principal**:
- O sistema processa a transação autorizada.
- O valor é debitado da conta do usuário.
- O sistema registra a transação no histórico da conta.
- O sistema envia uma confirmação ao comerciante indicando que a transação foi realizada com sucesso.

**Pós-condição**: A transação é completada e registrada no sistema.

### CU05 - Notificar Usuário
**Descrição**: Esse caso de uso lida com o envio de notificações ao usuário sobre o status das transações realizadas.

**Ator(es)**: Sistema, Usuário.

**Pré-condição**: A transação deve ter sido processada (autorizada ou negada).

**Fluxo Principal**:
- O sistema identifica que uma transação foi processada.
- O sistema cria uma notificação com os detalhes da transação (valor, status, comerciante).
- O sistema envia a notificação ao usuário.
- O usuário recebe a notificação e pode visualizar os detalhes da transação.

**Pós-condição**: O usuário é informado sobre o status da transação realizada.

### Regras de Negócio Detalhadas
**RN01 - Limite Insuficiente**

- Descrição: A transação é negada se o valor solicitado exceder o limite disponível na conta do usuário.
- Justificativa de Negação: "Limite insuficiente."

**RN02 - Cartão Não Ativo**

- Descrição: A transação é negada se o cartão associado à conta não estiver ativo.
- Justificativa de Negação: "Cartão não ativo."

**RN03 - Alta Frequência de Transações**

- Descrição: A transação é negada se houver mais de 3 transações em um intervalo de 2 minutos para a mesma conta.
- Justificativa de Negação: "Alta-frequência-pequeno-intervalo."

**RN04 - Transação Duplicada**

- Descrição: A transação é negada se houver mais de 2 transações com o mesmo valor e comerciante em um intervalo de 2 minutos.
- Justificativa de Negação: "Transação duplicada."

# Fluxograma - Criação de Conta 
![image](https://github.com/user-attachments/assets/546db496-81f9-4a9a-9f45-cc258b33114d)

# Fluxograma - Transação Financeira
![image](https://github.com/user-attachments/assets/8cdb3edf-4a6f-4477-a981-21cddf3fd7c6)

# Modelagem do Banco de Dados

![image](https://github.com/user-attachments/assets/540bb2b8-014b-4b12-bada-cabcd236e166)

# Script de Criação do Banco de Dados

```sql
CREATE TABLE Tipo_Conta (
    ID_Tipo_Conta INT PRIMARY KEY,
    Tipo_Conta VARCHAR(50)
);

CREATE TABLE Cartao (
    ID_Cartao INT PRIMARY KEY,
    Numero_Cartao VARCHAR(16),
    Data_Validade DATE,
    CVV VARCHAR(3)
);

CREATE TABLE Usuario (
    ID_Usuario INT PRIMARY KEY,
    Nome VARCHAR(100),
    Data_Criacao DATE,
    CPF VARCHAR(11),
    ID_Tipo_Conta INT,
    ID_Cartao INT,
    FOREIGN KEY (ID_Tipo_Conta) REFERENCES Tipo_Conta(ID_Tipo_Conta),
    FOREIGN KEY (ID_Cartao) REFERENCES Cartao(ID_Cartao)
);

CREATE TABLE Comerciante (
    ID_Comerciante INT PRIMARY KEY,
    CNPJ VARCHAR(14),
    Razao_Social VARCHAR(100)
);

CREATE TABLE Notificacao (
    ID_Notificacao INT PRIMARY KEY,
    Data_Notificacao DATE,
    Tipo_Notificacao VARCHAR(50)
);

CREATE TABLE Transacao (
    ID_Transacao INT PRIMARY KEY,
    Tipo VARCHAR(50),
    Valor DECIMAL(10, 2),
    Data_Hora DATETIME,
    Status VARCHAR(50),
    ID_Comerciante INT,
    ID_Cartao INT,
    ID_Notificacao INT,
    FOREIGN KEY (ID_Comerciante) REFERENCES Comerciante(ID_Comerciante),
    FOREIGN KEY (ID_Cartao) REFERENCES Cartao(ID_Cartao),
    FOREIGN KEY (ID_Notificacao) REFERENCES Notificacao(ID_Notificacao)
);

CREATE TABLE Autorizacao (
    ID_Autorizacao INT PRIMARY KEY,
    ID_Transacao INT,
    Data_Autorizacao DATE,
    Status_Autorizacao VARCHAR(50),
    FOREIGN KEY (ID_Transacao) REFERENCES Transacao(ID_Transacao)
);
```

[Link Dontpad](https://dontpad.com/ibmec-projetocloud-20242)
[Repo Professor](https://github.com/rafaelcruz-net/ibmec-projeto-cloud-20242)

