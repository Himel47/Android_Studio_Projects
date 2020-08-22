package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_switch;
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_switch =(Button)findViewById(R.id.btnOff);

        if(checkReadPermission()){

            functionMain();
        }
        else{
            startActivity(new Intent(this, Permission.class));
        }

    }

    private void functionMain() {
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isFlashOn){

                    turnOffFlash();
                }
                else {

                    turnOnFlash();
                }
            }
        });

        getCamera();
        toogleImage();

    }

    private void turnOffFlash() {

        if(isFlashOn) {
            if(camera == null || parameters == null) {
                return;
            }

            parameters=camera.getParameters();
            parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            isFlashOn=false;
            toogleImage();
        }
    }

    private void turnOnFlash() {

        if(!isFlashOn) {
            if(camera == null || parameters == null) {
                return;
            }

            parameters=camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            isFlashOn=true;
            toogleImage();
        }
    }

    private void getCamera(){
        if(camera==null) {

            try{

                camera = Camera.open();
                parameters = camera.getParameters();
            }

            catch (RuntimeException e) {}
        }
    }

    private void toogleImage(){

        if(isFlashOn){

            btn_switch.setBackgroundResource(R.drawable.on_button);
        }
        else{

            btn_switch.setBackgroundResource(R.drawable.off_button);
        }
    }

    private boolean checkReadPermission(){

        int result = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}