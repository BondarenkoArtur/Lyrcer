package ga.uabart.lyrcer.sync;

import java.util.List;

import ga.uabart.lyrcer.github.model.Content;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackManager {

    public Callback<List<Content>> getCallback(final SyncManager syncManager) {
        return new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                syncManager.addToDownload(response.body());
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
            }
        };
    }
}
