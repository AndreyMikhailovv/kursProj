package com.andymproj.technologynewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Document doc;
    private Thread secThread;
    private Runnable runnable;
    private ListView lView;
    private ElemAdapter adapter;
    private List<GettingTNews> tNewsList;
    private int winPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = findViewById(R.id.listView);
        tNewsList = new ArrayList<>();
        adapter = new ElemAdapter(this,R.layout.window_tnews,tNewsList,getLayoutInflater());
        lView.setAdapter(adapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }


    private void getWeb(){
        try {
            doc = Jsoup.connect("https://www.igromania.ru/news/hard/").get();
            Elements tNewsEl = doc.getElementsByClass("aubl_cont");
            Element tNewsEl1 = tNewsEl.get(0);
            //Log.d("MyLog","See: "+Integer.toString(3));

            for (int i = 0; i < tNewsEl1.childrenSize(); i++){
                GettingTNews tNewsElem = new GettingTNews();
                tNewsElem.setTitNameBold(tNewsEl1.children().get(i).getElementsByClass("aubli_name").text());
                tNewsElem.setTitName(tNewsEl1.children().get(i).getElementsByClass("aubli_name").text());
                tNewsElem.setContName(tNewsEl1.children().get(i).getElementsByClass("aubli_desc").text());
                tNewsElem.setImgLink(tNewsEl1.children().get(i).getElementsByClass("aubli_img").select("img").attr("src"));
                tNewsElem.setNomTNews(Integer.toString(i));
                tNewsList.add(tNewsElem);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void goToFullTNews(View view) {
        TextView textView = findViewById(R.id.textViewNomTNews);
        winPos = Integer.parseInt(textView.getText().toString());
        //Log.d("MyLog","See: "+textView.getText());


        runnable = new Runnable() {
            @Override
            public void run() {
                getWebFullTNews(winPos);
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }

    private void getWebFullTNews(int windowPosition){
        try {
            doc = Jsoup.connect("https://www.igromania.ru/news/hard/").get();
            Elements tNewsEl = doc.getElementsByClass("aubl_cont");
            Element tNewsEl1 = tNewsEl.get(0);
            String urlFullTNews = "https://www.igromania.ru" + tNewsEl1.children().get(windowPosition).getElementsByClass("aubli_img").select("a").attr("href");

            doc = Jsoup.connect(urlFullTNews).get();
            Elements fullTNewsEl = doc.getElementsByClass("page_news noselect");
            Element fullTNewsEl1 = fullTNewsEl.get(0);
            //Log.d("MyLog","See: "+fullTNewsEl1.children().get(13).text());

            try {
                Intent intent = new Intent(MainActivity.this, FullTNewsActivity.class);
                intent.putExtra("titleTN",fullTNewsEl1.children().get(1).text());
                intent.putExtra("commentTN",fullTNewsEl1.children().get(5).text());
                intent.putExtra("contentTN",fullTNewsEl1.children().get(13).text());
                startActivity(intent);
                finish();
            }
            catch (Exception e){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}