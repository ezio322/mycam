package com.example.manoj.mycam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ExecuteTask extends AsyncTask<String, Integer, String> {
    String res = "FAIL";
    Context contextActivity;

    public ExecuteTask(Context context) {
        this.contextActivity = context;
    }

    @Override
    protected String doInBackground(String... params) {
        res=PostData(params);
        res= res.trim();
        return res;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("true"))
        {
            Intent i =new Intent(contextActivity,MainActivity2.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contextActivity.startActivity(i);
            ((Activity)contextActivity).finish();
        }
        else if(result.equalsIgnoreCase("SNA"))
        {
            Toast.makeText(contextActivity, "Can not access Webservice while logging", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(contextActivity, result+"Invalid Credentials", Toast.LENGTH_LONG).show();
        }
    }

    public String PostData(String [] urls)
    {
        String webResponse = "false";
        try{
            final String NAMESPACE = "http://LDAP/";
            //final String URL = "http://14.141.245.15:80/Ldapsignon/ldapservice?wsdl";
            //final String SOAP_ACTION = "http://14.141.245.15:80/Ldapsignon/ldapservice";

            final String URL = "http://14.141.245.15:80/Ldapsignon/ldapservice?wsdl";
            final String SOAP_ACTION = "http://14.141.245.15:80/Ldapsignon/ldapservice";
           // final String URL = "http://10.86.52.168:80/Ldapsignon/ldapservice?wsdl";
            //final String SOAP_ACTION = "http://10.86.52.168:80/Ldapsignon/ldapservice";

            final String METHOD_NAME = "checkcredentials";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo unameProp =new PropertyInfo();
            unameProp.setName("givenusername");

            //gets the first element from urls array
            unameProp.setValue(urls[0]);
            unameProp.setType(String.class);
            request.addProperty(unameProp);

            PropertyInfo upwdProp =new PropertyInfo();
            upwdProp.setName("givenpassword");

            //second element of the urls array
            upwdProp.setValue(urls[1]);
            upwdProp.setType(String.class);
            request.addProperty(upwdProp);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //    envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();

            try {
                webResponse=response.toString();
            }catch (Exception exp)
            {
            }
        }
        catch(Exception e){
            webResponse = "SNA";
            e.printStackTrace();
        }
        System.out.println(webResponse);
        return webResponse;
    }

}

