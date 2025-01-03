package repository;

import model.Video;
import util.FileManager;
import util.VideoConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileVideoRepository implements VideoRepository {
    private final FileManager fileManager;

    public FileVideoRepository(String filePath) {
        this.fileManager = new FileManager(filePath);
    }

    @Override
    public void save(Video video) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileManager.getFile(), true))) {
            bw.write(VideoConverter.toString(video));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o vídeo", e);
        }
    }

    @Override
    public List<Video> findAll() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileManager.getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                Video video = VideoConverter.fromString(line);
                if (video != null) {
                    videos.add(video);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler os vídeos", e);
        }
        return videos;
    }
}