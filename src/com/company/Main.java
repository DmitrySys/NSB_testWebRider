package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args)
    {
        String strImageName;
        String IMAGE_DESTINATION_FOLDER = "/home/dmitry/imgbuffer/";
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        String url = "http://moyaokruga.ru/bur-gaz/";
        try{
            Document document = Jsoup.connect(url).get();
            Elements titles=document.select("div.txt-articles > header > h3 > a");
            Elements intro = document.select("div.txt-articles > p");
            Elements data=document.select("div.txt-articles > header > p >time");
            Elements category=document.select("div.txt-articles > header > p> a");
            Elements imgbitmap = document.select("div.news-container > article.articles.clearfix > a > img");
            HashMap<String,Object> map;
            Element title;
            Element introItem;
            Element dateItem;
            Element categoryItem;
            Element _imgbitmap;
            String src;
            for(int i=0;i<titles.size();i++)
            {
                map = new HashMap<>();
                title=titles.get(i);
                introItem=intro.get(i);
                dateItem=data.get(i);
                categoryItem=category.get(i);
                _imgbitmap=imgbitmap.get(i);
                map.put("TitleItem",title.text());
                map.put("NewsIntroItem",introItem.text());
                map.put("Date",dateItem.text());
                map.put("Category",categoryItem.text());
                map.put("href",title.attr("href"));
                src ="http://moyaokruga.ru"+_imgbitmap.attr("src") ;

                URL imgdl = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) imgdl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                System.out .println(input.toString());
                arrayList.add(map);
            }
            for(HashMap<String,Object> item : arrayList)
            {
                System.out.println("Категория:"+item.get("Category")+"\n"+item.get("TitleItem")+"\n"+item.get("NewsIntroItem")+"\nDate:"+item.get("Date")+"\n"+item.get("href")+"\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}