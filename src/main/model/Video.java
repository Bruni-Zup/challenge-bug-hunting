package service;

import model.Video;
import repository.FileVideoRepository;

import java.util.List;

public class VideoServiceImpl implements VideoService {
    private final FileVideoRepository repository;

    public VideoServiceImpl(FileVideoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addVideo(Video video) {
        repository.save(video);
    }

    @Override
    public List<Video> getAllVideos() {
        return repository.findAll();
    }
}