package com.example.scrollingtext;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.io.*;


import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView textoLeer;
    private EditText textoGuardar;
    private static final String FILE_NAME = "comment.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoGuardar = findViewById(R.id.articleComment);
        textoLeer = findViewById(R.id.textComment);

    }

    /**
     * metodo para boton que al pulsar muestra un EditText donde podemos insertar texto
     * @param view vista
     */
    public void MostrarEditText(View view) {
        Button buttonAddComment = findViewById(R.id.button_addComment);

        if (!textoGuardar.isEnabled()) {
            textoGuardar.setVisibility(View.VISIBLE);
            textoGuardar.setCursorVisible(true);
            textoGuardar.setHint("escribe aqui commentario");
            textoGuardar.setEnabled(true);
            textoGuardar.setInputType(InputType.TYPE_CLASS_TEXT);
            buttonAddComment.setText(R.string.guardar);
        } else {

            textoGuardar.setEnabled(false);
            buttonAddComment.setText(R.string.add_comment);
            saveFale();

            // guardamos en una instancia dispositivo que usamos para introducir datos, teclado
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            // escondemos teclado
            inputMethodManager.hideSoftInputFromWindow(this.textoLeer.getWindowToken(), 0);
        }
    }


    /**
     * metodo para guardar texto dentro de un fichero
     */
    private void saveFale(){
        String texto = textoGuardar.getText().toString();
        FileOutputStream fileOutputStream = null;
        try {
            // abrimos fichero o lo creamos
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            // guardamos fichero con flujo de byte
            fileOutputStream.write(texto.getBytes());
            Log.d("ScrollText","Fichero guardado en "+getFilesDir()+ "/"+FILE_NAME);
            Toast.makeText(getBaseContext(),"guardado con exito",Toast.LENGTH_LONG); // toast no va be?
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // si esta abiero - cerramos
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * metodo para leer un fichero
     * @param view vista
     */
    public void readFile(View view){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream =openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineaTexto ;

            StringBuilder stringBuilder = new StringBuilder();
            while ((lineaTexto=bufferedReader.readLine())!=null){
                stringBuilder.append(lineaTexto).append("\n");
            }
            textoLeer.setText(stringBuilder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}