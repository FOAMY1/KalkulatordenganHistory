package com.foamy.kalkulatordenganhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtangka1, txtangka2;
    TextView jawaban;
    RadioGroup gruparitmatik;
    String strangka1,strangka2,strangka3;
    double angka1,angka2;
    public static RecyclerView rec_History;
    public static SharedPreferences pref;
    public static Gson gson;
    public static ArrayList<String>List_History;
    public static String strlistHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtangka1=findViewById(R.id.edtangka1);
        txtangka2=findViewById(R.id.edtangka2);
        jawaban=findViewById(R.id.jawaban);
        gruparitmatik=findViewById(R.id.aritmatik);
        rec_History = findViewById(R.id.rec_history);
        pref = this.getSharedPreferences(getString(R.string.shared_history), Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();
        strlistHistory = pref.getString(getString(R.string.string_history),"[]");
        List_History = gson.fromJson(strlistHistory, new TypeToken<ArrayList<String>>(){}.getType());
        if (List_History == null) List_History = new ArrayList<>();
        rec_History.setAdapter(new TagsAdapter(List_History));
        rec_History.setLayoutManager(new LinearLayoutManager(this));
    }
    public void menghitung(View v){
        rec_History.setAdapter(new TagsAdapter(List_History));
        rec_History.setLayoutManager(new LinearLayoutManager(this));
        strangka1= txtangka1.getText().toString();
        strangka2= txtangka2.getText().toString();
        if (strangka1.matches("")||strangka2.matches("")){
            jawaban.setText("belum menginputkan angka");
        }
        else {
            angka1 = Double.parseDouble(strangka1);
            angka2 = Double.parseDouble(strangka2);
            switch (gruparitmatik.getCheckedRadioButtonId()) {
                case R.id.radioButton:
                    jawaban.setText(angka1 + angka2 + "");
                    strangka3 = jawaban.getText().toString();
                    List_History.add(strangka1+" + "+ strangka2 +" = "+strangka3);
                    strlistHistory = gson.toJson(List_History);
                    pref.edit().putString(getString(R.string.string_history), strlistHistory).apply();
                    break;
                case R.id.radioButton1:
                    jawaban.setText(angka1 - angka2 + "");
                    strangka3 = jawaban.getText().toString();
                    List_History.add(strangka1+" - "+ strangka2 +" = "+ strangka3);
                    strlistHistory = gson.toJson(List_History);
                    pref.edit().putString(getString(R.string.string_history), strlistHistory).apply();
                    break;
                case R.id.radioButton2:
                    jawaban.setText(angka1 * angka2 + "");
                    strangka3 = jawaban.getText().toString();
                    List_History.add(strangka1+" x "+strangka2 +" = "+strangka3);
                    strlistHistory = gson.toJson(List_History);
                    pref.edit().putString(getString(R.string.string_history), strlistHistory).apply();
                    break;
                case R.id.radioButton3:
                    jawaban.setText(angka1 / angka2 + "");
                    strangka3 = jawaban.getText().toString();
                    List_History.add(strangka1+" : "+ strangka2 +" = "+strangka3);
                    strlistHistory = gson.toJson(List_History);
                    pref.edit().putString(getString(R.string.string_history), strlistHistory).apply();
                    break;
            }
        }
    }
    public void Delete(View v){
        if(List_History.size()>0){
            List_History.clear();
            rec_History.setAdapter(new TagsAdapter(List_History));
            rec_History.setLayoutManager(new LinearLayoutManager(this));
            strlistHistory = gson.toJson(List_History);
            pref.edit().putString(getString(R.string.string_history), strlistHistory).apply();
        }
    }
}