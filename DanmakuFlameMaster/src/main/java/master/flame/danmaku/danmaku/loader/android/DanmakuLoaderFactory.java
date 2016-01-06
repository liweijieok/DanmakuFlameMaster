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

package master.flame.danmaku.danmaku.loader.android;

import master.flame.danmaku.danmaku.loader.ILoader;

/**
 * 创建Loader
 * 建议通过这种方式创建
 */
public class DanmakuLoaderFactory {

    public enum TAG{
        TAG_BILI,TAG_ACFUN;
    }
    public static ILoader create(TAG tag) {
        if (TAG.TAG_BILI.equals(tag)) {
            return BiliDanmakuLoader.instance();
        } else if(TAG.TAG_ACFUN.equals(tag))
        	return AcFunDanmakuLoader.instance();
        return null;
    }

}
