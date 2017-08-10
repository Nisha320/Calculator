package com.example.samplecalc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.mozilla.javascript.tools.debugger.Main;

import java.security.PublicKey;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static android.R.attr.button;
import static android.R.attr.format;
import static android.R.id.input;


public class Gridadapter extends BaseAdapter {
    Context context;
    public static String string = "";
    public static String equalresult = "";

    String[] calcitems = {"(", ")", "^", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "MR", "0", ".", "="};

    public Gridadapter(Context cx) {
        context = cx;
    }

    @Override
    public int getCount() {
        return calcitems.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.grid_item, null);

            gridView.setMinimumHeight((int) (MainActivity.height / 8.5));
            Button button = (Button) gridView.findViewById(R.id.btn);
            //   button.setBackgroundColor(Color.parseColor("#2c2c2c"));

            button.setText(calcitems[position]);
            button.setTextSize(20);

            if (calcitems[position].equalsIgnoreCase("(") || calcitems[position].equalsIgnoreCase(")") || calcitems[position].equalsIgnoreCase("C") ||
                    calcitems[position].equalsIgnoreCase("/") || calcitems[position].equalsIgnoreCase("*") || calcitems[position].equalsIgnoreCase("-") ||
                    calcitems[position].equalsIgnoreCase("+")) {

                button.setTextColor(Color.parseColor("#329a83"));

            } else {
                button.setTextColor(Color.parseColor("#e4e4e4"));
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (calcitems[position].equalsIgnoreCase("=")) {
                        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");


                        try {

                            if (string.contains("^")) {
                                String arr[] = string.split("\\^");
                                Log.e("arr[0]", ""+arr.length);
                                Log.e("arr[1]",""+ arr.length);
                                String fst = "", fst1 = "", second = "", sec1 = "";
                                boolean present = false;
                                for (int t = arr[0].length() - 1; t >= 0; t--) {
                                    String c = "" + arr[0].toCharArray()[t];
                                    Log.e("char1", "" + c);
                                    if (!present) {
                                        if (c.equalsIgnoreCase("+") || c.equalsIgnoreCase("-")
                                                || c.equalsIgnoreCase("*") || c.equalsIgnoreCase("/")) {
                                            fst1 = c + fst1;
                                            present = true;
                                        } else {
                                            fst = c + fst;
                                        }
                                    } else {
                                        fst1 = c + fst1;
                                    }
                                }
                                present = false;
                                for (int t = 0; t < arr[1].length() ; t++) {
                                    String c = "" + arr[1].toCharArray()[t];
                                    Log.e("char", "" + c);
                                    if (!present) {
                                        if (c.equalsIgnoreCase("+") || c.equalsIgnoreCase("-")
                                                || c.equalsIgnoreCase("*") || c.equalsIgnoreCase("/")) {
                                            sec1 = c + sec1;
                                            present = true;
                                        } else {
                                            second = c + second;
                                        }
                                    } else {
                                        sec1 =  sec1 + c;
                                    }
                                }
                                Log.e("powfst", "here " + fst);
                                Log.e("powsec", "here " + second);
                                Log.e("fst", fst1);
                                Log.e("sec", sec1);
                                string = fst1 + Math.pow(Double.parseDouble(fst), Double.parseDouble(second)) + sec1;
                                Log.e("final", "" + string);
                            }

                            String resultfinal = ((double) engine.eval(string) + "");
                            if (string.contains(".") || string.contains("/")) {
                                string = resultfinal;
                                equalresult = resultfinal;
                                Log.e("yes", "dot");
                            } else {
                                String arr[] = resultfinal.split("\\.");
                                equalresult = string = arr[0];
                            }
                            MainActivity.resulttv.setText(equalresult);
                            MainActivity.editText.setText("" + equalresult);

                        } catch (Exception e) {
                            Log.e("error", "" + e);
                            //do nothing if theexpression is incorrect
                            Toast.makeText(context, "Please check ur input format", Toast.LENGTH_SHORT).show();

                        }
                    } else if (calcitems[position].equalsIgnoreCase("MR")) {

                        if (MainActivity.mr_value.trim().length() != 0 || !MainActivity.mr_value.equalsIgnoreCase("")) {

                            if (string.trim().length() != 0 || !string.equalsIgnoreCase("")) {
                                String test = string.substring(string.length() - 1);

                                if (test.equalsIgnoreCase("+") || test.equalsIgnoreCase("-")
                                        || test.equalsIgnoreCase("*") || test.equalsIgnoreCase("/")) {

                                    string = string + MainActivity.mr_value;
                                    MainActivity.editText.setText("" + string);

                                } else {

                                    string = MainActivity.mr_value;
                                    MainActivity.editText.setText("" + string);
                                }
                            } else {
                                string = MainActivity.mr_value;
                                MainActivity.editText.setText("" + string);
                            }

                        }

                    }/*else if(calcitems[position].equalsIgnoreCase("C")){

                        string="";

                        MainActivity.editText.setText(""+string);

                    }*/ else {
                        if (calcitems[position].equalsIgnoreCase("+") || calcitems[position].equalsIgnoreCase("-")
                                || calcitems[position].equalsIgnoreCase("*") || calcitems[position].equalsIgnoreCase("/")) {
                            if (string.endsWith("+") || string.endsWith("-") || string.endsWith("*") ||
                                    string.endsWith("/")) {
                                string = string.substring(0, string.length() - 1);
                            }
                        }


                        // Math.pow()
                        string = string + calcitems[position];

                        MainActivity.editText.setText("" + string);

                    }
                }
            });

        } else {
            gridView = (View) convertView;
        }


        return gridView;
    }
}
