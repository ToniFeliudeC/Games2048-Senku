package com.example.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SenkuGameActivity extends AppCompatActivity {

    private ArrayList<ArrayList<Integer>> gameSenkuBoard;
    private GridLayout gameGridLayout;
    private TextView selectedCell;
    private boolean isFirstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);

        this.gameGridLayout = findViewById(R.id.gameSenkuGrid);

        this.gameSenkuBoard = new ArrayList<ArrayList<Integer>>();
        this.gameSenkuBoard.add(new ArrayList<>(Arrays.asList(0, 0, 1, 1, 1, 0, 0)));

        Button playButton = findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SenkuGameActivity.this, MainActivity.class));
            }
        });

        setupGridClickListener();
    }

    private void setupGridClickListener() {
        for (int i = 0; i < gameGridLayout.getChildCount(); i++) {
            final TextView cell = (TextView) gameGridLayout.getChildAt(i);
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleCellClick(cell);
                }
            });
        }
    }

    private void handleCellClick(TextView cell) {
        int cellValue = Integer.parseInt(cell.getText().toString());

        if (isFirstClick) {
            if (cellValue == 1) {
                selectedCell = cell;
                isFirstClick = false;
            }
        } else {
            if (cellValue == 0) {
                // Intercambiar valores
                String tempText = selectedCell.getText().toString();
                selectedCell.setText(cell.getText());
                cell.setText(tempText);

                // Reiniciar el estado de selección
                selectedCell = null;
                isFirstClick = true;
            }
        }
    }
}



