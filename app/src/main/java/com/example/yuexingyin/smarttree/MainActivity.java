package com.example.yuexingyin.smarttree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//We are using zxing library to implement barcode scanner function
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aboutBtn;
    private Intent intentAbout;
    private Button nearByBtn;
    private Button photoBtn;
    private Button shareBtn;
    private Button commentBtn;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        //create the action bar
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();//get the id

        // if the log out button is clicked, trigger the log out action
        if(id == R.id.action_sign_out){

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up the "About" button
        aboutBtn = (Button) findViewById(R.id.about_btn);
        //set up listener on this button
        aboutBtn.setOnClickListener(this);
        //set up the "NearBy" button
        nearByBtn = (Button) findViewById(R.id.buttonNearBy);
        //set up the listener to this button
        nearByBtn.setOnClickListener(this);
        //set up the "Photo" button
        photoBtn = (Button) findViewById(R.id.photo_btn);
        //set up the listener to this button
        photoBtn.setOnClickListener(this);
        //set up the "Share" button
        shareBtn = (Button) findViewById(R.id.share_btn);
        //set up the listener to this button
        shareBtn.setOnClickListener(this);
        //set up the "Comment" Button
        commentBtn = (Button) findViewById(R.id.comment_btn);
        //set up the listener to this button
        commentBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){

        int id = view.getId();//get id

        if(id == R.id.about_btn){//go to "About" activity if this button is clicked.

            //Implement scanning function
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //initiate the scanning process
            scanIntegrator.initiateScan();

        }else if(id == R.id.buttonNearBy){// go to "Nearby" activity if this button is clicked.

            Intent intentNearBy = new Intent(MainActivity.this, NearbyActivity.class);//jump to map
            //start map activity
            startActivity(intentNearBy);
        }else if(id == R.id.photo_btn){//go to "Photo/Video" activity if this button is clicked

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Photo or Video")
                    .setPositiveButton(R.string.choose_take_photo, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //activate the take photo function
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivity(cameraIntent);
                        }
                    })
                    .setNegativeButton(R.string.choose_video, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //active the video recording function
                            Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            startActivity(cameraIntent);
                        }
                    });
            builder.show();

        }else if(id == R.id.share_btn){

            //Start the gallery
            Intent shareIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
            startActivity(shareIntent);
        }else if(id == R.id.comment_btn){

            //Start the comment activity
            Intent commentIntent = new Intent(MainActivity.this, CommentActivity.class);
            commentIntent.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(commentIntent);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        //Getting scanning result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if(scanningResult != null){//Make sure we actually got a result first
            String content = scanningResult.getContents();
            //jump to the "About" activity
            intentAbout =new Intent(MainActivity.this,AboutActivity.class);
            //pass the content to the new activity
            intentAbout.putExtra("About", content);
            //Start the activity
            startActivity(intentAbout);

        }else{
            //If we are not getting any result, jump to this to send a alert to user.
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




}
