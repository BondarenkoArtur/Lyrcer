package ga.uabart.lyrcer.github;

import java.util.List;

import ga.uabart.lyrcer.github.impl.GitHubContentAdapter;
import ga.uabart.lyrcer.github.impl.GitHubRepoAdapter;
import ga.uabart.lyrcer.github.model.Content;
import retrofit2.Callback;

public interface GitHubConnector {

    void repo(String s, GitHubRepoAdapter adapter);

    void contents(String user, String repo, GitHubContentAdapter adapter);
    void contents(String user, String repo, String path, GitHubContentAdapter adapter);

    void getContents(String user, String repo, Callback<List<Content>> callback);
    void getContents(String user, String repo, String path, Callback<List<Content>> callback);
}
