package me.fingerart.idea.engine.net;

/**
 * Created by FingerArt on 16/10/2.
 */
public interface UploadProgressListener {
    void onPre();

    void onProgress(long total, long progress);

    void onError();

    void onFinish();
}
