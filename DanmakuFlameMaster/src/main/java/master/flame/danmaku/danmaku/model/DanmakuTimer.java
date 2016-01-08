/*
 * Copyright (C) 2013 Chen Hui <calmer91@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package master.flame.danmaku.danmaku.model;

import android.util.Log;

public class DanmakuTimer {
    public long currMillisecond;

    private long lastInterval;//时间间隔

    /**
     * 更新 传入当前时间，获取上次更新和这次的时间间隔
     * @param curr
     * @return
     */
    public long update(long curr) {
        lastInterval = curr - currMillisecond;
        Log.i("Main", lastInterval + "lastInterval");
        Log.i("Main", curr + "curr");
        Log.i("Main", currMillisecond + "currMillisecond");

        currMillisecond = curr;
        return lastInterval;
    }

    /** 一般适合初始化
     * 添加一个最新时间，该时间是作为时间间隔
     * 之后把原时间加上这哥mills最为当前时间
     * @param mills
     *
     * @return
     */
    public long add(long mills) {
        return update(currMillisecond + mills);
    }

    /**
     * 最后一次间隔
     * @return
     */
    public long lastInterval() {
        return lastInterval;
    }

}
