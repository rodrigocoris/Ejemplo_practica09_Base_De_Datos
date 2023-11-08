package com.example.ejemplo_practica09_base_de_datos;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ControladorBD extends SQLiteOpenHelper {

    public ControladorBD(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase nombreBD) {
        //Query sql
        String sql = "create table empleado (numemp int primary key, nombre text, apellidos text, sueldo real)";
        try {
            //creacion de tabla, con campos y clave primaria
            nombreBD.execSQL(sql);
        }catch (SQLException e){
            Toast.makeText(null, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }

    }//oncreate

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

}
