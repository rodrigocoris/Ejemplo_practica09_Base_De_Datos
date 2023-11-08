package com.example.ejemplo_practica09_base_de_datos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Listado_Activity extends AppCompatActivity {

    //Instnacias de componentes
    private TextView etListado;
    //Instancia del controlador
    ControladorBD admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        //asociar la instancia con el componente
        etListado = (TextView) findViewById(R.id.txtDetalle);
        //Crecaion de la base de datos, de manera local, cuyo parametros son:
        //contexto de la aplicacion, nombre de la BD, version
        admin = new ControladorBD(this,"empresapatito.db",null,1);
        //Define el metodo de acceso a la BD
        SQLiteDatabase bd = admin.getReadableDatabase();
        //instancias del apuntador al registro de busqueda
        Cursor registro = bd.rawQuery("select * from empleado",null);
        //variable para la cantidad de registro obtenidos
        int n = registro.getCount();
        //variable para el control de datos en el textview
        int nr=1;
        //valido que existan registros de la BS
        if(n > 0){
            //Mover el cursor al inicio de los registros obtenidos
            registro.moveToFirst();
            //ciclo repetitivo para colocar la informacion dentro del Textview
            do{
                etListado.setText(etListado.getText()+"\nRegistro #"+ nr);
                etListado.setText(etListado.getText()+"\nNumero: "+ registro.getString(0) );
                etListado.setText(etListado.getText()+"\nNombre: "+ registro.getString(1) );
                etListado.setText(etListado.getText()+"\nApellidos: "+ registro.getString(2) );
                etListado.setText(etListado.getText()+"\nSueldo: "+ registro.getString(3) );
                etListado.setText(etListado.getText()+"\n");
            }while(registro.moveToNext());//si existen mas registros
        }else {
            //Mensaje informativo que no hay campos
            Toast.makeText(this,"Sin registros de empleados", Toast.LENGTH_SHORT).show();
        }
        //cerrando la BD
        bd.close();
    }//oncreate

    public void regresarPrincipal(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }//listar Registros
}//ListadoActivity