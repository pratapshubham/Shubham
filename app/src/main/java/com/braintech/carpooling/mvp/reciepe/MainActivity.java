package com.braintech.carpooling.mvp.reciepe;

import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.braintech.carpooling.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWebsite();

    }

    private void getWebsite() {
        final TextView result = (TextView) this.findViewById(R.id.result);
        final Document[] doc = new Document[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                     doc[0] = Jsoup.connect("https://www.chowhound.com/recipes/matcha-green-tea-ice-cream-sandwich-vegan-ginger-molasses-cookies-32082").get();
                    String title = doc[0].title();
                    Elements links = doc[0].select("div[class]");

                    builder.append(title).append("\n");

                    for (Element link : links) {
                        builder.append("\n").append("content : ").append(link.attr("fr_how_to"))
                                .append("\n").append("name : ").append(link.text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}
