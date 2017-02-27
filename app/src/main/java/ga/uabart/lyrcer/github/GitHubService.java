package ga.uabart.lyrcer.github;

import java.util.List;

import ga.uabart.lyrcer.github.model.Content;
import ga.uabart.lyrcer.github.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("repos/{user}/{repo}/contents")
    Call<List<Content>> listContent(@Path("user") String user, @Path("repo") String repo);
    @GET("repos/{user}/{repo}/contents/{path}")
    Call<List<Content>> listContent(@Path("user") String user, @Path("repo") String repo, @Path("path") String path);
}