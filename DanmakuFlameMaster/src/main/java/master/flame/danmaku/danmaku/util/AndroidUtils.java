
package master.flame.danmaku.danmaku.util;

import android.app.ActivityManager;
import android.content.Context;

public class AndroidUtils {

    /**
     * 获取进程最大可能内存
     * @param context
     * @return
     */
    public static int getMemoryClass(final Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
    }
}
