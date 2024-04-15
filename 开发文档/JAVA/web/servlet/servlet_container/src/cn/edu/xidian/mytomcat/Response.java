package cn.edu.xidian.mytomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private String protocol = "HTTP/1.1";
    private Integer code = 200;
    private String msg = "OK";
    private String contentType = "text/html;charset=utf-8";
    private String contentLength;

    private Map<String,String> headers = new HashMap(){{
        put("content-type",contentType);
    }};

    private String data;
    private OutputStream os;

    public Response(){}
    public Response(String protocol, Integer code, String msg) {
        this.protocol = protocol;
        this.code = code;
        this.msg = msg;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.setContentLength(data.getBytes().length+"");
    }

    public String getContentType() {
        return getHeaders().get("content-type");
    }

    public void setContentType(String contentType) {
        this.getHeaders().put("content-type",contentType);
    }

    public String getContentLength() {
        return getHeaders().get("content-length");
    }

    public void setContentLength(String contentLength) {
        this.getHeaders().put("content-length",this.data.getBytes().length+"");
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public void addHeader(String key, String value){
        this.getHeaders().put(key,value);
    }

    //组合响应
    public String buildResponse(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getProtocol()).append(" ")
                .append(this.getCode()).append(" ")
                .append(this.getMsg()).append("\r\n");
        for(Map.Entry<String,String> entry : this.getHeaders().entrySet()){
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\r\n");
        }
        sb.append("\r\n").append(this.getData());

        return sb.toString();
    }

    //输出响应
    public void write(){
        try {
            os.write(buildResponse().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os!=null)
            {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write(String content){
        this.setData(content);
        this.write();
    }
}
