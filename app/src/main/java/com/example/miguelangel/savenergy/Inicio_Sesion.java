package com.example.miguelangel.savenergy;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Inicio_Sesion extends AppCompatActivity implements View.OnClickListener{

                                    //Inicio de la declaracion de variables
    TextInputLayout til_correo,til_password;
    ProgressDialog progressDialog;
    private boolean ses= false;
    EditText email, pass;
    Button iniciar;
    String correo_cache,password_cache,nombre_cache,id_user_cache,fecha_cache,id_tarifa_cache,id_cuota_cache,tarifa_cache,cuota_cache,tipo_usuario_cache,id_clave_cache;
                                    //Fin de la declaración de variables


                            //Metodo para lanzar el Activity Principal)
    public void iniciar_sesionOnclick() {
        Intent intent = new Intent(Inicio_Sesion.this, Principal.class);
        startActivity(intent);
        finish();
    }

                            //Método para regresar si el usuario presiono atras -->
    public void onBackPressed(){
        Intent intent = new Intent(Inicio_Sesion.this, Inicio.class);
        startActivity(intent);
        finish();
    }

                            //Metodo con 2 parametros para conectar con servidor y devuelve datos
    public String validar(String contra, String email){//Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/Login.php?contra="+contra+"&email="+email);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

    public void sesion(String pass, String email){
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject  (validar(pass,email));//Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // el correo y contraseña son correctas
                ses = true;
                id_user_cache = respuestaJSON.getJSONObject("usuario").getString("id_usuario");
                password_cache = respuestaJSON.getJSONObject("usuario").getString("contrasenia");
                correo_cache = respuestaJSON.getJSONObject("usuario").getString("email");
                nombre_cache = respuestaJSON.getJSONObject("usuario").getString("nombre");
                tipo_usuario_cache = respuestaJSON.getJSONObject("usuario").getString("tipo_usuario");
                id_clave_cache = respuestaJSON.getJSONObject("usuario").getString("id_clave_producto");
                id_cuota_cache= respuestaJSON.getJSONObject("usuario").getString("id_uota");
                cuota_cache = respuestaJSON.getJSONObject("usuario").getString("cuota");
                id_tarifa_cache = respuestaJSON.getJSONObject("usuario").getString("id_tarifa");
                tarifa_cache = respuestaJSON.getJSONObject("usuario").getString("tarifa");
                guardarUser();
            }
            else if (resultJSON.equals("2")){//el ususario no existe
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Metodo para validar correo
    public boolean validar_correo(String correo){
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            til_correo.setError(getResources().getString(R.string.correo_inv));
            return false;
        } else {
            til_correo.setError(null);
        }

        return true;
    }

                            //Método para guardar o recordar al usuario
    public void guardarUser(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id_usuario",id_user_cache);
        editor.putString("correo", correo_cache);
        editor.putString("contrasenia",password_cache);
        editor.putString("nombre",nombre_cache);
        editor.putString("tipo_usuario",tipo_usuario_cache);
        editor.putString("id_clave",id_clave_cache);
        editor.putString("id_tarifa",id_tarifa_cache);
        editor.putString("tarifa",tarifa_cache);
        editor.putString("id_cuota",id_cuota_cache);
        editor.putString("cuota",cuota_cache);
        editor.commit();
    }

                            //Método para regresar cuando pulsan boton back (toolbar)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

                            //Metodo onCreate
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__sesion);

        //Se asigna la variable que corresponde a cada componente declarado en el archivo XML

        email = (EditText) findViewById(R.id.correo);                //Asignación de variables de tipo EditText
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_correo.setError(null);
                validar_correo(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });       //Asignación de evento al teclear texto el usuario

        pass = (EditText) findViewById(R.id.password);               //Asignación de variables de tipo EditText
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });       //Asignación de evento al teclear texto el usuario

        iniciar = (Button) findViewById(R.id.iniciar_sesion);  //Asignación de variables de tipo Button
        iniciar.setOnClickListener(this);                       //Asignación de evento a boton

        til_correo = (TextInputLayout) findViewById(R.id.til_correo);       //Asignación de variables de tipo TextInputLayout
        til_password = (TextInputLayout) findViewById(R.id.til_password);   //Asignación de variables de tipo TextInputLayout

        progressDialog= new ProgressDialog(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }


    @Override
    public void onClick(View view) {
        if(view == iniciar) {
            sesion(pass.getText().toString(), email.getText().toString());
            System.out.print(ses);
            progressDialog.setTitle("Espera por favor");
            progressDialog.setMessage("Iniciando sesion...");
            progressDialog.show();
            new BackGroundJob().execute();
        }
    }
    //Metodo para determinar la duracion del Progress Dialog
    private class BackGroundJob extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(2500);//Se crea un hilo para determinar el tiempo que dura la animacion
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {//Método que se ejecuta despues de terminar la duración
            progressDialog.cancel();
            if (ses == true){
                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                iniciar_sesionOnclick();
            }else{
                til_correo.setError(getResources().getString(R.string.datos_inv));
                pass.setText("");
                til_password.setError(getResources().getString(R.string.datos_inv));
            }
        }
    }

}