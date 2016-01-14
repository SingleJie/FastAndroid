//package com.wenjackp.android.lib.util;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//import android.content.Context;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.os.Environment;
//import android.os.StatFs;
//import android.os.storage.StorageManager;
//
//import com.rcsing.AppApplication;
//import com.rcsing.AppCacheDir;
//import com.rcsing.Configure;
//import com.rcsing.manager.ActivityManager;
//import com.rcsing.util.ExceptionLogUtil;
//import com.rcsing.util.TipHelper;
//import com.rcsing.util.Util;
//
//
//public class FileUtils {
//	public static boolean hasSDCard() {
//		return Environment.getExternalStorageState()
//				.equals(Environment.MEDIA_MOUNTED);
//	}
//	public static boolean hasExternalSDCard() {
//		String extSdCard = getExternalSDCardDir();
//		if(!Util.isEmptyStr(extSdCard))
//			return true;
//		return false;
//	}
//
//	// "/storage/sdcard/"
//	public static String getSDCardDir() {
//		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
//	}
//	public static String getExternalSDCardDir() {
//		String sSDpath = "";
//		File   fileCur = null;
//		for( String sPathCur : Arrays.asList("ext_card", "external_sd", "ext_sd", "external", "extSdCard", "externalSdCard", "sdcard2")){
//			fileCur = new File( "/mnt/", sPathCur);
//			if( fileCur.isDirectory() && fileCur.canWrite()){
//				sSDpath = fileCur.getAbsolutePath() +"/";;
//				break;
//			}
//			fileCur = null;
//		}
//		fileCur = null;
//
//		return sSDpath;
//	}
//
//	/**
//	 * 获取外置SD卡路径
//	 * @return	应该就一条记录或空
//	 */
//	public static List getExtSDCardPathList(Context context){
//		List lResult = new ArrayList();
//
////		//方法：通过反射
////		StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
////		try{
////			String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);
////			for(int i = 0; i < paths.length; i++){
////				//Log.i("iii", paths[i]);
////				String status = (String) sm.getClass().getMethod("getVolumeState", String.class).invoke(sm, paths[i]);
////				if(status.equals(android.os.Environment.MEDIA_MOUNTED)){
////					lResult.add(status);
////				}
////			}
////		}
////		catch (Exception e){
////			e.printStackTrace();
////		}
//		try {
//			Runtime rt = Runtime.getRuntime();
//			Process proc = rt.exec("mount");
//			InputStream is = proc.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			String line;
//			List<String> lsName = Arrays.asList("ext_card", "external_sd", "ext_sd", "external", "extSdCard", "externalSdCard", "sdcard2");
//			while ((line = br.readLine()) != null) {
//				if (line.contains(lsName.get(0))
//						|| line.contains(lsName.get(1))
//						|| line.contains(lsName.get(2))
//						|| line.contains(lsName.get(3))
//						|| line.contains(lsName.get(4))
//						|| line.contains(lsName.get(5))
//						|| line.contains(lsName.get(6))
//						){
//					String [] arr = line.split(" ");
//					String path = arr[1];
//					File file = new File(path);
//					if (path.indexOf(".") < 0 && file.isDirectory()){
//						lResult.add(path);
//					}
//				}
//			}
//			isr.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lResult;
//
//	}
//
//	// "/data/data/包名/files/"
//	public static String getAppFilesDir(Context context) {
//		return context.getFilesDir().getAbsolutePath() + "/";
//	}
//
//	// "/data/data/包名/cache/"
//	public static String getAppCacheDir(Context context) {
//		return context.getCacheDir().getAbsoluteFile() + "/";
//	}
//
//	public static void deleteFile(String strFileName) {
//		if(Util.isEmptyStr(strFileName))
//			return;
//		File file = new File(strFileName);
//		deleteFile(file);
//	}
//
//	public static void deleteFile(File file) {
//		if (!file.exists()) {
//            return;
//        } else {
//            if (file.isFile()) {
//                file.delete();
//                return;
//            }
//            if (file.isDirectory()) {
//                File[] childFile = file.listFiles();
//                if (childFile == null || childFile.length == 0) {
//                    file.delete();
//                    return;
//                }
//                for (File f : childFile) {
//                    deleteFile(f);
//                }
//                file.delete();
//            }
//        }
//    }
//	public static boolean copyfile(String fromFilePath, String toFilePath, Boolean rewrite ){
//		if(Util.isEmptyStr(fromFilePath) || Util.isEmptyStr(toFilePath))
//			return false;
//		File fromFile = new File(fromFilePath);
//		File toFile = new File(toFilePath);
//		return copyfile(fromFile, toFile, rewrite);
//	}
//	public static boolean copyfile(File fromFile, File toFile, Boolean rewrite ){
//
//		if(!fromFile.exists()){
//			return false;
//		}
//
//		if(!fromFile.isFile()){
//			return false;
//		}
//		if(!fromFile.canRead()){
//			return false;
//		}
//		if(!toFile.getParentFile().exists()){
//			toFile.getParentFile().mkdirs();
//		}
//		if(toFile.exists() && rewrite){
//			toFile.delete();
//		}
//
//		try {
//			FileInputStream fosfrom = new FileInputStream(fromFile);
//			FileOutputStream fosto = new FileOutputStream(toFile);
//
//			byte[] bt = new byte[1024];
//			int c;
//			while((c=fosfrom.read(bt)) > 0){
//				fosto.write(bt,0,c);
//			}
//			//关闭输入、输出流
//			fosfrom.close();
//			fosto.close();
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}
//
//	public static String readFromAssets(Context context, String fileName) {
//		try {
//			InputStream is = context.getResources().getAssets().open(fileName);
//			InputStreamReader reader = new InputStreamReader(is);
//			BufferedReader bufReader = new BufferedReader(reader);
//			String strLine = "";
//			String Result = "";
//            while((strLine = bufReader.readLine()) != null)
//                Result += strLine;
//            return Result;
//        } catch (Exception e) {
//            //e.printStackTrace();
//        	ExceptionLogUtil.logException(e);
//        }
//		return "";
//	}
//
//
//
//	public static long getDirSize(File dir) {
//		if (dir == null) {
//			return 0;
//		}
//		if (!dir.isDirectory()) {
//			return 0;
//		}
//		long dirSize = 0;
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			if (file.isFile()) {
//				dirSize += file.length();
//			} else if (file.isDirectory()) {
//				dirSize += file.length();
//				dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
//			}
//		}
//		return dirSize;
//	}
//
//    public static void makeDir(String path) {
//        File p = new File(path);
//        if (!p.exists()) {
//            p.mkdir();
//        }
//    }
//
//	public static void makeDirs(String path) {
//		File p = new File(path);
//		if (!p.exists()) {
//			p.mkdirs();
//		}
//	}
//
//	public static String getDirUnitSize(File dir) {
//		long dirSize=0;
//		dirSize=getDirSize(dir);
//		return formatDirUnitSize(dirSize);
//	}
//
//    public static String formatDirUnitSize(double bytes) {
//        double size = 0;
//        size = bytes / (1024*1024);
//        DecimalFormat df = new DecimalFormat("0.00");//	以Mb为单位保留两位小数
//        String filesize = df.format(size);
//        return filesize+"M";
//    }
//
//	/**
//	 * 小文件从String读取
//	 * @param file
//	 * @return
//	 */
//	public static String read(File file) {
//		BufferedReader reader = null;
//		FileReader in = null;
//
//		StringBuffer sb = new StringBuffer();
//		ReentrantReadWriteLock.ReadLock lock = null;
//
//		try {
//			lock = LockFactory.createReadLock(file.getAbsolutePath());
//			lock.lock();
//
//			in = new FileReader(file);
//			reader = new BufferedReader(in);
//
////			String line;
////			while ((line = reader.readLine()) != null) {
////				sb.append(line);
////			}
//
//			int len = -1;
//			char[] line = new char[1024];
//			while ((len = reader.read(line,0, 1024))  > 0) {
//				sb.append(line,0, len);
//			}
//
//			return sb.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//				}
//			}
//
//			if (reader != null) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//				}
//			}
//			if (lock != null)
//				lock.unlock();
//		}
//
//		return null;
//	}
//
//	/**
//	 * 小文件的写
//	 * @param file
//	 * @param string
//	 * @return
//	 */
//	public static boolean write(File file, String string) {
//		BufferedWriter writer = null;
//		FileWriter out = null;
//
//		ReentrantReadWriteLock.WriteLock lock = null;
//		try {
//			lock = LockFactory.createWriteLock(file.getAbsolutePath());
//			lock.lock();
//
//			if (!file.exists())
//				file.createNewFile();
//
//			out = new FileWriter(file);
//			writer = new BufferedWriter(out);
//
//			writer.write(string);
//			writer.flush();
//
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (lock != null)
//				lock.unlock();
//		}
//
//		return false;
//	}
//
//	public static File getAssetFile(String filename){
//		String tmp_filename = filename.replace("/", "_");
//		String fullPath = AppCacheDir.getAppHome()  + "/" + tmp_filename;
//		File file = new File(fullPath);
//		//if(file.exists())
//		//	return file;
//
//		InputStream is;
//		try {
//			AssetManager am = ActivityManager.getInstance().currentActivity().getAssets();
//			is = am.open(filename);
//			FileOutputStream fos = new FileOutputStream(fullPath);
//			byte[] buffer = new byte[1024];
//			int count = 0;
//			while ((count = is.read(buffer)) > 0) {
//				fos.write(buffer, 0, count);
//			}
//			fos.flush();
//			fos.close();
//			is.close();
//			file = new File(fullPath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return file;
//	}
//	public static String getAssetFilePath(String filename){
//		File file = getAssetFile(filename);
//		if(file != null)
//			return file.getPath();
//		return "";
//	}
//	public static AssetFileDescriptor getAssetFileDescriptor(String filename){
//
//		AssetManager am = ActivityManager.getInstance().currentActivity().getAssets();
//		AssetFileDescriptor afd = null;
//		try {
//			afd = am.openFd(filename);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return afd;
//	}
//
//	public static String getFileNameByUrl(String url) {
//		final int index = url.lastIndexOf("/");
//		if (index < 0)
//			return null;
//
//		return url.substring(index+1);
//	}
//	public static ArrayList<File> getFileListByLike(String dir_path, String like){
//		File[] childFile = getDirChildFiles(dir_path, false);
//		if(childFile == null || childFile.length == 0){
//			return null;
//		}
//		ArrayList<File> ls = new ArrayList<File>();
//		for(File f : childFile){
//			String fname = f.getName().toLowerCase();
//			if(fname.indexOf(like) > 0) {
//				ls.add(f);
//			}
//		}
//		return ls;
//	}
//	public static int delDirChildFilesByLike(String dir_path, String like){
//		File[] childFile = getDirChildFiles(dir_path, false);
//		if(childFile == null || childFile.length == 0){
//			return 0;
//		}
//		int cou = 0;
//		for(File f : childFile){
//			String fname = f.getName().toLowerCase();
//			if(fname.indexOf(like) > 0) {
//				f.delete();
//				cou++;
//			}
//		}
//		return cou;
//	}
//	private static double getSDAvailableSizeMB(String sdcard_path) {
//		StatFs sf = new StatFs(sdcard_path);
//		//获取单个数据块的大小(Byte)
//		long blockSize = sf.getBlockSize();
//		//空闲的数据块的数量
//		long freeBlocks = sf.getAvailableBlocks();
//		//返回SD卡空闲大小
//		//return freeBlocks * blockSize;  //单位Byte
//		//return (freeBlocks * blockSize)/1024;   //单位KB
//		return (freeBlocks * blockSize)/1024 /1024; //单位MB
//	}
//	public static int getSDCardCount(){
//
//		int cou = 0;
//		if(FileUtils.hasSDCard())
//			cou++;
//		List lsExtSdCards = FileUtils.getExtSDCardPathList(AppApplication.getContext());
//		if(lsExtSdCards != null){
//			cou += lsExtSdCards.size();
//		}
//		return cou;
//	}
//	public static double getInnerSDCardAvailableSizeMB() {
//		//取得内置SD卡文件路径
//		File path = Environment.getExternalStorageDirectory();
//		return getSDAvailableSizeMB(path.getPath());
//	}
//
//	public static boolean isExternalAvailable() {
//		String sSDpath = getExternalSDCardDir();
//		if (sSDpath != null)
//			return true;
//		return false;
//	}
//
//	public static double getExternalSDCardAvailableSizeMB() {
//		//取得外置SD卡文件路径
////		List lsExtSdCards = FileUtils.getExtSDCardPathList(AppApplication.getContext());
////		if (lsExtSdCards != null){
////			String path = (String)lsExtSdCards.get(0);
////			return getSDAvailableSizeMB(path);
////		}
//		String sSDpath = getExternalSDCardDir();
//		if(!Util.isEmptyStr(sSDpath)){
//			return getSDAvailableSizeMB(sSDpath);
//		}
//
//		return 0;
//	}
//	public static long getDirSize(String dir) {
//		if(Util.isEmptyStr(dir))
//			return 0;
//		File file = new File(dir);
//		return getDirSize(file);
//	}
////	public static long dirSize(File dir) {
////		if(dir == null || !dir.exists() || !dir.isDirectory())
////			return 0;
////		long result = 0;
////		File[] fileList = dir.listFiles();
////		for(int i = 0; i < fileList.length; i++) {
////			if(fileList[i].isDirectory()) {
////				result += dirSize(fileList [i]);
////			} else {
////				// Sum the file size in bytes
////				result += fileList[i].length();
////			}
////		}
////		return result;
////	}
//	public static File[] getDirChildFiles(String dir, boolean sortByModifiedTime){
//		if(Util.isEmptyStr(dir))
//			return null;
//		File file = new File(dir);
//		if(file == null || !file.exists() || !file.isDirectory())
//			return null;
//		File[] fileList = file.listFiles();
//		if(sortByModifiedTime) {
//			Arrays.sort(fileList, new Comparator<File>() {
//				@Override
//				public int compare(File f1, File f2) {
//					return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
//				}
//			});
//		}
//
//		return fileList;
//	}
//	public static int delDirChildFiles(String dir){
//		File[] fileList = getDirChildFiles(dir, false);
//		if(fileList == null || fileList.length == 0)
//			return 0;
//
//		int cou = 0;
//		for(int i = 0; i < fileList.length; i++) {
//			File f = fileList[i];
//			if (!f.isDirectory()) {
//				f.delete();
//				cou++;
//			}
//		}
//		return cou;
//	}
//	public static int delDirOldestChildFiles(String dir, long oldMillis){
//		File[] fileList = getDirChildFiles(dir, true);
//		if(fileList == null || fileList.length == 0)
//			return 0;
//
//		int cou = 0;
//		for(int i = 0; i < fileList.length; i++) {
//			File f = fileList[i];
//			if(!f.isDirectory()) {
//				// Sum the file size in bytes
//				if(oldMillis > 0) {
//					boolean isOldest = (f.lastModified() <= oldMillis);
//					if (isOldest) {
//						f.delete();
//						cou += 1;
//					} else {
//						break;
//					}
//				}
//				else {
//					f.delete();
//				}
//			}
//		}
//		return cou;
//	}
//	public static void delDirChildFilesOfOldest(String dir, int num){
//
//		File[] fileList = FileUtils.getDirChildFiles(dir, true);
//		if(fileList == null || fileList.length == 0)
//			return;
//
//		int size = fileList.length;
//		int cou = 0;
//		for(int i = 0; i < size; i++) {
//			File f = fileList[i];
//			if (!f.isDirectory()) {
//				f.delete();
//				cou++;
//			}
//			if( cou >= num )
//				break;
//		}
//	}
//
//	public static void delDirChildFilesOfOldest2(String dir, int numOfRecent){
//
//		File[] fileList = FileUtils.getDirChildFiles(dir, true);
//		if(fileList == null || fileList.length == 0)
//			return;
//
//		int size = fileList.length;
//		int cou = 0;
//		for(int i = 0; i < size; i++) {
//			if( (size-cou) <= numOfRecent )
//				break;
//			File f = fileList[i];
//			if (!f.isDirectory()) {
//				f.delete();
//				cou++;
//			}
//		}
//	}
//}
