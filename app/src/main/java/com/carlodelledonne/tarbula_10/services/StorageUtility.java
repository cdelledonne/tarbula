package com.carlodelledonne.tarbula_10.services;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlo on 18/11/15.
 */
public class StorageUtility {

    // TODO: considerare la seguente possibilità
    /* il parametro 'context' può essere sostituito in ogni parte del codice da 'this', che
    * rappresenta la StorageUtility, così da evitare di passare un parametro di tipo Context
    * ogni volta che si vuole  salvare o caricare una lista */
    public static List<?> loadList(Context context, String fileName) {
        List<?> list = new ArrayList<>();
        // apertura file da leggere
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // creazione dell'oggetto responsabile della lettura
        if (fis != null) {
            ObjectInputStream is = null;
            try {
                is = new ObjectInputStream(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // riempimento della mappa
            try {
                list = (ArrayList<?>) is.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // chiusura dell'oggetto che legge il file
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // chiusura del file
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void storeList(Context context, List<?> list, String fileName) {
        // apertura del file
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // creazione dello stream per scrivere
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // scrittura della lista
        try {
            os.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // chiusura dello stream
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // chiusura del file
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
