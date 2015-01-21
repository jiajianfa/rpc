package com.itjoyee.rpc.common;

/**
 * 封装 RPC 响应
 *
 * @author itjoyee
 * @since 1.0.0
 */
public class RpcResponse {

    private String requestId;
    private Throwable error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
