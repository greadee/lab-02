package com.example.listycity;

import android.os.Bundle;
import android.view.View; // 1:
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button; // 1:
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays; // 0:


public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    ListView cityList;
    LinearLayout footerObjects;
    EditText enterCity;
    Button confirm_button;
    Button del_button;
    Button add_button;

    int selectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // link to the main_activity.xml file
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list); // View: e_l in .xml => Use .id
        footerObjects = findViewById(R.id.footer);
        enterCity = findViewById(R.id.enterCity);
        confirm_button = findViewById(R.id.btnConfirm);
        del_button = findViewById(R.id.btnDeleteCity);
        add_button = findViewById(R.id.btnAddCity);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList); // linking the .xml itself => use .layout

        cityList.setAdapter(cityAdapter); // link between (cities -> dataList) <-> content.xml


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                cityList.setItemChecked(position, true);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerObjects.setVisibility(View.VISIBLE);
                enterCity.setText("");
                enterCity.requestFocus();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = enterCity.getText().toString().trim();
                if (city.isEmpty()) {
                    footerObjects.setVisibility(View.GONE);
                    return;
                }
                dataList.add(city);
                cityAdapter.notifyDataSetChanged();
                footerObjects.setVisibility(View.GONE);
                enterCity.setText("");
            }
        });

        del_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos == -1) return;
                dataList.remove(selectedPos);
                cityAdapter.notifyDataSetChanged();
                selectedPos = -1;
                cityList.clearChoices();
            }
        });





    }
}