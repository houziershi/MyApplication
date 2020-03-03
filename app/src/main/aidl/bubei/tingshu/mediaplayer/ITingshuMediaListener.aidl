// ITingshuMediaListener.aidl
package bubei.tingshu.mediaplayer;

// Declare any non-default types here with import statements

interface ITingshuMediaListener {
   /**
       *  空闲状态: 1; 2:加载未播放状态 ; 3:播放中; 4:PLAYER_PAUSE
       * and return values in AIDL.
       */
      void onPlaybackStateChanged(int state);

      /**
       * 书籍或节目切换、章节切换时发生变化回调函数
       * @param albumName 书籍或节目名称，无时返回“”
       * @param chapterName 章节名称，无时返回“”
       * @param cover 封面，无时返回“”
       */
      void onMetaInfoChanged(String resourceName,String chapterName, String cover);

      /**
       * 播放进度回调函数
       * @param totalTime 总时长 ms
       * @param currentTime 当前播放时长 ms
       */
      void onPlayProgressChanged(long totalTime,long currentTime);

      /**
       * 同步懒人听书app是否启动
       * @param isRunning true 正在工作，false 未启动
       */
      void syncAppRunningStatus(boolean isRunning);
}
