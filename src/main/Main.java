package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();

        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
            System.out.println("1. Adicionar vídeo");
            System.out.println("2. Listar vídeos");
            System.out.println("3. Pesquisar vídeo por título");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                continue;
            }

import java.util.Scanner;

            public class Main {
                public static void main(String[] args) {
                    Scanner scanner = new Scanner(System.in);

                    while (true) {
                        System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
                        System.out.println("1. Adicionar vídeo");
                        System.out.println("2. Sair");
                        System.out.print("Escolha uma opção: ");

                        int opcao;
                        try {
                            opcao = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor, digite um número.");
                            continue;
                        }

                        if (opcao == 1) {
                            System.out.print("Digite o título do vídeo: ");
                            String titulo = scanner.nextLine();

                            System.out.print("Digite a descrição do vídeo: ");
                            String descricao = scanner.nextLine();

                            // Chama o método para ler e validar a duração
                            int duracao = lerDuracao(scanner);

                            System.out.println("Vídeo adicionado com sucesso!");
                            System.out.println("Título: " + titulo);
                            System.out.println("Descrição: " + descricao);
                            System.out.println("Duração: " + duracao + " minutos");
                        } else if (opcao == 2) {
                            System.out.println("Saindo do sistema...");
                            break;
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    }

                    scanner.close();
                }

                /**
                 * Método para ler e validar a duração do vídeo.
                 * Garante que a entrada seja um número inteiro positivo.
                 *
                 * @param scanner Scanner para leitura da entrada do usuário.
                 * @return Duração válida (número inteiro positivo).
                 */
                private static int lerDuracao(Scanner scanner) {
                    while (true) {
                        System.out.print("Digite a duração do vídeo (em minutos): ");
                        try {
                            int duracao = Integer.parseInt(scanner.nextLine());
                            if (duracao > 0) {
                                return duracao;
                            } else {
                                System.out.println("Erro: A duração deve ser um número positivo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Erro: A duração deve ser um número inteiro.");
                        }
                    }
                }
            }
            