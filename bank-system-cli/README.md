# Bank System CLI

Sistema bancário simples em Java executado no terminal, com operações de consulta de saldo, depósito e saque.

## Estrutura do Projeto

```text
bank-system-cli/
├── backend/
│   └── src/
│       └── com/
│           └── meubanco/
│               ├── app/
│               │   └── Main.java
│               ├── model/
│               │   └── ContaBancaria.java
│               ├── service/
│               │   └── TransferenciaService.java
│               └── ideias.txt
└── README.md
```

## Conceitos Praticados

- Encapsulamento (`private`, getters e métodos de negócio)
- Organização em pacotes (`app`, `model`, `service`)
- Validação de valores em depósitos e saques
- Menu interativo no terminal com `Scanner` e tratamento de `InputMismatchException`

## Como Rodar

Dentro da pasta `bank-system-cli/backend/`:

```powershell
javac -d out src\com\meubanco\model\ContaBancaria.java src\com\meubanco\service\TransferenciaService.java src\com\meubanco\app\Main.java
java -cp out com.meubanco.app.Main
```
