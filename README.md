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

