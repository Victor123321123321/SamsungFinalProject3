package com.example.samsungfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView theoremListView;
    private TheoremAdapter adapter;
    private List<Theorem> theoremList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theoremListView = findViewById(R.id.theorem_list_view);
        dbHelper = new DatabaseHelper(this);
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTheoremActivity.class));
            }
        });

        theoremList = dbHelper.getAllTheorems();

        adapter = new TheoremAdapter(this, theoremList);
        theoremListView.setAdapter(adapter);

        theoremListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theorem selectedTheorem = theoremList.get(position);
                Intent intent = new Intent(MainActivity.this, TheoremDetailsActivity.class);
                intent.putExtra("theorem_title", selectedTheorem.getTitle());
                intent.putExtra("theorem_content", selectedTheorem.getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        theoremList.clear();
        theoremList.addAll(dbHelper.getAllTheorems());
        adapter.notifyDataSetChanged();
    }
}
