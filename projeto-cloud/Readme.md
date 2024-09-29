## **1. Criar Usuário**
### Método: `POST`
### Endpoint: `/usuario`
### Descrição:
Cria um novo usuário no sistema com as informações fornecidas.

### JSON de Requisição:
```json
{
  "nome": "João da Silva",
  "dataNascimento": "1985-05-20T00:00:00",
  "cpf": "123.456.789-09"
}
```

## **2. Buscar Usuário por ID**
### Método: `GET`
### Endpoint: `/usuario/{id}`
### Descrição:
Busca as informações de um usuário pelo ID.

### Parâmetros:
- `id`: O ID do usuário que deseja buscar.
## **3. Associar Cartão a Usuário**
### Método: `POST`
### Endpoint: `/usuario/{id}/cartao`
### Descrição:
Associa um cartão de crédito a um usuário já existente no sistema.

### Parâmetros:
- `id`: O ID do usuário ao qual o cartão será associado.

### JSON de Requisição:
```json
{
  "ativo": true,
  "limite": 5000.00,
  "numero": "1234-5678-9876-5432"
}
```
## **4. Ativar Cartão**
### Método: `POST`
### Endpoint: `/cartao/{id}/ativar`
### Descrição:
Ativa um cartão de crédito, tornando-o utilizável para transações.

### Parâmetros:
- `id`: O ID do cartão que será ativado.

## **5. Desativar Cartão**
### Método: `POST`
### Endpoint: `/cartao/{id}/desativar`
### Descrição:
Desativa um cartão de crédito, impedindo-o de ser usado para transações.

### Parâmetros:
- `id`: O ID do cartão que será desativado.

## **6. Autorizar Transação**
### Método: `POST`
### Endpoint: `/transacao/autorizar`
### Descrição:
Autoriza uma transação de crédito para um cartão, verificando todas as regras de negócio.

### Parâmetros:
- **Query Parameters:**
    - `cartaoId`: ID do cartão utilizado na transação.
    - `valor`: O valor da transação.
    - `comerciante`: Nome do comerciante envolvido na transação.

## **7. Buscar Transação por ID**
### Método: `GET`
### Endpoint: `/transacao/{id}`
### Descrição:
Busca uma transação específica pelo ID.

### Parâmetros:
- `id`: O ID da transação que deseja buscar.
