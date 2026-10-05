package model;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== JOGO DE ADIVINHAÇÃO ===");
            System.out.println("1. Iniciar novo jogo");
            System.out.println("2. Modo sequência");
            System.out.println("3. Ver regras");
            System.out.println("4. Ver histórico de pontuações");
            System.out.println("5. Ver recordes");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Jogo.iniciarJogo();
                    break;
                case 2:
                    Jogo.iniciarSequencia();
                    break;
                case 3:
                    Regras.exibirRegras();
                    break;
                case 4:
                    Historico.exibirHistorico();
                    break;
                case 5:
                    Historico.exibirRecordes();
                    break;
                case 6:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
    }
}
