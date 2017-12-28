package com.ngocthach.taskmanager.common;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 27/12/2017.
 */

public class FileUtils {

    public static List<String> getListAssetsFilePath(Context context, String folderName, String filter) {
        try {
            List<String> list = new ArrayList<>();
            for(String fileName : context.getAssets().list(folderName)){
                if(fileName.endsWith(filter)) {
                    list.add(Constants.ASSETS_FOLDER_PATH + folderName + "/" + fileName);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
