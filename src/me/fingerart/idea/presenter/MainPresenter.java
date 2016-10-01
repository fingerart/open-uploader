package me.fingerart.idea.presenter;

import me.fingerart.idea.engine.net.CustomMultipartEntity;
import me.fingerart.idea.engine.net.UploadProgressListener;
import me.fingerart.idea.ui.iview.IView;
import me.fingerart.idea.utils.VerifyUtil;
import me.fingerart.idea.utils.ViewUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.TextUtils;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by FingerArt on 16/10/1.
 */
public class MainPresenter implements UploadProgressListener {

    private IView mView;

    public MainPresenter(IView view) {
        mView = view;
    }

    /**
     * 处理文件上传
     *
     * @param url
     * @param filePath
     * @param tableParams
     */
    public void handleUploadFile(String url, String filePath, TableModel tableParams) {
        if (!VerifyUtil.verifyUrl(url)) {
            mView.showE("请求的Url无效");
            return;
        }

        if (TextUtils.isEmpty(filePath)) {
            mView.showE("请选择一个上传的文件");
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            mView.showE("选择的文件不存在");
            return;
        }

        LinkedHashMap<String, String> params = ViewUtil.getTableContent(tableParams);

        upload(url, file, params);
    }

    private void upload(String url, File file, LinkedHashMap<String, String> params) {
//        ArtHttp.post().url().params().build().exceut(callback);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        CustomMultipartEntity entity = new CustomMultipartEntity(this);
        post.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onProgress(long total, long progress) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}
