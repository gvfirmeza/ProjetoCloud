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

### CU01 - Criação de Conta

**Descrição:** Esse caso de uso trata da criação de uma nova conta no sistema, vinculando um cartão de crédito ao usuário e definindo os parâmetros iniciais, como limite disponível e status do cartão.
<br/>
**Ator(es):** Usuário, Sistema de Backend.
<br/>
**Pré-condição:** O usuário deve fornecer as informações necessárias para a criação da conta, como nome, CPF, tipo de conta, e dados do cartão.
<br/>
**Fluxo Principal:**
- O usuário solicita a criação de uma nova conta.
- O sistema valida os dados fornecidos.
- O sistema cria a conta e vincula o cartão de crédito.
- O sistema retorna uma confirmação de sucesso ao usuário.
  
**Pós-condição:** A conta é criada e armazenada no banco de dados, com o cartão associado.

### CU02 - Autorização de Transação

**Descrição:** Esse caso de uso lida com a autorização de uma transação de cartão de crédito para uma conta existente, verificando se a transação atende às regras de negócio definidas.
<br/>
**Ator(es):** Comerciante, Sistema de Autorização.
<br/>
**Pré-condição:** A conta e o cartão devem existir e estar ativos.
<br/>
**Fluxo Principal:**
- O comerciante envia uma solicitação de transação.
- O sistema verifica o status do cartão (ativo/inativo).
- O sistema verifica o limite disponível da conta.
- O sistema verifica a frequência das transações no intervalo de 2 minutos.
- O sistema verifica a duplicidade da transação (mesmo valor e comerciante).
- O sistema autoriza ou nega a transação com base nas regras.
- O sistema retorna o resultado ao comerciante.

**Pós-condição:** A transação é registrada como autorizada ou negada.

### CU03 - Notificação de Transação

**Descrição:** Esse caso de uso trata do envio de notificações ao usuário sobre o status das transações realizadas, incluindo transações aprovadas, negadas e quaisquer problemas identificados.
<br/>
**Ator(es):** Usuário, Sistema de Notificação.
<br/>
**Pré-condição:** O sistema deve ter registrado uma transação (autorizada ou negada).
<br/>
**Fluxo Principal:**
- O sistema identifica a necessidade de enviar uma notificação.
- O sistema cria uma notificação com os detalhes da transação.
- O sistema envia a notificação ao usuário.
- O usuário recebe a notificação e pode visualizar os detalhes.

**Pós-condição:** O usuário é informado sobre o status da transação.

