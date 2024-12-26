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

            switch (opcao) {
                case 1:
                    adicionarVideo(scanner, videoService);
                    break;
                case 2:
                    listarVideos(videoService);
                    break;
                case 3:
                    pesquisarVideo(scanner, videoService, searchStrategy);
                    break;
                case 4:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void adicionarVideo(Scanner scanner, VideoService videoService) {
        System.out.print("Digite o título do vídeo: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite a descrição do vídeo: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite a duração do vídeo (em minutos): ");

        int duracao;
        try {
            duracao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: A duração deve ser um número inteiro.");
            return;
        }

        System.out.print("Digite a categoria do vídeo: ");
        String categoria = scanner.nextLine();
        System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();

        // Validação de campos obrigatórios
        if (titulo.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || dataStr.isEmpty()) {
            System.out.println("Erro: Todos os campos são obrigatórios.");
            return;
        }

        try {
            // Validação e parsing da data
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Validação rigorosa
            Date dataPublicacao = sdf.parse(dataStr);

            // Criação e adição do vídeo
            Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
            videoService.addVideo(video);
            System.out.println("Vídeo adicionado com sucesso!");
        } catch (java.text.ParseException e) {
            System.out.println("Erro: Data de publicação inválida. Use o formato dd/MM/yyyy.");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao adicionar vídeo: " + e.getMessage());
        }
    }

    private static void listarVideos(VideoService videoService) {
        List<Video> videos = videoService.listVideos();
        if (videos.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado.");
        } else {
            for (Video video : videos) {
                System.out.println(video);
            }
        }
    }

    private static void pesquisarVideo(Scanner scanner, VideoService videoService, SearchStrategy searchStrategy) {
        System.out.print("Digite o título para busca: ");
        String query = scanner.nextLine();
        List<Video> resultados = searchStrategy.search(videoService.listVideos(), query);
        if (resultados.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado com o título especificado.");
        } else {
            for (Video video : resultados) {
                System.out.println(video);
            }
        }
    }
}