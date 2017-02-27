package ga.uabart.lyrcer.github;

import ga.uabart.lyrcer.github.impl.GitHubContentAdapter;
import ga.uabart.lyrcer.github.impl.GitHubRepoAdapter;

public interface GitHubConnector {

    void repo(String s, GitHubRepoAdapter adapter);

    void contents(String user, String repo, GitHubContentAdapter adapter);
    void contents(String user, String repo, String path, GitHubContentAdapter adapter);
}
