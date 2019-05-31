package com.example.tpdm_u5_practica1_maldonado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionBD extends SQLiteOpenHelper {


    public ConexionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Zodiaco(Horoscopo varchar(100), Frase varchar(1000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
