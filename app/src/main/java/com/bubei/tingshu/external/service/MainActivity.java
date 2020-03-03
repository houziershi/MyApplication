package com.bubei.tingshu.external.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bubei.tingshu.mediaplayer.ITingshuMediaListener;
import bubei.tingshu.mediaplayer.ITingshuMediaService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "iTingshuService";
    private TextView tvChapterName;

    //author_announcer tvAuthorAnnouncer
    private TextView tvAuthorAnnouncer;
    private ImageView ivCover;

    private ImageView ivPre;
    private ImageView ivPlayPause;
    private ImageView ivNext;
    private ITingshuMediaService iTingshuMediaService;
    private boolean isBound = false;

    private ITingshuMediaListener iTingshuMediaListener = new ITingshuMediaListener.Stub() {
        @Override
        public void onPlaybackStateChanged(int state) throws RemoteException {
            Log.i(TAG,"onPlaybackStateChanged .......");

        }

        @Override
        public void onMetaInfoChanged(String resourceName, String chapterName, String cover) throws RemoteException {
            Log.i(TAG,"onMetaInfoChanged .......");
            updateMediaUI();
        }

        @Override
        public void onPlayProgressChanged(long totalTime, long currentTime) throws RemoteException {
            Log.i(TAG,"onPlayProgressChanged .......");
        }

        @Override
        public void syncAppRunningStatus(boolean isRunning) throws RemoteException {
            Log.i(TAG,"syncAppRunningStatus .......");
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            iTingshuMediaService = ITingshuMediaService.Stub.asInterface(service);
            try {
                iTingshuMediaService.registerTingshuMediaListener(iTingshuMediaListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Service Connected");
            updateMediaUI();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.i(TAG, "Service Disconnected");
        }
    };

    private void updateMediaUI() {
        if (iTingshuMediaService != null) {
            try {
                tvChapterName.setText(iTingshuMediaService.getChapterName());
                Log.i(TAG, "....author =====" + iTingshuMediaService.getAlbumAuthor() + "....... announcer =" + iTingshuMediaService.getAlbumAnnouncer());
                Log.i(TAG, "...........cover==" + iTingshuMediaService.getAlbumCoverPath());
                ivPlayPause.setImageResource(iTingshuMediaService.getPlayStatus() ? R.drawable.icon_play_nor : R.drawable.icon_stop_nor);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_bind).setOnClickListener(this);
        findViewById(R.id.tv_unbind).setOnClickListener(this);

        tvChapterName = findViewById(R.id.tv_chapter_name);
        tvAuthorAnnouncer = findViewById(R.id.tv_author_cast);
        ivCover = findViewById(R.id.iv_cover);
        ivPre = findViewById(R.id.iv_pre_song);
        ivPre.setOnClickListener(this);
        ivPlayPause = findViewById(R.id.iv_pause_play);
        ivPlayPause.setOnClickListener(this);
        ivNext = findViewById(R.id.iv_next_song);
        ivNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_bind:
                Intent intent = new Intent();
                //for android 5.0 and later, service intent must be explicit
                intent.setComponent(new ComponentName("bubei.tingshu.nissancarapp", "bubei.tingshu.mediaplayer.exo.MediaPlayerService"));
                bindService(intent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.tv_unbind:
                if (isBound) {
                    try {
                        iTingshuMediaService.unregisterTingshuMediaListener(iTingshuMediaListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    unbindService(conn);
                }
                break;
            case R.id.iv_pre_song:
                if (iTingshuMediaService != null) {
                    try {
                        iTingshuMediaService.prev();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.iv_pause_play:
                if (iTingshuMediaService != null) {
                    try {
                        if (iTingshuMediaService.getPlayStatus()) {
                            iTingshuMediaService.pause();
                        } else {
                            iTingshuMediaService.play();
                        }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.iv_next_song:
                if (iTingshuMediaService != null) {
                    try {
                        iTingshuMediaService.next();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
        }
    }
}
