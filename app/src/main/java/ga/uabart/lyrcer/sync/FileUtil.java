package ga.uabart.lyrcer.sync;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class FileUtil {

    public static void downloadFile(final String url, final File destFile, OkHttpClient client) {

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BufferedSink sink = null;
                BufferedSource source = null;
                try {
                    ResponseBody body = response.body();
                    source = body.source();
                    sink = Okio.buffer(Okio.sink(destFile));
                    Buffer sinkBuffer = sink.buffer();
                    int bufferSize = 8 * 1024;
                    while (source.read(sinkBuffer, bufferSize) != -1) {
                        sink.emit();
                    }
                    sink.flush();
                } catch (Exception ignored) {

                } finally {
                    Util.closeQuietly(sink);
                    Util.closeQuietly(source);
                }
            }
        });

    }
}