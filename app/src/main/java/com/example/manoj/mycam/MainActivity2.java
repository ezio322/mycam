package com.example.manoj.mycam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity2 extends AppCompatActivity {

    String  pathtofile="";
    String myfilename="";
    File file;
    ImageView i2;
    Spinner s2;
    Button b2,logout2;
    String  _bytes64Sting="",comments,dept ;
     static EditText z1;
     static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        logout2=findViewById(R.id.logout2);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(MainActivity2.this,MainActivity.class);

                MainActivity2.this.startActivity(i2);
                MainActivity2.this.finish();

            }
        });


        pd=new ProgressDialog(MainActivity2.this);
        pd.setTitle("Uploading...");
        pd.setMessage("The details are being uploaded");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        Button b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchpicturetakeaction();
            }
        });
        z1=findViewById(R.id.edt);

        i2=findViewById(R.id.image2);


        s2=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.departments,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter);
        s2.setOnItemSelectedListener(new spinneractivity());


        b2=findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View k) {
                comments=z1.getText().toString();
                dept=s2.getSelectedItem().toString();
                if(dept.equals("Select"))
                {
                    Toast.makeText(MainActivity2.this, "Select Department", Toast.LENGTH_SHORT).show();

                }
                else if(comments.isEmpty())
                {
                    Toast.makeText(MainActivity2.this, "Enter Comments", Toast.LENGTH_SHORT).show();


                }
                else
                {
                    pd.show();
                    uploadImage(pathtofile);
                }



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == 1)
        {
            try {

                Bitmap bitmap=BitmapFactory.decodeFile(pathtofile);
                i2.setImageBitmap(bitmap);
            }
            catch(Exception d)
            {
               // Log.d("no data",d.toString());
            }
        }
    }

    private void dispatchpicturetakeaction() {
        Intent takepic =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takepic.resolveActivity(getPackageManager())!=null)
        {
            File photofile = null;
            photofile=createphotofile();
            Log.d("myfilename",myfilename);
            if(photofile != null)
            {
                pathtofile =photofile.getAbsolutePath();
                //Log.d("pathtofile",pathtofile);
                Uri photouri =FileProvider.getUriForFile(MainActivity2.this,"com.example.manoj.mycam",photofile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT,photouri);
                startActivityForResult(takepic,1);
            }
        }
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }


    private File createphotofile() {
        String name= currentDateFormat();
        myfilename=name+".jpg";
        File storagedir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image=null;
        try
        {
            image=File.createTempFile(name,".jpg", storagedir);
        }
        catch(Exception z)
        {
            //Log.d("show","an error");
        }
        return image;
    }




    private void uploadImage(String picturePath) {
       if(picturePath!=null  )
       {
           try
           {    Bitmap bm = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] byteArray = bao.toByteArray();
                _bytes64Sting = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);
              //  Log.d("myfilename2", myfilename);
           }
           catch(Exception n)
           {
               myfilename="null";
               _bytes64Sting="null";
           }
       }
       else
       {
           myfilename="null";
           _bytes64Sting="null";
       }
        new mytask(MainActivity2.this).execute(myfilename, _bytes64Sting, dept, comments, MainActivity.userstr);


    }
}
