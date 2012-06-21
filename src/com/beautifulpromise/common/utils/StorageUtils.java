package com.beautifulpromise.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.os.Environment;

/**
 * @description 데이터 저장을 위한 관련된 클래스
 * @author immk
 *
 */
public class StorageUtils {

	public static final String DIR_PATH = "BeautifulPromise";
    
    public static String getFilePath(Context context){
    	
    	
		final File path = new File(Environment.getExternalStorageDirectory(), DIR_PATH);
		if (!path.exists()) {
			path.mkdir();
		}

    	SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
	    Date currentTime = new Date();
	    String dateString = formatter.format(currentTime); 
	    String filePath = path + "/" + dateString ;
	    return filePath;
    }
    
    public static String getImaegPath(Context context){
    	
    	
		final File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}
	    String filePath = path + "/" + "no_image" ;
	    return filePath;
    }
    
	public static final String ROOT_PATH = new String(Environment.getExternalStorageDirectory().getPath()); // ���� ���?����

	public static void getFileList(ArrayList<String> list, String dir, String extension) throws Exception{

		File fileDir = new File(dir);
		if(fileDir.isDirectory()){
			File[] fileList = fileDir.listFiles();
			for(int i = 0; i < fileList.length; i++){
				String apath = fileList[i].getAbsolutePath();
				if(fileList[i].isDirectory()){
					getFileList(list, apath, extension);
				}else if(apath.indexOf(extension) > 0 && apath.substring(apath.indexOf(extension)).equals(extension)){
					list.add(apath);
				}
			}
		}
	}
}
