package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoManager videoManager = new VideoManager();
        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
            System.out.println("1. Adicionar vídeo");
         System.out.println("2. Listar vídeos");
            System.out.println("3. Pesquisar vídeo por título");
            System.out.println("4. Editar vídeo");
            System.out.println("5. Excluir vídeo");
            System.out.println("6. Filtrar vídeos por categoria");
            System.out.println("7. Ordenar vídeos por data de publicação");
            System.out.println("8. Exibir relatório de estatísticas");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                continue;
            }

            switch (opcao) {
                case 1 -> adicionarVideo(scanner, videoManager);
                case 2 -> listarVideos(videoManager);
                case 3 -> pesquisarVideo(scanner, videoManager, new TitleSearchStrategy());
                case 4 -> editarVideo(scanner, videoManager);
                case 5 -> excluirVideo(scanner, videoManager);
                case 6 -> filtrarPorCategoria(scanner, videoManager);
                case 7 -> ordenarPorData(videoManager);
                case 8 -> exibirRelatorio(videoManager);
                case 9 -> {
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // Métodos fictícios para evitar erros de compilação
    private static void adicionarVideo(Scanner scanner, VideoManager videoManager) {
        // Implementação aqui
    }

    private static void listarVideos(VideoManager videoManager) {
        // Implementação aqui
    }

    private static void pesquisarVideo(Scanner scanner, VideoManager videoManager, TitleSearchStrategy strategy) {
        // Implementação aqui
    }

    private static void editarVideo(Scanner scanner, VideoManager videoManager) {
        // Implementação aqui
    }

    private static void excluirVideo(Scanner scanner, VideoManager videoManager) {
        // Implementação aqui
    }

    private static void filtrarPorCategoria(Scanner scanner, VideoManager videoManager) {
        // Implementação aqui
    }

    private static void ordenarPorData(VideoManager videoManager) {
        // Implementação aqui
    }

    private static void exibirRelatorio(VideoManager videoManager) {
        // Implementação aqui
    }
}