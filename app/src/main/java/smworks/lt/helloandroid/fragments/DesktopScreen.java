package smworks.lt.helloandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import smworks.lt.helloandroid.R;


public class DesktopScreen extends AppCompatActivity {
    public static TextView resultTextView;
    Button scanner, toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desktop_screen);

        resultTextView = findViewById(R.id.barCodeTextView);
        scanner = findViewById(R.id.scannerButton);
        toast = findViewById(R.id.toastButton);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Scanner.class));

            }
        });

        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DesktopScreen.this, resultTextView.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}