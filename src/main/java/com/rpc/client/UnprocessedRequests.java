package com.rpc.client;

import com.rpc.common.RPCResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YJL
 */
public class UnprocessedRequests {

    private static ConcurrentHashMap<String, CompletableFuture<RPCResponse>> unprocessedResponseFutures = new ConcurrentHashMap<>();


    public void put(String requestId,CompletableFuture<RPCResponse> future) {
        unprocessedResponseFutures.put(requestId, future);
    }

    public void complete(RPCResponse response){
        CompletableFuture<RPCResponse> future = unprocessedResponseFutures.remove(response.getRequestId());
        if (future != null) {
            future.complete(response);
        }
    }

}
