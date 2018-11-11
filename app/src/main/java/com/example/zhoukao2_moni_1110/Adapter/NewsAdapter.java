package com.example.zhoukao2_moni_1110.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhoukao2_moni_1110.R;
import com.example.zhoukao2_moni_1110.bean.News;

import java.util.List;

/**
 * Created by 择木 on 2018/11/11.
 */


public class NewsAdapter extends BaseAdapter{
    private Context context;
    private List<News.DataBean> list;
    private ImageView imgShow;
    private TextView tvShow;

    public NewsAdapter(Context context, List<News.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_news,null);
           holder. imgShow = convertView.findViewById(R.id.img_show);
           holder. tvShow = convertView.findViewById(R.id.tv_show);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getThumbnail_pic_s()).into(holder.imgShow);
        holder.tvShow.setText(list.get(position).getTitle());
        return convertView;
    }

    class ViewHolder{
        ImageView imgShow;
        TextView tvShow;
    }
}
