package ga.uabart.lyrcer.github.impl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ga.uabart.lyrcer.R;
import ga.uabart.lyrcer.contents.ContentsActivity;
import ga.uabart.lyrcer.github.model.Repo;

import static ga.uabart.lyrcer.contents.ContentsActivity.CONTENTS_REPO;
import static ga.uabart.lyrcer.contents.ContentsActivity.CONTENTS_USER;

public class GitHubRepoAdapter extends BaseAdapter {

    private final Context context;
    private List<Repo> itemList = null;

    public GitHubRepoAdapter(Context context) {
        this.context = context;
    }

    public GitHubRepoAdapter(Context context, List<Repo> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setList(List<Repo> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (itemList == null) {
            return null;
        }

        final Repo repo = itemList.get(position);

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
        holder.text1.setText(String.format("%s",repo.id));
        holder.text2.setText(repo.name);
        holder.text3.setText(repo.description);
        holder.text4.setText(repo.fork ? "true" : "false");
        holder.text5.setText(repo.owner.login);

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, ContentsActivity.class);
                mIntent.putExtra(CONTENTS_REPO, repo.name);
                mIntent.putExtra(CONTENTS_USER, repo.owner.login);
                context.startActivity(mIntent);
            }
        });

        return vi;
    }

    private static class ViewHolder {

        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;

    }
}
