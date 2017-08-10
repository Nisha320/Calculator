package com.example.samplecalc;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.samplecalc.Gridadapter.string;

public class MainActivity extends AppCompatActivity  {

    GridView gridView;
    public static  TextView resulttv,editText;
    Button btnback;
    TextView store;
    public static int height;
    public static String mr_value="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnback=(Button)findViewById(R.id.btnback);
        gridView=(GridView)findViewById(R.id.calcgrid);
        editText=(TextView)findViewById(R.id.edttext);
        resulttv=(TextView)findViewById(R.id.resulttv);
        store=(TextView)findViewById(R.id.store);
        Gridadapter gridadapter=new Gridadapter(MainActivity.this);
        gridView.setAdapter(gridadapter);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
         height = metrics.heightPixels;

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Gridadapter.equalresult.trim().length()!=0 || !Gridadapter.equalresult.equalsIgnoreCase("")) {

                    mr_value=Gridadapter.equalresult;

                }


            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(string.trim().length()!=0 || !string.equalsIgnoreCase("")) {
                   string = removeLastChar(string);
                   editText.setText(string);
               }

            }
        });



    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       mr_value="";
        Gridadapter.equalresult="";
        Gridadapter.string="";

    }
}
