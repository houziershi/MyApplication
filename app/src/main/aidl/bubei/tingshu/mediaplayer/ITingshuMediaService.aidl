// ITingshuMediaService.aidl
package bubei.tingshu.mediaplayer;

// Declare any non-default types here with import statements
import bubei.tingshu.mediaplayer.ITingshuMediaListener;
interface ITingshuMediaService {
    /**
     * 获取懒人听书app当前是否工作
     *@return  true 工作中，false 懒人听书退出
     */
    boolean getTingshuRunningStatus();

    /**
     * 开始播放
     */
    void play();

    /**
     * 暂停播放
     */
    void pause();

    /**
     * 上一章
     */
    void prev();

    /**
     * 下一章
     */
    void next();

    /**
     * 切换上一章时，是否还有上一章
     *@return  true 有，false 无
     */
    boolean hasPrev();

    /**
     * 切换下一章时，是否还有下一章
     *@return  true 有，false 无
     */
    boolean hasNext();

    /**
     * 获取当前播放章节总时长
     *@return  返回当前播放章节总时长 ms
     */
    long getTotalTime();

    /**
     * 获取当前播放章节已播时长
     *@return  返回当前播放章节已播时长 ms
     */
    long getCurrentTime();

    /**
     * 获取播放状态
     *@return  返回当前播放状态，true 播放，false 暂停
     */
    boolean getPlayStatus();

    /**
     * 注册媒体播放监听接口
     */
    void registerTingshuMediaListener(ITingshuMediaListener listener);

    /**
     * 反注册媒体播放监听接口
     */
    void unregisterTingshuMediaListener(ITingshuMediaListener listener);

    /**
     * 获取书籍、节目名称
     *@return  返回当前播放书籍或节目的名称，无则返回“”
     */
    String getAlbumName();

    /**
     * 获取章节名称
     *@return  返回当前播放章节的名称，无则返回“”
     */
    String getChapterName();

    /**
     * 获取书籍或节目的封面地址
     * 播放为节目时，当前播放章节有封面则返回章节封面，否则返回节目封面。
     *@return  返回当前播放书籍或节目的封面地址，无则返回“”
     */
    String getAlbumCoverPath();

    /**
     * 获取书籍或节目的作者
     *@return  返回当前播放书籍或节目的作者，无则返回“”
     */
    String getAlbumAuthor();

    /**
     * 获取书籍或节目的主播
     *@return  返回当前播放书籍或节目的主播，无则返回“”
     */
    String getAlbumAnnouncer();

}
