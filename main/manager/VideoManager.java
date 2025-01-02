package manager;

import model.Video;
import service.VideoService;
import java.util.List;

public class VideoManager {
    private final VideoService videoService;

    public VideoManager(VideoService videoService) {
        this.videoService = videoService;
    }

    public void addVideo(Video video) {
        videoService.addVideo(video);
    }

    public List<Video> listVideos() {
        return videoService.listVideos(); // Corrigido para chamar o método correto
    }
}