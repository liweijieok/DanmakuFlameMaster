package liweijie.example.com.danmudemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.widget.VideoView;

import java.io.InputStream;
import java.util.HashMap;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;

public class MainActivity extends Activity {
    private IDanmakuView view;
    private DanmakuContext context;
    private VideoView videoView;
    private BaseDanmakuParser paser;
    private BaseCacheStuffer.Proxy mCProxyStufferAdapter = new BaseCacheStuffer.Proxy() {
        @Override
        public void prepareDrawing(BaseDanmaku danmaku, boolean fromWorkerThread) {
            if (danmaku.text instanceof Spanned) {
                //加载图片显示
                //最后可以赋值给该text
            }
        }
        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            //FIXME 清除
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (IDanmakuView) findViewById(R.id.danmakuView);
        videoView = (VideoView) findViewById(R.id.videoView);
        //设置可以同时显示的最大行数
        HashMap<Integer, Integer> maxLines = new HashMap<>();
        maxLines.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        maxLines.put(BaseDanmaku.TYPE_SCROLL_LR, 5);
        maxLines.put(BaseDanmaku.TYPE_FIX_TOP, 5);

        //是否禁止重叠
        HashMap<Integer, Boolean> overloapingEnable = new HashMap<>();
        overloapingEnable.put(BaseDanmaku.TYPE_SCROLL_LR, true);
        overloapingEnable.put(BaseDanmaku.TYPE_FIX_TOP, true);

        context = DanmakuContext.create();
        context.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f).setCacheStuffer(new SpannedCacheStuffer(), mCProxyStufferAdapter).setMaximumLines(maxLines).preventOverlapping(overloapingEnable);
        if (view != null) {
            paser = createParser(this.getResources().openRawResource(R.raw.comments));
            view.setCallback(new DrawHandler.Callback() {
                @Override
                public void prepared() {
                    Log.i("Main", "prepare");
                }
                @Override
                public void updateTimer(DanmakuTimer timer) {
                    Log.i("Main", "update");
                }
                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                    Log.i("Main", "shown");
                }
                @Override
                public void drawingFinished() {
                    Log.i("Main", "finished");
                }
            });
            view.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
                @Override
                public void onDanmakuClick(BaseDanmaku latest) {
                    Log.i("Main", "click lateset");
                }
                @Override
                public void onDanmakuClick(IDanmakus danmakus) {
                    Log.i("Main", "click danmaku");
                }
            });
            view.prepare(paser, context);
            view.showFPS(true);
            view.enableDanmakuDrawingCache(true);
            if (videoView != null) {
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        view.start();
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        view.seekTo(0L);
                        videoView.seekTo(0);
                        view.start();
                        videoView.start();
                    }
                });
                videoView.setVideoPath("/storage/sdcard1/DCIM/Camera/VID_20151220_141059.mp4");
            }
        }
    }
    private BaseDanmakuParser createParser(InputStream inputStream) {
        if (inputStream == null) {
            return new BaseDanmakuParser() {
                @Override
                protected IDanmakus parse() {
                    return new Danmakus();
                }
            };
        }
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
        try {
            loader.load(inputStream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (view != null && !view.isPaused() && view.isPrepared()) {
            view.stop();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (view != null && view.isPrepared() && view.isPaused()) {
            view.resume();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (view != null) {
            // dont forget release!
            view.release();
            view = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (view != null) {
            // dont forget release!
            view.release();
            view = null;
        }
    }

}
