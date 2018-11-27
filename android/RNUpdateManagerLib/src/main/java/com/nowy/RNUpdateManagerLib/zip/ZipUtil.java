package com.nowy.RNUpdateManagerLib.zip;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.nowy.compressedfilelib.listener.OnCompressListener;
import com.nowy.compressedfilelib.task.DeCompressTask;

import java.io.File;

public class ZipUtil {
    public static void deCompress(File zipFile,String outputPath,OnCompressListener listener){
        String dir = new File(zipFile.getAbsolutePath()).getParent()+"/";
        if(!TextUtils.isEmpty(outputPath)){
            dir = outputPath;
        }
        new DeCompressTask(listener).execute(zipFile.getAbsolutePath(),dir);
    }
}
