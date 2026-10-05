public package model;

import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static final int[] limites = {50, 100, 200};
    private static final int[] tentativasPorNivel = {10, 7, 5};
    private static final int[] pontuacoesBase = {100, 200, 300};

    public static void iniciarJogo() {
        int nivel = escolherNivel();

        int maxNumero = limites[nivel - 1];
        int tentativas = tentativasPorNivel[nivel - 1];
        int pontosBase = pontuacoesBase[nivel - 1];

        int numeroSecreto = random.nextInt(maxNumero) + 1;
        int pontos = pontosBase;
        boolean acertou = false;

        System.out.println("\nVocê tem " + tentativas + " tentativas.");

        for (int i = 0; i < tentativas; i++) {
            System.out.println("\nTentativa " + (i + 1) + " de " + tentativas);
            System.out.print("Digite seu palpite: ");
            int palpite = scanner.nextInt();

            if (palpite == numeroSecreto) {
                acertou = true;
                int naoUtilizadas = tentativas - (i + 1);
                pontos += naoUtilizadas * 50;

                System.out.println("Parabéns! Você acertou!");
                System.out.println("Número secreto: " + numeroSecreto);
                System.out.println("Pontuação final: " + pontos);

                Historico.add(nomeNivel(nivel), pontos);
                Historico.atualizarRecorde(nivel, pontos);
                break;
            }

            pontos -= 10;
            oferecerDica(nivel, numeroSecreto, palpite, pontos);
            pontos = pontosDaDica;
        }

        if (!acertou) {
            System.out.println("\nSuas tentativas acabaram.");
            System.out.println("O número secreto era: " + numeroSecreto);
            System.out.println("Pontuação final: 0");
            Historico.add(nomeNivel(nivel), 0);
        }
    }

    private static int pontosDaDica;

    private static void oferecerDica(int nivel, int numeroSecreto, int palpite, int pontosAtuais) {
        pontosDaDica = pontosAtuais;

        System.out.print("Deseja usar uma dica? (1-Sim / 2-Não): ");
        int opcao = scanner.nextInt();

        if (opcao != 1) {
            return;
        }

        System.out.println("1. Paridade (-10 pontos)");
        System.out.println("2. Intervalo (-20 pontos)");
        System.out.println("3. Proximidade (quente/frio) (-15 pontos)");
        System.out.print("Escolha a dica: ");
        int dica = scanner.nextInt();

        switch (dica) {
            case 1:
                pontosDaDica -= 10;
                if (numeroSecreto % 2 == 0) {
                    System.out.println("Dica: o número é PAR.");
                } else {
                    System.out.println("Dica: o número é ÍMPAR.");
                }
                break;
            case 2:
                pontosDaDica -= 20;
                int metade = limites[nivel - 1] / 2;
                if (numeroSecreto <= metade) {
                    System.out.println("Dica: está na metade INFERIOR.");
                } else {
                    System.out.println("Dica: está na metade SUPERIOR.");
                }
                break;
            case 3:
                pontosDaDica -= 15;
                int diferenca = Math.abs(palpite - numeroSecreto);
                System.out.println("Dica: " + (diferenca <= 15 ? "quente." : "frio."));
                break;
            default:
                System.out.println("Dica inválida.");
        }

        if (pontosDaDica < 0) {
            pontosDaDica = 0;
        }
    }

    public static void iniciarSequencia() {
        int nivel = escolherNivel();
        int maxNumero = limites[nivel - 1];
        int tentativas = tentativasPorNivel[nivel - 1];
        int[] sequencia = new int[3];
        int[] palpites = new int[sequencia.length];
        int pontos = pontuacoesBase[nivel - 1];
        boolean acertouSequencia = false;

        for (int i = 0; i < sequencia.length; i++) {
            sequencia[i] = random.nextInt(maxNumero) + 1;
        }

        System.out.println("\n=== MODO SEQUÊNCIA ===");
        System.out.println("Adivinhe os 3 números gerados em até " + tentativas + " tentativas.");

        for (int tentativa = 1; tentativa <= tentativas && !acertouSequencia; tentativa++) {
            System.out.print("Tentativa " + tentativa + " de " + tentativas
                    + " - digite os 3 números, separados por espaço: ");
            for (int i = 0; i < palpites.length; i++) {
                palpites[i] = scanner.nextInt();
            }

            int acertos = 0;
            for (int i = 0; i < sequencia.length; i++) {
                if (palpites[i] == sequencia[i]) {
                    acertos++;
                    pontos += 50;
                } else {
                    pontos -= 20;
                }
            }

            acertouSequencia = acertos == sequencia.length;
            if (acertouSequencia) {
                System.out.println("Você acertou a sequência completa!");
            } else {
                System.out.println("Você acertou " + acertos + " de 3 posições. Tente novamente.");
            }
        }

        if (pontos < 0) {
            pontos = 0;
        }

        System.out.println("Sequência correta: [" + sequencia[0] + ", " + sequencia[1] + ", " + sequencia[2] + "]");
        if (!acertouSequencia) {
            System.out.println("Suas tentativas acabaram antes de completar a sequência.");
        }
        System.out.println("Pontuação final: " + pontos);

        Historico.add(nomeNivel(nivel) + " - Sequência", pontos);
        Historico.atualizarRecorde(nivel, pontos);
    }

    private static int escolherNivel() {
        System.out.println("\nEscolha o nível de dificuldade:");
        System.out.println("1. Fácil (1 a 50, 10 tentativas)");
        System.out.println("2. Médio (1 a 100, 7 tentativas)");
        System.out.println("3. Difícil (1 a 200, 5 tentativas)");
        System.out.print("Digite sua escolha: ");

        int nivel = scanner.nextInt();

        while (nivel < 1 || nivel > 3) {
            System.out.print("Opção inválida. Digite 1, 2 ou 3: ");
            nivel = scanner.nextInt();
        }

        return nivel;
    }

    private static String nomeNivel(int nivel) {
        if (nivel == 1) return "Fácil";
        if (nivel == 2) return "Médio";
        return "Difícil";
    }
}
 {
    
}
