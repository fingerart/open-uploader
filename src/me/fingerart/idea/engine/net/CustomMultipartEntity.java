package me.fingerart.idea.engine.net;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by FingerArt on 16/10/2.
 */
public class CustomMultipartEntity extends MultipartEntity {
    private UploadProgressListener mListener;

    public CustomMultipartEntity(HttpMultipartMode mode, String boundary, Charset charset, UploadProgressListener listener) {
        super(mode, boundary, charset);
        mListener = listener;
    }

    public CustomMultipartEntity(HttpMultipartMode mode, UploadProgressListener listener) {
        super(mode);
        mListener = listener;
    }

    public CustomMultipartEntity(UploadProgressListener listener) {
        mListener = listener;
    }

    public CustomMultipartEntity() {
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(new ProgressOutputStream(outstream, mListener));
    }

    private class ProgressOutputStream extends OutputStream {
        private OutputStream out;
        private UploadProgressListener mListener;
        private long progress;

        public ProgressOutputStream(OutputStream outstream, UploadProgressListener listener) {
            out = outstream;
            mListener = listener;
            progress = 0;
        }

        @Override
        public void write(int b) throws IOException {
            out.write(b);
            progress++;
            mListener.onProgress(0, progress);
        }

        @Override
        public void write(@NotNull byte[] b) throws IOException {
            out.write(b);
            progress += b.length;
            mListener.onProgress(0, progress);
        }

        @Override
        public void write(@NotNull byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            progress += len;
            mListener.onProgress(0, progress);
        }
    }
}
