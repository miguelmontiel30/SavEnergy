package com.example.miguelangel.savenergy;


                //Importación de librerias necesarias
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;


public class Registrarse extends AppCompatActivity implements View.OnClickListener{

                                                // Declaracion de variables

    EditText clave, correo, pass, pass2;
    Button registrar;
    TextInputLayout til_clave,til_correo,til_contrasenia,til_repetir_contrasenia;

                                                //Fin de la declaracion de variables
                    //Método para regresar
    public void onBackPressed(){
        Intent intent = new Intent(Registrarse.this,Inicio.class);
        startActivity(intent);
        finish();
    }

                    //Metodo que abre el siguiente Activity en caso de ser válidos los datos
    public void registrarse_OnClick() {
        Intent intent = new Intent(Registrarse.this,Configuracion_f_corte.class);
        intent.putExtra("clave",String.valueOf(clave.getText()));
        intent.putExtra("correo",String.valueOf(correo.getText()));
        intent.putExtra("pass",String.valueOf(pass.getText()));
        startActivity(intent);
        finish();
    }

                    //Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
    public String validar(int code, String data){
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/registro.php?code="+code+"&data="+data);//url en donde esta guardado el archivo PHP
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;

            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

                    //Metodo para validación de clave de producto con Base de datos
    public boolean validarClave(){
        String est, id;
        boolean clv = false;
        try {
            JSONObject respuestaJSON = new JSONObject  (validar(1, clave.getText().toString()));    //Se guarda el resultado obtenido del JSON
            String resultJSON = respuestaJSON.getString("estado");                                  //guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // hay registros
                est = respuestaJSON.getJSONObject("consulta").getString("estado");
                id = respuestaJSON.getJSONObject("consulta").getString("id_usuario");
                if(est.equals("Activo")||est.equals("activo")) {    //Si el el producto esta activado
                    if(id.equals("null")||id=="null"){              // si el producto no tiene cliente asignado
                        clv = true;
                    }else {                                         //El producto ya tiene un cliente asignado
                        til_clave.setError(getResources().getString(R.string.prod_reg)); //Muestra error en TextInputLayout
                    }
                }else{//El producto no esta activado
                    til_clave.setError(getResources().getString(R.string.prod_in));      //Muestra error en TextInputLayout
                }
            }else if (resultJSON.equals("2")){      //la clave del producto es incorrecta
                til_clave.setError(getResources().getString(R.string.prod_inex));       //Muestra error en TextInputLayout
                clv=false;
            }
            }catch (JSONException e) {
            e.printStackTrace();
            }
        return clv;
    }

                    //Metodo para validar el correo en la BD
    public boolean validarCorreo(){
        boolean clv=false;
        try {
            JSONObject respuestaJSON = new JSONObject  (validar(2, correo.getText().toString()));//Se guarda el resultado obtenido del JSON
            String resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // ese correo ya existe
                til_correo.setError(getResources().getString(R.string.correo_reg)); //Muestra error en TextInputLayout
            }
            else if (resultJSON.equals("2")){//el correo no esta en la BD, lo cual es correcto para permitir el registro
                clv=true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clv;
    }

                    //Metodo para validar los caracteres usados en la contraseña
    public boolean validar_car_contrasenia(String pass){
        Pattern patron = Pattern.compile("^[A-Za-z0-9 ]+$");
        if (!patron.matcher(pass).matches() || pass.length() > 30) {
            til_contrasenia.setError(getResources().getString(R.string.pass_inc));
            return false;
        } else {
            til_contrasenia.setError(null);
        }

        return true;
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

                    //Método para validar que las contraseñas sean iguales
    public boolean validar_contrasenia(String repeat_pass){
        String pass_1 = String.valueOf(pass.getText());
        if (repeat_pass.equals(pass_1)) {
            til_repetir_contrasenia.setError(null);
            return true;
        }else{
            til_repetir_contrasenia.setError(getResources().getString(R.string.contrasenias));
        }
        return false;
    }

    //Método para regresar cuando pulsan boton back (toolbar)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    //Metodo OnClick
    @Override
    public void onClick(View view) {
        if(validarClave() && validarCorreo() && validar_contrasenia(String.valueOf(pass2.getText()))){
            registrarse_OnClick();
        }
    }

    //Metodo OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

                                                    //Asignacion de variables con componentes

        clave = (EditText) findViewById(R.id.et_clave);              //Asignación de variables de tipo EditText
        clave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    til_clave.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });       //Asignación de evento al teclear texto el usuario

        correo = (EditText) findViewById(R.id.et_correo);           //Asignación de variables de tipo EditText
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validar_correo(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });     //Asignación de evento al teclear texto el usuario

        pass = (EditText) findViewById(R.id.et_contrasenia);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validar_car_contrasenia(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pass2 = (EditText) findViewById(R.id.repeat_password);      //Asignación de variables de tipo EditText
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validar_contrasenia(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });      //Asignación de evento al teclear texto el usuario

        til_clave = (TextInputLayout) findViewById(R.id.til_clave);                                 //Asignación de variables de tipo TextInputLayout
        til_correo = (TextInputLayout) findViewById(R.id.til_correo);                               //Asignación de variables de tipo TextInputLayout
        til_contrasenia = (TextInputLayout) findViewById(R.id.til_contrasenia);                     //Asignación de variables de tipo TextInputLayout
        til_repetir_contrasenia = (TextInputLayout) findViewById(R.id.til_repetir_contrasenia);     //Asignación de variables de tipo TextInputLayout

        registrar = (Button) findViewById(R.id.registrarse);        //Asignacion de variables de tipo button
        registrar.setOnClickListener(this);

                                                    //Fin de la asignación de variables a componentes XML


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

}
