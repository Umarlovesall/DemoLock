package com.moadd.demolock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Login extends AppCompatActivity {
SharedPreferences sp;
EditText userId,password;
Button login;
public static String userRoleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = (EditText) findViewById(R.id.password);
        userId= (EditText) findViewById(R.id.userId);
        login = (Button) findViewById(R.id.login);
        sp=getSharedPreferences("Credentials", Context.MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userId.getText().toString().equals(null) || !password.getText().toString().equals(null)) {
                    new HttpRequestTask().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username/Password fields can't be empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private class HttpRequestTask extends AsyncTask<Void, Void,String> {
        // String a=null;
        String la=null;
        @Override
        public  String doInBackground(Void... params) {
            try {
                //The link on which we have to POST data and in return it will return some data
               /* String URL = "https://www.moaddi.com/moaddi/supplier/serviesforsupplierLogin.htm";
                AppDetailsPojo ap=new AppDetailsPojo();
                ap.setIMEI(sp.getString("IMEI",null));
                ap.setPassword(password.getText().toString());
                ap.setUserId(userId.getText().toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                la = restTemplate.postForObject(URL,ap,String.class);
                return la;*/
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String m) {
            Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();

           if (m.equals("fialure"))
            {
                Toast.makeText(getApplicationContext(),"Access only the userId linked to this device",Toast.LENGTH_LONG).show();
            }
            else if (m.equals("InvalidUserName"))
            {
                Toast.makeText(getApplicationContext(),"Invalid Username/Password",Toast.LENGTH_LONG).show();
            }
            else if (!m.equals(null) && !m.equals("fialure") && !m.equals("InvalidUserName") )
            {
               String arr[] =m.split("#");
                userRoleId=arr[1];
                Toast.makeText(getApplicationContext(),"Login Successful "+ userRoleId,Toast.LENGTH_LONG).show();
                Intent in = new Intent(Login.this,MainActivity.class);
                startActivity(in);
                finish();
            }
            else
           {
               Toast.makeText(getApplicationContext(),"Poor Internet Connection.Try again",Toast.LENGTH_LONG).show();
           }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
       /* Intent in =new Intent(Login.this,EnterPassword.class);
        startActivity(in);*/
    }
}