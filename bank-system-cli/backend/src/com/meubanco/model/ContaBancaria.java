package com.meubanco.model;

public class ContaBancaria {
    
    private double saldo;
    private String nome;
    private String cpf;
    
    public ContaBancaria(String nome, String cpf, double saldo) {
        setNome(nome);
        setCPF(cpf);
        setSaldo(saldo);
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        this.nome = nome; 
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
    
    public void setSaldo(double saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        } else {
            System.out.println("Saldo inicial não pode ser negativo. Definido como 0.0.");
            this.saldo = 0;
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            System.out.println("Deposito de " + valor + " concluido");
        } else {
            System.out.println("Não é permitido valores negativos! Deposito cancelado, tente novamente."); 
        }
    }
    
    public void sacar(double valor) {
        if (valor <= saldo && valor > 0) {
            this.saldo -= valor;
            System.out.println("Saque de " + valor + " Concluido!");
        } else {
            System.out.println("Valor é maior que o saldo ou menor que 0! Saque cancelado, tente novamente.");
        }
    }
}