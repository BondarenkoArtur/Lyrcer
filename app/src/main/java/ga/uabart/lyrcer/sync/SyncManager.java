package ga.uabart.lyrcer.sync;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ga.uabart.lyrcer.github.GitHubConnector;
import ga.uabart.lyrcer.github.model.Content;
import okhttp3.OkHttpClient;

public class SyncManager {


    public static final String DIR = "dir";
    public static final String FILE = "file";
    private final GitHubConnector githubConnector;
    private final CallbackManager callbackManager;
    private final SyncActivity.SyncListener syncListener;
    private final OkHttpClient client;
    private final File externalFilesDir;
    private String githubUserName;
    private String githubRepo;
    List<Content> listToDownload = new ArrayList<>();
    long size = 0;

    public SyncManager(GitHubConnector githubConnector, CallbackManager callbackManager,
                       SyncActivity.SyncListener syncListener, File externalFilesDir) {
        this.githubConnector = githubConnector;
        this.callbackManager = callbackManager;
        this.syncListener = syncListener;
        this.externalFilesDir = externalFilesDir;
        client = new OkHttpClient();
    }

    void addToDownload(List<Content> contentsList) {
        if (contentsList != null) {
            for (Content content : contentsList) {
                addToDownload(content);
            }
        }
    }

    void addToDownload(Content content) {
        if (content.type.equals(DIR)) {
            githubConnector.getContents(githubUserName, githubRepo, content.path, callbackManager.getCallback(this));
        } else if (content.type.equals(FILE)) {
            listToDownload.add(content);
            size += content.size;
            downloadFile(content);
            syncListener.onSync(listToDownload.size(), size, new File(externalFilesDir, githubRepo));
        }
    }

    private void downloadFile(Content content) {
        File file = new File(externalFilesDir, githubRepo + File.separator + content.path);
        new File(file.getParent()).mkdirs();
        FileUtil.downloadFile(content.download_url, file, client);
    }

    public void start(String githubUserName, String githubRepo) {
        this.githubUserName = githubUserName;
        this.githubRepo = githubRepo;
        githubConnector.getContents(githubUserName, githubRepo, callbackManager.getCallback(this));
    }
}
