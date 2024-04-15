package cn.edu.xidian.mytomcat;

import java.util.*;

public class Request {
    private String type;//类型
    private String url;//链接地址
    private String protocal;//协议

    private  String remoteHost;//目标ip

    private String contentType;//请求头类型

    private Map<String,String> headers = new HashMap<>(8);

    private Map<String,String> attributes = new HashMap<>(8);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", protocal='" + protocal + '\'' +
                ", contentType='" + contentType + '\'' +
                ", headers=" + headers +
                ", attributes=" + attributes +
                '}';
    }

    public static Request buildRequest(String requestStr) {
        Request request = new Request();
        //split的长度为1说明只有请求头，为2说明有请求体
        String[] split = requestStr.split("\r\n\r\n");
        //请求行 和 请求头
        String[] lineAndHeader = split[0].split("\r\n");
        String[] lines = lineAndHeader[0].split(" ");
        request.setType(lines[0]);
        request.setUrl(lines[1].substring(1));
        request.setProtocal(lines[2]);

        for(int i=1;i<lines.length;++i){
            String[] header = lineAndHeader[i].split(": ");
            request.getHeaders().put(header[0].trim().toLowerCase(),header[1].trim().toLowerCase());
        }

        request.setContentType(request.getHeaders().get("content-type"));


        //处理请求体
        if(split.length == 2)
        {

        }
        return request;
    }
}
