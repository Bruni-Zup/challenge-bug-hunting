package main;

import manager.VideoManager;
import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;
import util.FileHandler;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler("videos.txt");
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository(fileHandler));
        VideoManager videoManager = new VideoManager(videoService);

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
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataPublicacao = sdf.parse(dataStr);
            Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
            videoManager.addVideo(video);
            System.out.println("Vídeo adicionado com sucesso!");
        } catch (java.text.ParseException e) {
            System.out.println("Erro: Data de publicação inválida. Use o formato dd/MM/yyyy.");
        }
    }

    private static void listarVideos(VideoManager videoManager) {
        List<Video> videos = videoManager.listVideos();
        if (videos.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado.");
        } else {
            for (Video video : videos) {
                System.out.println(video);
            }
        }
    }

    private static void pesquisarVideo(Scanner scanner, VideoManager videoManager, SearchStrategy searchStrategy) {
        System.out.print("Digite o título para busca: ");
        String query = scanner.nextLine();
        List<Video> resultados = videoManager.searchVideos(searchStrategy, query);
        if (resultados.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado com o título especificado.");
        } else {
            for (Video video : resultados) {
                System.out.println(video);
            }
        }
    }

    private static void editarVideo(Scanner scanner, VideoManager videoManager) {
        System.out.print("Digite o título do vídeo que deseja editar: ");
        String titulo = scanner.nextLine();
        List<Video> videos = videoManager.listVideos().stream()
                .filter(video -> video.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());

        if (videos.isEmpty()) {
            System.out.println("Vídeo não encontrado.");
            return;
        }

        Video video = videos.get(0);
        System.out.print("Digite o novo título (ou pressione Enter para manter): ");
        String novoTitulo = scanner.nextLine();
        if (!novoTitulo.isEmpty()) video.setTitulo(novoTitulo);

        System.out.print("Digite a nova descrição (ou pressione Enter para manter): ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.isEmpty()) video.setDescricao(novaDescricao);

        System.out.print("Digite a nova duração (ou pressione Enter para manter): ");
        String novaDuracao = scanner.nextLine();
        if (!novaDuracao.isEmpty()) video.setDuracao(Integer.parseInt(novaDuracao));

        System.out.print("Digite a nova categoria (ou pressione Enter para manter): ");
        String novaCategoria = scanner.nextLine();
        if (!novaCategoria.isEmpty()) video.setCategoria(novaCategoria);

        System.out.println("Vídeo editado com sucesso!");
    }

    private static void excluirVideo(Scanner scanner, VideoManager videoManager) {
        System.out.print("Digite o título do vídeo que deseja excluir: ");
        String titulo = scanner.nextLine();
        List<Video> videos = videoManager.listVideos();
        boolean removido = videos.removeIf(video -> video.getTitulo().equalsIgnoreCase(titulo));
        if (removido) {
            System.out.println("Vídeo excluído com sucesso!");
        } else {
            System.out.println("Vídeo não encontrado.");
        }
    }

    private static void filtrarPorCategoria(Scanner scanner, VideoManager videoManager) {
        System.out.print("Digite a categoria para filtrar: ");
        String categoria = scanner.nextLine();
        List<Video> videos = videoManager.listVideos().stream()
                .filter(video -> video.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());

        if (videos.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado na categoria especificada.");
        } else {
            for (Video video : videos) {
                System.out.println(video);
            }
        }
    }

    private static void ordenarPorData(VideoManager videoManager) {
        List<Video> videos = videoManager.listVideos().stream()
                .sorted(Comparator.comparing(Video::getDataPublicacao))
                .collect(Collectors.toList());

        if (videos.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado.");
        } else {
            for (Video video : videos) {
                System.out.println(video);
            }
        }
    }

    private static void exibirRelatorio(VideoManager videoManager) {
        List<Video> videos = videoManager.listVideos();
        if (videos.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado.");
            return;
        }

        int totalVideos = videos.size();
        double duracaoMedia = videos.stream().mapToInt(Video::getDuracao).average().orElse(0);
        Map<String, Long> categorias = videos.stream()
                .collect(Collectors.groupingBy(Video::getCategoria, Collectors.counting()));

        System.out.println("=== Relatório de Estatísticas ===");
        System.out.println("Total de vídeos: " + totalVideos);
        System.out.println("Duração média: " + duracaoMedia + " minutos");
        System.out.println("Vídeos por categoria:");
        categorias.forEach((categoria, count) -> System.out.println("- " + categoria + ": " + count));
    }
}