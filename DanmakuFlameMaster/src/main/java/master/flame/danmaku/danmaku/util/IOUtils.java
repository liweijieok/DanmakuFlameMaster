package master.flame.danmaku.danmaku.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by MoiTempete.
 * ＩＯ操作类
 */
public class IOUtils {
    /**
     * 根据输入流获取对应的String，通用
     * @param in
     * @return
     */
    public static String getString(InputStream in){
        byte[] data = getBytes(in);
        return data == null? null:new String(data);
    }
    public static byte[] getBytes(InputStream in){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = in.read(buffer)) != -1)
                baos.write(buffer, 0, len);
            in.close();
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
    public static void closeQuietly(InputStream in){
    	try {
    		if(in != null)
    			in.close();
		} catch (IOException ignore) {}
    }
    public static void closeQuietly(OutputStream out){
    	try {
    		if(out != null)
    			out.close();
    	} catch (IOException ignore) {}
    }
}
