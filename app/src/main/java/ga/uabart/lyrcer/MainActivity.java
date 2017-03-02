package ga.uabart.lyrcer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import ga.uabart.markdown.MarkdownView;

import ga.uabart.lyrcer.github.GitHubConnector;
import ga.uabart.lyrcer.github.impl.GitHubConnectorImpl;
import ga.uabart.lyrcer.github.impl.GitHubRepoAdapter;

public class MainActivity extends Activity {

    private GitHubConnector githubConnector;
    private GitHubRepoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConnectors();
        initViews();
    }

    private void initConnectors() {
        adapter = new GitHubRepoAdapter(this);
        githubConnector = new GitHubConnectorImpl();
    }

    private void initViews() {
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdown_view);
//        markdownView.setMarkDownText("# Hello World\nThis is a simple markdown"); //Displays markdown text
        markdownView.loadMarkdownFromAssets("test.md");

        final EditText editText = (EditText) findViewById(R.id.activity_main_edit_text);
        Button button = (Button) findViewById(R.id.activity_main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                githubConnector.repo(editText.getText().toString(), adapter);
            }
        });
        ListView listView = (ListView) findViewById(R.id.activity_main_list_view);
        listView.setAdapter(adapter);
    }

}
