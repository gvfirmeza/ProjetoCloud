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
Descrição: O usuário cria uma conta associada a um cartão de crédito.

**Fluxo Principal:**
- O usuário envia os dados para criar a conta.
- O sistema valida e cria a conta.
- O sistema confirma a criação da conta.

### CU02 - Autorizar Transação

Descrição: O sistema decide se uma transação de cartão de crédito é autorizada ou negada.
**Fluxo Principal:**
- Uma transação é solicitada.
- O sistema verifica as regras de negócio (limite, status do cartão, frequência, duplicidade).
- O sistema autoriza ou nega a transação.

### CU03 - Notificar Usuário

Descrição: O sistema notifica o usuário sobre o status da transação.
**Fluxo Principal:**
- A transação é processada.
- O sistema envia uma notificação ao usuário.
