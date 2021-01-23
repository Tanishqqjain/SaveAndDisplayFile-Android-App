package com.example.prgm2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button cr = (Button)findViewById(R.id.create);
        tv = (TextView)findViewById(R.id.show);
        cr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//				ViewGroup vg = ;
				View vie = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box, (ViewGroup)findViewById(R.id.mainDialog), false);
				builder.setView(vie);
				final AlertDialog alert = builder.create();
				alert.show();
				final EditText filename = (EditText)vie.findViewById(R.id.filename);
				final EditText filecontent = (EditText)vie.findViewById(R.id.filecontent);
				vie.findViewById(R.id.btncreate).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						String fname = filename.getText().toString();
						String fcontent = filecontent.getText().toString();
						try{
							File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fname + ".txt");
							file.createNewFile();
							if(file.exists()){
								OutputStream os = new FileOutputStream(file);
//								byte[] data = fcontent.getBytes();
								os.write(fcontent.getBytes());
								os.close();
								alert.dismiss();
								Toast.makeText(getApplicationContext(), "File created", Toast.LENGTH_LONG).show();
//								Toast.makeText(getApplicationContext(), "Flle Created", Toast.LENGTH_LONG).show();
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
						
					}
				});
				
				findViewById(R.id.disp).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						ViewGroup vg = (ViewGroup)findViewById(R.id.mainDialog);
						View vie = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box, vg, false);
						builder.setView(vie);
						final AlertDialog alert = builder.create();
						alert.show();
						final EditText filename = (EditText)vie.findViewById(R.id.filename);
						final EditText filecontent = (EditText)vie.findViewById(R.id.filecontent);
						filecontent.setVisibility(View.GONE);
						final Button btn = (Button)vie.findViewById(R.id.btncreate);
						btn.setText("Display");
						btn.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								String fname = filename.getText().toString();
								try{
									File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fname + ".txt");
									FileInputStream is = new FileInputStream(file);
									InputStreamReader isr = new InputStreamReader(is);
									BufferedReader buffer = new BufferedReader(isr);
									String fdata = buffer.readLine();
									String finalReadingData = "";
									while(fdata != null){
										finalReadingData += fdata;
										fdata = buffer.readLine();
									}
									buffer.close();
									Toast.makeText(getApplicationContext(), "File readed", Toast.LENGTH_SHORT).show();
									tv.setText(finalReadingData);
									alert.dismiss();
								}
								catch(Exception e){
									e.printStackTrace();
								}
								
							}
						});
						
					}
				});
			}
		});
        
        
    }
    
    
}
