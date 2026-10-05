package model;

public class Historico {
    private static final String[] historico = new String[10];
    private static int quantidade = 0;

    private static final int[] recordes = {0, 0, 0};

    public static void add(String nivel, int pontuacao) {
        if (quantidade < historico.length) {
            historico[quantidade] = "Nível: " + nivel + " | Pontuação: " + pontuacao;
            quantidade++;
        } else {
            for (int i = 0; i < historico.length - 1; i++) {
                historico[i] = historico[i + 1];
            }
            historico[historico.length - 1] = "Nível: " + nivel + " | Pontuação: " + pontuacao;
        }
    }

    public static void atualizarRecorde(int nivel, int pontuacao) {
        if (pontuacao > recordes[nivel - 1]) {
            recordes[nivel - 1] = pontuacao;
        }
    }

    public static void exibirHistorico() {
        System.out.println("\n=== HISTÓRICO DE PONTUAÇÕES ===");

        if (quantidade == 0) {
            System.out.println("Nenhuma pontuação registrada.");
            return;
        }

        for (int i = 0; i < quantidade; i++) {
            System.out.println((i + 1) + ". " + historico[i]);
        }
    }

    public static void exibirRecordes() {
        System.out.println("\n=== RECORDES ===");
        for (int i = 0; i < recordes.length; i++) {
            String nivel;
            if (i == 0) nivel = "Fácil";
            else if (i == 1) nivel = "Médio";
            else nivel = "Difícil";

            if (recordes[i] == 0) {
                System.out.println(nivel + ": ainda não há recorde.");
            } else {
                System.out.println(nivel + ": " + recordes[i] + " pontos");
            }
        }
    }
}
