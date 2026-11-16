package com.CarJava.app;

import com.CarJava.model.Car;
import com.CarJava.model.Toyota;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            int option = -1;

            while (option != 0) {
                showMenu();
                option = readInt(scanner, "Digite o número do comando: ");

                switch (option) {
                    case 1 -> listCars(cars);
                    case 2 -> registerCar(scanner, cars);
                    case 0 -> System.out.println("\nEncerrando o sistema. Até logo!");
                    default -> System.out.println("\nOpção inválida!");
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Registro de Carros ===");
        System.out.println("1 - Consultar carros cadastrados");
        System.out.println("2 - Cadastrar carro");
        System.out.println("0 - Sair");
    }

    private static void listCars(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("\nNenhum carro cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Carros Cadastrados (" + cars.size() + ") ---");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]");
            cars.get(i).showInfo();
        }
    }

    private static void registerCar(Scanner scanner, List<Car> cars) {
        System.out.println("\n--- Novo Cadastro ---");
        String brand = readNonEmptyText(scanner, "Marca: ");
        String model = readNonEmptyText(scanner, "Modelo: ");
        String color = readNonEmptyText(scanner, "Cor: ");
        int year = readInt(scanner, "Ano: ");

        try {
            Car car = brand.equalsIgnoreCase("Toyota")
                    ? new Toyota(model, color, year)
                    : new Car(brand, model, color, year);

            cars.add(car);
            System.out.println("\nCarro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("\nNão foi possível cadastrar: " + e.getMessage());
        }
    }

    private static String readNonEmptyText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Este campo não pode ficar vazio.");
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido!");
            }
        }
    }
}
