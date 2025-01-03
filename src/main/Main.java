package main;

import manager.VideoManager;
import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import util.FileHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler("videos.txt");
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository(fileHandler));
        VideoManager videoManager = new VideoManager(videoService);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Vídeo");
            System.out.println("2. Listar Vídeos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    adicionarVideo(scanner, videoManager);
                    break;
                case "2":
                    listarVideos(videoManager);
                    break;
                case "3":
                    running = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void adicionarVideo(Scanner scanner, VideoManager videoManager) {
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

        if (titulo.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || dataStr.isEmpty()) {
            System.out.println("Erro: Todos os campos são obrigatórios.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataPublicacao = LocalDate.parse(dataStr, formatter);

            Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
            videoManager.addVideo(video);
            System.out.println("Vídeo adicionado com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data de publicação inválida. Use o formato dd/MM/yyyy.");
        }
    }

    private static void listarVideos(VideoManager videoManager) {
        System.out.println("\nLista de Vídeos:");
        videoManager.listVideos().forEach(video -> {
            System.out.println("Título: " + video.getTitulo());
            System.out.println("Descrição: " + video.getDescricao());
            System.out.println("Duração: " + video.getDuracao() + " minutos");
            System.out.println("Categoria: " + video.getCategoria());
            System.out.println("Data de Publicação: " + video.getDataPublicacao());
            System.out.println("-------------------------");
        });
    }
}