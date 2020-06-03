package com.example.dam_project.ui.help;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dam_project.MainActivity;
import com.example.dam_project.R;
import com.example.dam_project.sessionmanagment.UserSessionManager;

public class ShareApplicationActivity extends AppCompatActivity {

    private ListView listSharing;
    private ArrayAdapter<String> adapterSharing;
    private String[] appSharing = {"Instagram", "Gmail", "WhatsApp", "Otros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_application);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Reference to the views
        listSharing = (ListView) findViewById(R.id.sharing_list);
        adapterSharing = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, appSharing);
        listSharing.setAdapter(adapterSharing);

        //Listener to create intents for each sharing option in the view
        listSharing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sendIntent;
                switch (position) {
                    case 0:
                        //Instagram
                        sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Descarga la aplicación de Casa Juan, https://drive.google.com/file/d/1Tr-q7XqfTkYXH60vdhHweLhxUkj5XnLn/view?usp=sharing");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.instagram.android");
                        startActivity(sendIntent);
                        break;

                    case 1:
                        //Gmail
                        sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setType("message/rfc822");
                        sendIntent.setPackage("com.google.android.gm");
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Aplicación Casa Juan");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Descarga la aplicación de Casa Juan, https://drive.google.com/file/d/1Tr-q7XqfTkYXH60vdhHweLhxUkj5XnLn/view?usp=sharing");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;

                    case 2:
                        //WhatsApp
                        sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Descarga la aplicación de Casa Juan, https://drive.google.com/file/d/1Tr-q7XqfTkYXH60vdhHweLhxUkj5XnLn/view?usp=sharing");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);
                        break;

                    case 3:
                        //Others (Deploy Android  Sharesheet)
                       sendIntent = new Intent();
                       sendIntent.setAction(Intent.ACTION_SEND);
                       sendIntent.putExtra(Intent.EXTRA_TEXT, "Descarga la aplicación de Casa Juan, https://drive.google.com/file/d/1Tr-q7XqfTkYXH60vdhHweLhxUkj5XnLn/view?usp=sharing");
                       sendIntent.setType("text/plain");
                       Intent shareIntent = Intent.createChooser(sendIntent, null);
                       startActivity(shareIntent);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle arrow click
        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
