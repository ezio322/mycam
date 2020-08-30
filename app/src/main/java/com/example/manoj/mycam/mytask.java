package com.example.manoj.mycam;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class mytask extends AsyncTask<String, Void ,String> {

    Context contextActivity;
    public mytask(Context context)
    {
        this.contextActivity = context;
   }

    @Override
    protected String doInBackground(String... strings) {
        String res=responding(strings[0],strings[1],strings[2],strings[3],strings[4]);
        return res;
    }


    protected void onPostExecute(final String result)
    {
        super.onPostExecute(result);

        if(result.equals("true"))
        {
            MainActivity2.pd.dismiss();
            Toast.makeText(contextActivity,"success",Toast.LENGTH_SHORT).show();
        }
        else
        {
            MainActivity2.pd.dismiss();
            Toast.makeText(contextActivity,"fail",Toast.LENGTH_SHORT).show();

        }

    }



    public String responding(String f,String i,String d,String c,String u)
    {
        String webresponse="wrong";
        try{
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://121.241.172.98/ImageUpload/ImageUpload.asmx?WSDL";
            final String SOAP_ACTION = "http://tempuri.org/UploadFile";
            final String METHOD_NAME = "UploadFile";

            SoapObject request =new SoapObject(NAMESPACE,METHOD_NAME);

            PropertyInfo name =new PropertyInfo();
            name.setName("fileName");
            name.setValue(f);
            //Log.d("fimp",f);
            name.setType(String.class);
            request.addProperty(name);

            PropertyInfo image =new PropertyInfo();
            image.setName("imagebase64");
            image.setValue(i);
            image.setType(String.class);
            request.addProperty(image);

            PropertyInfo depart =new PropertyInfo();
            depart.setName("department");
            depart.setValue(d);
            depart.setType(String.class);
            request.addProperty(depart);

            PropertyInfo comment =new PropertyInfo();
            comment.setName("comments");
            comment.setValue(c);
            comment.setType(String.class);
            request.addProperty(comment);

            PropertyInfo user =new PropertyInfo();
            user.setName("userid");
            user.setValue(u);
            user.setType(String.class);
            request.addProperty(user);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,3000000);
            //Log.d("unique11","11");
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Log.d("unique22","22");
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            webresponse=response.toString();
            //Log.d("very very imp",response.toString());
            return webresponse;
        }
        catch(Exception e)
        {
          //  Log.e("also very imp",Log.getStackTraceString(e));

        }
        return webresponse;
    }


}