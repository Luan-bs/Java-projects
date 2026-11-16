# SiteCarJava

Sistema web simples para cadastro e consulta de carros, com backend em Java puro (`HttpServer`) e frontend em HTML, CSS e JavaScript.

## Estrutura do Projeto

```text
SiteCarJava/
├── backend/
│   ├── Car.java
│   ├── Toyota.java
│   └── site.java
├── frontend/
│   ├── index.html
│   └── style.css
└── README.md
```

## Como Rodar

No terminal, entre na pasta `backend/` e execute:

```powershell
cd backend
javac -d out Car.java Toyota.java site.java
java -cp out site
```

Depois, acesse no navegador:

```text
http://localhost:8080
```

## Como Parar

- No terminal onde o servidor está rodando, pressione **`Ctrl + C`**.
- Se a porta `8080` continuar ocupada em segundo plano (PowerShell):

```powershell
Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue | ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }
```

## Rotas da API

- `GET /api/cars` — lista os carros cadastrados em JSON
- `POST /api/cars` — cadastra um novo carro (`brand`, `model`, `color`, `year`)
