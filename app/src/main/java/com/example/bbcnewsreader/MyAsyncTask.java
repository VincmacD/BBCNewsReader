package com.example.bbcnewsreader;
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * The MyAsyncTask class extends AsyncTask and is responsible for performing asynchronous network operations
 * to fetch news articles from an XML feed.
 * It takes a URL as input and returns an ArrayList of NewsArticle objects.
 */

public class MyAsyncTask extends AsyncTask<String, String, ArrayList<NewsArticle>> {
    @Override
    protected void onPreExecute(){
    }
    @Override
    protected ArrayList<NewsArticle> doInBackground(String... strings){
        ArrayList<NewsArticle> result = new ArrayList<>();
      try{

          //building string builder
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder = factory.newDocumentBuilder();
          StringBuilder xmlStringBuilder = new StringBuilder();
          //opening connection with website
          URL url = new URL(strings[0]);
          HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
          connection.setReadTimeout(150000);
          connection.setConnectTimeout(15000);
          connection.setRequestMethod("GET");
          connection.connect();
          if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
              BufferedReader reader = new BufferedReader(new InputStreamReader(
                      connection.getInputStream(), "iso-8859-1"), 8);
              StringBuilder sb = new StringBuilder();
              String line = null;
              //iterates through website content
              while ((line = reader.readLine()) != null) {
                  xmlStringBuilder.append(line);
              }
              ByteArrayInputStream input = new ByteArrayInputStream(
                      xmlStringBuilder.toString().getBytes("UTF-8"));
              //parsing input
              Document document = builder.parse(input);
              //entering items in node
              NodeList nList = document.getElementsByTagName("item");
              //iterate through node
              for(int i = 0; i < nList.getLength(); i++){
                  Node node = nList.item(i);
                  if(node.getNodeType() == Node.ELEMENT_NODE){
                      Element element = (Element) node;
                      //add node data into new NewsArticle object
                      result.add(new NewsArticle(element));
                  }
              }
          } else {
              return result;
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
        return result;
    }
    @Override
    protected void onProgressUpdate(String...values){
        super.onProgressUpdate(values);

    }
}
