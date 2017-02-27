package ga.uabart.lyrcer.contents;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import ga.uabart.lyrcer.R;
import ga.uabart.lyrcer.github.GitHubConnector;
import ga.uabart.lyrcer.github.impl.GitHubConnectorImpl;
import ga.uabart.lyrcer.github.impl.GitHubContentAdapter;

public class ContentsActivity extends Activity {

    public static final String CONTENTS_REPO = "contents_repo";
    public static final String CONTENTS_USER = "contents_user";
    public static final String CONTENTS_PATH = "contents_path";
    private GitHubContentAdapter adapter;
    private GitHubConnector githubConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        initConnectors();
        initViews();
        getBundles();
    }

    private void initConnectors() {
        adapter = new GitHubContentAdapter(this);
        githubConnector = new GitHubConnectorImpl();
    }

    private void initViews() {
        ListView listView = (ListView) findViewById(R.id.activity_contents_list_view);
        listView.setAdapter(adapter);
    }

    private void getBundles() {
        String user = getIntent().getExtras().getString(CONTENTS_USER);
        String repo = getIntent().getExtras().getString(CONTENTS_REPO);
        String path = getIntent().getExtras().getString(CONTENTS_PATH);
        if (user != null && repo != null && !user.isEmpty() && !repo.isEmpty()) {
            if (path != null && !path.isEmpty()) {
                githubConnector.contents(user, repo, path, adapter);
            } else {
                githubConnector.contents(user, repo, adapter);
            }
        }
    }
}
