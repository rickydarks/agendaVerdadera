package com.example.administrador.agenda3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Nombre;
    EditText Apellidos;
    EditText Direccion;
    EditText Correoe;
    EditText Telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 Nombre = (EditText)findViewById(R.id.nombre);
 Apellidos = (EditText)findViewById(R.id.apellidos);
 Direccion = (EditText)findViewById(R.id.direccion);
 Correoe = (EditText)findViewById(R.id.correoe);
 Telefono = (EditText)findViewById(R.id.telefono);

    }

    //dar de alta
    public  void alta(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion" , null , 1);

        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();

        String nombre = Nombre.getText().toString();
        String apellidos = Apellidos.getText().toString();
        String direccion = Direccion.getText().toString();
        String correoe = Correoe.getText().toString();
        String telefono = Telefono.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("nombre" , nombre);
        registro.put("apellidos" , apellidos);
        registro.put("direccion" , direccion);
        registro.put("correoe" , correoe);
        registro.put("telefono" , telefono);

        //los inserto en la base de datos
        sqLiteDatabase.insert("usuario" , null , registro);
        sqLiteDatabase.close();

        //ponemos los campos a vacio para insertar siguiente usuario
        Nombre.setText("");
        Apellidos.setText("");
        Direccion.setText("");
        Correoe.setText("");
        Telefono.setText("");

        Toast.makeText(this , "Datos del usuario cargandos" , Toast.LENGTH_SHORT ).show();
    }

    //busqueda por telefono
    public  void  consulta(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion" , null , 1);

        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();

        String telefono = Telefono.getText().toString();

        Cursor fila = sqLiteDatabase.rawQuery("select  nombre , apellidos, direccion , correoe  from usuario where telefono="+ telefono, null);

        if (fila.moveToFirst())
        {
            Nombre.setText(fila.getString(0));
            Apellidos.setText(fila.getString(1));
            Direccion.setText(fila.getString(2));
            Correoe.setText(fila.getString(3));

        } else
            Toast.makeText(this, "No existe ningun  usuario con ese  numero" , Toast.LENGTH_SHORT).show();

        sqLiteDatabase.close();

    }

    public  void baja(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion" , null , 1);

        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();


        String telefono = Telefono.getText().toString();

        //aqui se borra la base de datos del usuario por el telefono
        int cant = sqLiteDatabase.delete("usuario", "telefono="+ telefono , null);

        sqLiteDatabase.close();

        Nombre.setText("");
        Apellidos.setText("");
        Direccion.setText("");
        Correoe.setText("");

        if (cant == 1)
            Toast.makeText(this , "no existe el usuario" ,Toast.LENGTH_SHORT).show();
    }


    public void modificar(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion" , null , 1);

        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();

        String nombre = Nombre.getText().toString();
        String apellidos = Apellidos.getText().toString();
        String direccion = Direccion.getText().toString();
        String correoe = Correoe.getText().toString();
        String telefono = Telefono.getText().toString();

        ContentValues registro = new ContentValues();

        //actualizamos con los nuevos datos
        registro.put("nombre" , nombre);
        registro.put("apellidos" , apellidos);
        registro.put("direccion" , direccion);
        registro.put("correoe" , correoe);
        registro.put("telefono" , telefono);

        int cant = sqLiteDatabase.update("usuario" , registro , "nombre=" +nombre , null);
        sqLiteDatabase.close();

        if (cant == 1)
            Toast.makeText(this , "Datos modificados con exito" , Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this , "no existe usuario" , Toast.LENGTH_LONG).show();
    }


}
