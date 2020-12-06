package com.andymproj.technologynewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ElemAdapter extends ArrayAdapter<GettingTNews>{
    private LayoutInflater inflater;
    private List<GettingTNews> listNews = new ArrayList<>();


    public ElemAdapter(@NonNull Context context, int resource, List<GettingTNews> listNews, LayoutInflater inflater) {
        super(context, resource, listNews);
        this.inflater = inflater;
        this.listNews = listNews;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        GettingTNews newsMain = listNews.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.window_tnews, null, false);
            viewHolder = new ViewHolder();

            viewHolder.titNameBold = convertView.findViewById(R.id.textViewTitBoldTNews);
            viewHolder.titName = convertView.findViewById(R.id.textViewTitTNews);
            viewHolder.contName = convertView.findViewById(R.id.textViewTNews);
            viewHolder.nomName = convertView.findViewById(R.id.textViewNomTNews);
            viewHolder.imgName = convertView.findViewById(R.id.imageViewTNews);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.titNameBold.setText(newsMain.getTitNameBold());
        viewHolder.titName.setText(newsMain.getTitName());
        viewHolder.contName.setText(newsMain.getContName());
        viewHolder.nomName.setText(newsMain.getNomTNews());
        Picasso.with(viewHolder.imgName.getContext()).load(newsMain.getImgLink()).into(viewHolder.imgName);

        return convertView;
    }
    private class ViewHolder{
        TextView titNameBold;
        TextView titName;
        TextView contName;
        TextView nomName;
        ImageView imgName;
    }
}