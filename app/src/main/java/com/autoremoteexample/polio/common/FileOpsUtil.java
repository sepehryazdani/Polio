package com.autoremoteexample.polio.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.autoremoteexample.polio.model.PolioScannerData;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Unknown_ on 5/29/2017.
 */

public class FileOpsUtil {

    /**
     * use this method whenever you want to make sure about the temp file existence.
     * this temp file will be created after an app crashes.
     * <b>HINT: </b>it can only hold one set of workflow.
     */
    public static boolean checkTempFileExistence(String filename) {


        //TODO: it will overwrite the existed file.
        return false;
    }

    /**
     * use this method whenever you want to make sure about the temp file existence.
     * this temp file will be created after every submit the data at the end of every workflow.
     * <b>HINT: </b>it can hold more than one set of workflow.
     */
    public static boolean checkMainFileExistance(String filename) {

        //TODO: just check to make sure the file is existed or not.
        return false;
    }

    /**
     * use this method to write and save data into the file.
     *
     * @param file      destination file name.
     * @param polioData an object to write into the filke
     */
    public static void write2File(FileOutputStream fo, File file, PolioScannerData polioData) {

        Log.d("navidi", "writeFile():: starting writing 2 file");
        ObjectOutputStream outStream = null;
        ArrayList<PolioScannerData> savedDataSoFar;

        try {

//            File file = new File(ctx.getFilesDir(), Constants.FILE_NAME);
            Log.d("navidi", "writeFile():: FileName:" + file.getAbsolutePath().toString());
            if (file.exists()) {
                Log.v("navidi", "writeFile():: File exists.");

                Log.v("navidi", "writeFile():: Calling Read File...");
                savedDataSoFar = FileOpsUtil.readFile(file);
                Log.v("navidi", "writeFile():: Clearing file ...");
                new FileOutputStream(file).close();

                if (savedDataSoFar != null) {
                    Log.v("navidi", "writeFile():: Collection.size [before add a new item]=" + savedDataSoFar.size());

                    for (PolioScannerData p : savedDataSoFar) {
                        Log.v("navidi", "writeFile():: QR-Content: " + p.getQrCodeContent());
                    }
                } else {
                    Log.v("navidi", "writeFile():: nothing was stored before in the file.");
                    savedDataSoFar = new ArrayList<>();
                }
            } else {
                Log.v("navidi", "writeFile():: file does NOT exist.");
                savedDataSoFar = new ArrayList<>();
                if (!file.createNewFile()) {
//                    Toast.makeText(null,
//                            "failing creating a new file", Toast.LENGTH_LONG).show();
                    Log.e("navidi", "writeFile():: trying To Create a new file: FAILURE!");
                } else {
                    Log.d("navidi", "writeFile():: trying To Create a new file: SUCCESS!");
                }
            }

            Log.d("navidi", "writeFile():: Passed file checking");
            if (file != null) {

                Log.i("navidi", "writeFile():: file != null ");
//                FileOutputStream fo = openFileOutput(Constants.FILE_NAME, getBaseContext().MODE_PRIVATE);
//                    FileOutputStream fo = new FileOutputStream(file, true);
                Log.i("navidi", "writeFile():: Create new ObjectOutputStream");
                outStream = new ObjectOutputStream(fo);
                Log.i("navidi", "writeFile():: Add A New Item to Collection...");
                savedDataSoFar.add(polioData);
                Log.i("navidi", "writeFile():: collection.Size [AfterAddingNewitem]=" + savedDataSoFar.size());
                for (PolioScannerData p : savedDataSoFar) {
                    outStream.writeObject(p);
                    Log.i("navidi", "writeFile():: writting into file...Content:" + p.getQrCodeContent());
                }
                Log.i("navidi", "writeFile():: flushing into file");
                outStream.flush();
                Log.i("navidi", "writeFile():: close stream");
                outStream.close();

            } else {
                Log.e("navidi", "writeFile():: file == null --> OOPS! CANNOT Save anything.");
            }
        } catch (Exception ex) {
            Log.e("navidi", "writeFile():: " + ex.toString());
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (Exception ex) {
                Log.e("navidi", "writeFile():: " + ex.toString());
            }
        }


    }


    /**
     * Reads objects from a file and gives back an Arraylist full of objects that have been ssaved.
     *
     * @return
     */
    public static ArrayList<PolioScannerData> readFile(File file) {


        if (file.exists()) {

            ArrayList<PolioScannerData> polioDataList = new ArrayList<PolioScannerData>();

            ObjectInputStream inStream = null;

            try {
                Log.d("navidi", "readFile():: getting file/obj input stream.");
                inStream = new ObjectInputStream(new FileInputStream(file));

                if (inStream != null) {
                    for (int index = 0; ; index++) {

                        polioDataList.add((PolioScannerData) inStream.readObject());
                        Log.d("navidi", "readFile():: reading through file - item#" + (index + 1)); // it should be here since above method will throw EOF Exceptions whe it hit eof.
                    }


                } else {
//                    file.delete();
                    return null;
                }
//                Log.d("navidi","finish reading");
            } catch (FileNotFoundException ex) {
                Log.e("navidi", "readFile():: " + ex.toString());
            } catch (EOFException ex) {

                Log.d("navidi", "readFile():: EOF:" + polioDataList.size());


//                try {
//                    Log.d("navidi", "readFile():: EOF:: Closing Stream.");
//                    inStream.close(); // something will happened in EOF exception
                Log.d("navidi", "readFile():: EOF:: retuning data back to write method.");
                return polioDataList;
//                } catch (IOException eexx) {
//                    Log.e("navidi", "readFile():: closing Stream Exception:" + eexx.toString());
//                } catch (Exception eexx) {
//                    Log.e("navidi", "readFile():: Anyother Exception:" + eexx.toString());
//                }
            } catch (IOException ex) {
                Log.e("navidi", "readFile():: IOException:" + ex.toString());
            } catch (ClassNotFoundException ex) {
                Log.e("navidi", "readFile():: ClassNotFoundException:" + ex.toString());
            }

//            file.delete();
            return polioDataList;
        } else {
            Log.d("navidi", "readFile():: file is not existed.");
            return null;
        }

    }

    public static boolean deleteFile(File file){
        return file.delete();
    }

}
