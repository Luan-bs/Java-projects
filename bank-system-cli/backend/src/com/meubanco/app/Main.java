package com.meubanco.app;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import com.meubanco.model.ContaBancaria;
import com.meubanco.service.TransferenciaService;

public class Main {
    public static void main(String[] args) {

        ArrayList<ContaBancaria> contas = new ArrayList<>();
        contas.add(new ContaBancaria("Admin", "admin", 100000.00));

        int resposta = -1;
        Scanner scanner = new Scanner(System.in);
        TransferenciaService service = new TransferenciaService();

        // Terminal de comandos
        while (resposta != 0) {
            System.out.print(
                    "\n1 - Consultar Saldos" +
                            "\n2 - Depositar" +
                            "\n3 - Sacar" +
                            "\n4 - Transferir" +
                            "\n5 - Criar Conta" +
                            "\n0 - Sair" +
                            "\nEscolha uma opção: ");

            try {

                // Ferramenta que pega o valor digitado
                resposta = scanner.nextInt();
                scanner.nextLine(); // Limpa o '\n' deixado pelo nextInt()

                double valor;
                String nome;
                String cpf;
                double saldo;

                switch (resposta) {
                    case 0:
                        break;
                    case 1:
                        if (contas.isEmpty()) {
                            System.out.println("Nenhuma conta cadastrada.");
                        } else {
                            for (ContaBancaria conta : contas) {
                                System.out.println("Titular: " + conta.getNome() +
                                        " | CPF: " + conta.getCpf() +
                                        " | Saldo: " + conta.getSaldo());
                            }
                        }
                        break;
                    case 2:
                        System.out.print("Digite o CPF da conta: ");
                        cpf = scanner.nextLine();
                        ContaBancaria contaDeposito = buscarPorCpf(contas, cpf);
                        if (contaDeposito != null) {
                            System.out.print("Digite o valor do depósito: ");
                            valor = scanner.nextDouble();
                            contaDeposito.depositar(valor);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;
                    case 3:
                        System.out.print("Digite o CPF da conta: ");
                        cpf = scanner.nextLine();
                        ContaBancaria contaSaque = buscarPorCpf(contas, cpf);
                        if (contaSaque != null) {
                            System.out.print("Digite o valor de saque: ");
                            valor = scanner.nextDouble();
                            contaSaque.sacar(valor);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o CPF da conta de origem: ");
                        ContaBancaria origem = buscarPorCpf(contas, scanner.nextLine());
                        System.out.print("Digite o CPF da conta de destino: ");
                        ContaBancaria destino = buscarPorCpf(contas, scanner.nextLine());
                        if (origem != null && destino != null) {
                            System.out.print("Digite o valor para transferir: ");
                            valor = scanner.nextDouble();
                            service.transferir(origem, destino, valor);
                        } else {
                            System.out.println("Conta de origem ou destino não encontrada.");
                        }
                        break;
                    case 5:
                        System.out.print("Digite o nome: ");
                        nome = scanner.nextLine();
                        System.out.print("Digite o CPF: ");
                        cpf = scanner.nextLine();
                        System.out.print("Digite o saldo: ");
                        saldo = scanner.nextDouble();
                        contas.add(new ContaBancaria(nome, cpf, saldo));
                        System.out.println("Conta criada com sucesso!");
                        break;
                    default:
                        System.out.println("Valor digitado não está nas opções!");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida! Digite apenas números.");
                scanner.nextLine();
            }

        }

        scanner.close();
    }

    private static ContaBancaria buscarPorCpf(ArrayList<ContaBancaria> contas, String cpf) {
        for (ContaBancaria conta : contas) {
            if (conta.getCpf().equals(cpf)) {
                return conta;
            }
        }
        return null;
    }
}
