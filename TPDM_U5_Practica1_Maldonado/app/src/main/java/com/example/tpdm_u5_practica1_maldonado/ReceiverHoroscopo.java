package com.example.tpdm_u5_practica1_maldonado;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ReceiverHoroscopo extends BroadcastReceiver {

    ConexionBD base;

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";

    public void onReceive(Context context, Intent intent) {

        base=new ConexionBD(context, "Base1", null, 1);
        //context.deleteDatabase(base.getDatabaseName());

        insertar_datos();
        //retrieves the general action to be performed and display on log
        Log.i(TAG, "Intent Received: " +intent.getAction());
        if (intent.getAction()==SMS_RECEIVED)
        {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle!=null)
            {
                Object[] mypdu = (Object[])dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i<mypdu.length; i++)
                {
                    //for build versions >= API Level 23
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        String format = dataBundle.getString("format");
                        //From PDU we get all object and SmsMessage Object using following line of code
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }
                    else
                    {
                        //<API level 23
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();

                    Toast.makeText(context, "Horoscopo para "+msg+": "+buscar(msg),Toast.LENGTH_LONG).show();
                    enviarSMS(message[i].getOriginatingAddress(),"Horoscopo para "+msg+" : "+buscar(msg),context);
                }
                Toast.makeText(context, "Message: " +msg +"\nNumber: " +phoneNo, Toast.LENGTH_LONG).show();
            }
        }
    }

    public String buscar(String zod){
        try{
            SQLiteDatabase base = this.base.getReadableDatabase();
            String[] claves = {zod};
            Cursor c = base.rawQuery("SELECT * FROM Zodiaco WHERE Horoscopo = ?",claves);
            System.out.println(c.getCount());
            if(c.moveToFirst()){
                return(c.getString(1));
            } else {
                return("No se encontraron coincidencias");
            }
        } catch (SQLiteException e){
            return (e.getMessage());
        }
    }

    private void enviarSMS(String t, String m, Context c) {
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(t,null,m,null,null);
        }catch (Exception e){
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void insertar_datos(){
        SQLiteDatabase db = this.base.getWritableDatabase();
        db.execSQL("INSERT INTO Zodiaco VALUES('Aries',        'Numero de la Suerte: 5')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Tauro',        'Numero de la Suerte: 12')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Gemenis',      'Numero de la Suerte: 3')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Cancer',       'Numero de la Suerte: 2')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Leo',          'Numero de la Suerte: 9')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Virgo',        'Numero de la Suerte: 17')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Libra',        'Numero de la Suerte:23')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Escorpio',     'Numero de la Suerte:8')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Sagitario',    'Numero de la Suerte:1')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Capricornio',  'Numero de la Suerte:4')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Acuario',      'Numero de la Suerte:15')");
        db.execSQL("INSERT INTO Zodiaco VALUES('Piscis',       'Numero de la Suerte:7')");
    }




}
