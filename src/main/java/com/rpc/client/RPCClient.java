package com.rpc.client;

import com.rpc.common.RPCRequest;

import java.util.concurrent.CompletableFuture;

/**
 * @author YJL
 */
public interface RPCClient {

    CompletableFuture sendRequest(RPCRequest request);

}
