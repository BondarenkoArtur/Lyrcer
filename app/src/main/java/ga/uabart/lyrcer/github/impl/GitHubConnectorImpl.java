package ga.uabart.lyrcer.github.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ga.uabart.lyrcer.github.GitHubConnector;
import ga.uabart.lyrcer.github.GitHubService;
import ga.uabart.lyrcer.github.model.Content;
import ga.uabart.lyrcer.github.model.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubConnectorImpl implements GitHubConnector {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String BASE_URL = "https://api.github.com/";
    private final Gson gson;
    private final Retrofit retrofit;

    public GitHubConnectorImpl() {
        gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Override
    public void repo(String s, final GitHubRepoAdapter adapter) {

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos(s);
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                adapter.setList(response.body());
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void contents(String user, String repo, GitHubContentAdapter adapter) {
        contents(user, repo, null, adapter);
    }

    @Override
    public void contents(final String user, final String repo, final String path, final GitHubContentAdapter adapter) {
        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Content>> contents;
        if (path != null) {
            contents = service.listContent(user, repo, path);
        } else {
            contents = service.listContent(user, repo);
        }

        contents.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                adapter.setRequestParams(user, repo, path);
                adapter.setList(response.body());
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {

            }
        });
    }
}
