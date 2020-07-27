package smworks.lt.helloandroid.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler { // reikalinga kad atidarytu kamera vietoij fragmento su situ metodu reikia iskvisti
    int MY_PERMISSION_REQUEST_CAMERA = 0;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);// iskviecia kamera
        setContentView(scannerView);// iskviecia kamera
    }

    @Override
    public void handleResult(Result result) { // sita metoda.
        DesktopScreen.resultTextView.setText(result.getText());// handled request
    }

    @Override
    protected void onPause() { // sustabdo kamera
        super.onPause();
        scannerView.startCamera();
    }

    @Override
    protected void onPostResume() { // atidaro kamera, jeigu yra geras iskvietimas.
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
        }

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}



