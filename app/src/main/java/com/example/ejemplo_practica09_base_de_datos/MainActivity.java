package com.example.ejemplo_practica09_base_de_datos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Objetos de componentes graficos
    private EditText etNumEmp, etNombre, etApellidos, etSueldo;
    //objeto para gestion de la DB
    ControladorBD admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociar los objetos con componentes graficos
        etNumEmp = (EditText) findViewById(R.id.txtNumEmp);
        etNombre = (EditText) findViewById(R.id.txtNombre);
        etApellidos = (EditText) findViewById(R.id.txtApellidos);
        etSueldo = (EditText) findViewById(R.id.txtSueldo);

        //Creacion de objeto de clase controladordb para crear la base de datos
        //Sus parametros son: contexto, nombre de la base de datos, null y version de la BD
        //la ruta de creacion de la base de datos en el dispositivo es:
        //android/data/<paquete>/databases/<nombre_bd>

        admin = new ControladorBD(this,"empresapatito.db",null, 1);

    }//oncreate

    public void registrarEmpleado(View view){
        //Establecer l metodo de apertura de la base de datos en modo escritura
        SQLiteDatabase bd = admin.getWritableDatabase();

        //variables para obtener los valores de componentes graficos
        String nump = etNumEmp.getText().toString();
        String nomp = etNombre.getText().toString();
        String apep = etApellidos.getText().toString();
        String suep = etSueldo.getText().toString();

        //Validar que exista informacion registrada
        if(!nump.isEmpty() && !nomp.isEmpty()&& !apep.isEmpty()&& !suep.isEmpty()){
            //objeto que almacena los valores para enviar a la tabla
            ContentValues registro = new ContentValues();

            //Referencias a los datos que pasar a la BD
            //Indicando como parametros de put el nombre del campo y el valor a insertar
            //Els segundo proviene de los campos de texto
            registro.put("numemp",nump);
            registro.put("nombre",nomp);
            registro.put("apellidos",apep);
            registro.put("sueldo",suep);

            if(bd !=null){
                //alamacenar los valores en la tabla
                long x = 0;
                try{
                    x = bd.insert("empleado", null,registro);
                }catch (SQLException e){
                    Log.e("Exception", "Error: " +String.valueOf(e.getMessage()));
                }
                //cerrar la base de datos
                bd.close();
            }

            //Limpiar los campos de texto
            etNumEmp.setText("");
            etNombre.setText("");
            etApellidos.setText("");
            etSueldo.setText("");
            etNumEmp.requestFocus();

            //confirmar la operacion realizada
            Toast.makeText(this,"Empleado registrado",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Debes registrar primero los datos",Toast.LENGTH_SHORT).show();
        }//else
    }//registrar empleado

    public void Buscar(View view) {
        // Establecer el modo de apertura de la base de datos en modo escritura
        SQLiteDatabase bd = admin.getReadableDatabase();

        // Variable para búsqueda de dato para obtener información
        String nump = etNumEmp.getText().toString();

        // Validar que el campo no esté vacío
        if (!nump.isEmpty()) {
            // Objeto apunta al registro donde localice el dato, se le envía la instrucción SQL de búsqueda
            Cursor fila = bd.rawQuery("SELECT nombre, apellidos, sueldo FROM empleado WHERE numemp = " + nump, null);

            // Validar
            if (fila.moveToFirst()) {
                // Colocar en los componentes los valores encontrados
                etNombre.setText(fila.getString(0));
                etApellidos.setText(fila.getString(1));
                etSueldo.setText(fila.getString(2));
                // Cerrar la base de datos
                bd.close();
            } else {
                Toast.makeText(this, "Número de empleado no existe", Toast.LENGTH_SHORT).show();
                etNumEmp.setText("");
                etNumEmp.requestFocus();
                bd.close();
            }
        } else {
            Toast.makeText(this, "Ingresa número de empleado", Toast.LENGTH_SHORT).show();
            etNumEmp.requestFocus();
        }
    }


    public void actualizarEmpleado(View view){
        //Establecr el modo de apertura d ela base de datos en modo escritura
        SQLiteDatabase bd = admin.getWritableDatabase();

        //variables para obtener los valores de componentes graficos
        String nump = etNumEmp.getText().toString();
        String nomp = etNombre.getText().toString();
        String apep = etApellidos.getText().toString();
        String suep = etSueldo.getText().toString();

        //validar que exista informacion registrada
        if(!nump.isEmpty()&&!nomp.isEmpty()&&!apep.isEmpty()&&!suep.isEmpty()){
            //objeto que alamacena los valores para enviar a la tabla
            ContentValues registro = new ContentValues();

            //Referencias a los datos que pasar a la BD
            //indicando como parametros de put el nombre de campo y el valor e insertar
            //El segundo proviene de los campos de texto
            registro.put("numemp",nump);
            registro.put("nombre",nomp);
            registro.put("apellidos",apep);
            registro.put("sueldo",suep);

            //variable que indica el numero de registros actualizados
            int cantidad=0;
            //la isntruccion update requiere parametros para realizar la actualizacion de datos, estos son:

            //tabla, informacion por actualizar, clasula whete(condicion) y sin parametros para la clusula

            cantidad = bd.update("empleado", registro,"numemp="+nump,null);
            //cerrar la base de datos
            bd.close();

            //limpiar los campos de texto
            etNumEmp.setText("");
            etNombre.setText("");
            etApellidos.setText("");
            etSueldo.setText("");
            etNumEmp.requestFocus();

            //validar su existieron registros a borrar
            if(cantidad > 0)
                Toast.makeText(this,"Datos del empleado actualizados",Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this,"debes registrar primero los datos",Toast.LENGTH_SHORT).show();
        }//else

    }//actualziar empleado

    public void eliminarEmpleado(View view){
        //Establecer el modo de apertura de la base de datos en modo escritura
        SQLiteDatabase bd = admin.getWritableDatabase();

        //variable para busqueda de dato para eliminar
        String nump = etNumEmp.getText().toString();

        //validar que existe el campo no este vacio
        if(!nump.isEmpty()){
            //variable  que almacena que el numero de registros borrados
            int cantidad=0;

            //la instruccion delete requierre parametros oara realizar el borrado, estos son:
            //tabla, clausula whete(condicion) y sin parametros para la clausula
            cantidad = bd.delete("empleado","numemp="+nump,null);
            //cerrar la base de datos
            bd.close();

            //limpiar los datos del formulario
            etNumEmp.setText("");
            etNombre.setText("");
            etApellidos.setText("");
            etSueldo.setText("");
            etNumEmp.setText("");

            //validar si exisitieron registros a borrar
            if(cantidad > 0){
                Toast.makeText(this,"Empleado eliminado",Toast.LENGTH_SHORT).show();

            }else
                Toast.makeText(this,"El numero de empleado no existe",Toast.LENGTH_SHORT).show();
            }else {

            Toast.makeText(this,"Ingresa numero de empleado",Toast.LENGTH_SHORT).show();
        }//else
    }//eliminar empleado

    public void listarRegistros(View view){
        //objeto para conectar a otra Activity
        Intent intent = new Intent(this,Listado_Activity.class);
        //Inicar la activity
        startActivity(intent);
    }
}//MainActivity