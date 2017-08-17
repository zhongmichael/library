package com.chinaredstar.core.okhttp.log;

import android.text.TextUtils;

import com.chinaredstar.core.utils.LogUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            StringBuilder log = new StringBuilder();
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();

            log.append("url : " + clone.request().url());
            log.append("\n");
            log.append("code : " + clone.code());
            log.append("\n");
            log.append("protocol : " + clone.protocol());
            log.append("\n");
            if (!TextUtils.isEmpty(clone.message())) {
                log.append("message : " + clone.message());
                log.append("\n");
            }

            ResponseBody body = clone.body();

            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    log.append("responseBody's contentType : " + mediaType.toString());
                    log.append("\n");

                    if (isText(mediaType)) {
                        String resp = body.string();
                        if (mediaType.subtype().equals("json")){
                            log.append("responseBody's content : \n" );
                            log.append(formatJson(decodeUnicode(resp)));
                        }else{
                            log.append("responseBody's content : \n" + resp);
                        }
                        LogUtil.i(log.toString());
                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        log.append("responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtil.i(log.toString());
        } catch (Exception e) {
//            e.printStackTrace();
            LogUtil.e(e.getMessage());
        }
        return response;
    }

    private void logForRequest(Request request) {
        try {
            Headers headers = request.headers();
            StringBuilder log = new StringBuilder();
            log.append("method : " + request.method());
            log.append("\n");
            log.append("url : " + request.url().toString());
            log.append("\n");
            if (headers != null && headers.size() > 0) {
                log.append("headers : " + headers.toString());
                log.append("\n");
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    log.append("requestBody's contentType : " + mediaType.toString());
                    log.append("\n");
                    if (isText(mediaType)) {
                        if (mediaType.subtype().equals("json")){
                            log.append("requestBody's content : \n");
                            log.append(formatJson(decodeUnicode(bodyToString(request))));
                        }else {
                            log.append("requestBody's content : \n" + bodyToString(request));
                        }

                    } else {
                        log.append("requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtil.i(log.toString());
        } catch (Exception e) {
//            e.printStackTrace();
            LogUtil.e(e.getMessage());
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr
     *            需要格式化的json串
     * @return 格式化后的json串
     */
    private static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            // 遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                // 遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                // 遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString
     * @return 转化后的结果.
     */
    private static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}
