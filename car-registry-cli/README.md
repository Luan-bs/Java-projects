# Car Registry CLI

Sistema de cadastro e consulta de carros em Java executado no terminal.

## Conceitos Praticados

- Encapsulamento (`private`, getters e setters)
- Herança e uso de `super(...)` (`Toyota extends Car`)
- Validação de dados com `IllegalArgumentException`
- Coleções com `List<Car>` e `ArrayList`
- Leitura segura de entrada com `Scanner`

## Como Rodar

Dentro da pasta `car-registry-cli/`:

```powershell
javac -d out src\com\CarJava\model\Car.java src\com\CarJava\model\Toyota.java src\com\CarJava\app\Main.java
java -cp out com.CarJava.app.Main
```
