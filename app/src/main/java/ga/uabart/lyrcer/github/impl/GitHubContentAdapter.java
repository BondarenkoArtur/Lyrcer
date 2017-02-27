package ga.uabart.lyrcer.github.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ga.uabart.lyrcer.R;
import ga.uabart.lyrcer.contents.ContentsActivity;
import ga.uabart.lyrcer.github.model.Content;

import static ga.uabart.lyrcer.contents.ContentsActivity.CONTENTS_PATH;
import static ga.uabart.lyrcer.contents.ContentsActivity.CONTENTS_REPO;
import static ga.uabart.lyrcer.contents.ContentsActivity.CONTENTS_USER;

public class GitHubContentAdapter extends BaseAdapter {

    private final Context context;
    private List<Content> itemList;
    private String user;
    private String repo;
    private String path;

    public GitHubContentAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Content> list) {
        this.itemList = list;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        } else {
            return itemList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        ViewHolder holder;

        if (itemList == null) {
            return null;
        }

        final Content content = itemList.get(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(R.layout.item_repo, null);

            holder = new ViewHolder();
            holder.text1 = (TextView) vi.findViewById(R.id.repo_text1);
            holder.text2 = (TextView) vi.findViewById(R.id.repo_text2);
            holder.text3 = (TextView) vi.findViewById(R.id.repo_text3);
            holder.text4 = (TextView) vi.findViewById(R.id.repo_text4);
            holder.text5 = (TextView) vi.findViewById(R.id.repo_text5);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.text1.setText(content.type);
        holder.text2.setText(content.name);
        holder.text3.setText(content.path);
        holder.text4.setText(content.sha);
        holder.text5.setText(content.size + "");

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content.type.equals("dir")){
                    Intent mIntent = new Intent(context, ContentsActivity.class);
                    mIntent.putExtra(CONTENTS_USER, user);
                    mIntent.putExtra(CONTENTS_REPO, repo);
                    mIntent.putExtra(CONTENTS_PATH, content.path);
                    context.startActivity(mIntent);
                } else if (content.type.equals("file")){
                    Intent mIntent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(content.download_url);
                    mIntent.setData(uri);
                    context.startActivity(mIntent);
                }
            }
        });

        return vi;
    }

    public void setRequestParams(String user, String repo, String path) {
        this.user = user;
        this.repo = repo;
        this.path = path;
    }

    private static class ViewHolder {

        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;

    }
}
