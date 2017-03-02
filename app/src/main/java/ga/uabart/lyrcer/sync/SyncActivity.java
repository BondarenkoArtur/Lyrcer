package ga.uabart.lyrcer.sync;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ga.uabart.lyrcer.R;
import ga.uabart.lyrcer.github.GitHubConnector;
import ga.uabart.lyrcer.github.impl.GitHubConnectorImpl;

public class SyncActivity extends Activity {

    public static final String CONTENTS_REPO = "contents_repo";
    public static final String CONTENTS_USER = "contents_user";
    private GitHubConnector githubConnector;
    boolean isCredentialsAvailable = false;
    private String githubUserName;
    private String githubRepo;
    private CallbackManager callbackManager;
    private SyncManager syncManager;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        textView = (TextView) findViewById(R.id.activity_sync_text_view);
        initConnectors();
        getBundles();
    }

    private void initConnectors() {
        githubConnector = new GitHubConnectorImpl();
        callbackManager = new CallbackManager();
        syncManager = new SyncManager(githubConnector, callbackManager, new SyncListener(),
                getExternalFilesDir(githubRepo));
    }

    private void getBundles() {
        githubUserName = getIntent().getExtras().getString(CONTENTS_USER);
        githubRepo = getIntent().getExtras().getString(CONTENTS_REPO);
        if (githubUserName != null && githubRepo != null && !githubUserName.isEmpty() && !githubRepo.isEmpty()) {
            isCredentialsAvailable = true;
            sync();
        }
    }

    private void onRefresh(int amount, long size) {
        textView.setText("Amount: " + amount + " Size: " + size);
    }

    private void sync() {
        if (isCredentialsAvailable) {
            syncManager.start(githubUserName, githubRepo);
        }
    }

    class SyncListener {
        void onSync(int amount, long size) {
            onRefresh(amount, size);
        }
    }

}
