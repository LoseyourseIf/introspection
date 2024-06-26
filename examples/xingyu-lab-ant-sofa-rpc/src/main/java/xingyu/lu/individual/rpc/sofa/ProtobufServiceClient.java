/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xingyu.lu.individual.rpc.sofa;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import io.grpc.StatusRuntimeException;
import xingyu.lu.individual.rpc.sofa.builder.GrpcXReply;
import xingyu.lu.individual.rpc.sofa.builder.GrpcXRequest;
import xingyu.lu.individual.rpc.sofa.builder.SofaXServiceTriple;

/**
 *
 */
public class ProtobufServiceClient {

    public static void main(String[] args) {
        ApplicationConfig clientApp = new ApplicationConfig().setAppName("grpc-sofa-client");

        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("nacos")
                .setAddress("localhost:8848");

        ConsumerConfig<SofaXServiceTriple.IXService> consumerConfig =
                new ConsumerConfig<SofaXServiceTriple.IXService>();

        consumerConfig.setInterfaceId(SofaXServiceTriple.IXService.class.getName())
                .setProtocol(RpcConstants.PROTOCOL_TYPE_TRIPLE)
                .setSerialization(RpcConstants.SERIALIZE_PROTOBUF)
                .setRegistry(registryConfig)
                .setApplication(clientApp);

        SofaXServiceTriple.IXService ixService = consumerConfig.refer();


        GrpcXReply xReply = null;

        while (true) {
            try {
                try {
                    GrpcXRequest xRequest = GrpcXRequest.newBuilder().setAppId("grpc-sofa-client").build();
                    xReply = ixService.grpcXCall(xRequest);
                    System.out.println("Invoke Success Reply {} " + xReply.getBizData());
                } catch (StatusRuntimeException e) {
                    System.out.println("RPC failed: {}" + e.getStatus());
                } catch (Throwable e) {
                    System.out.println("Unexpected RPC call breaks" + e);
                }
            } catch (Exception e) {
                System.out.println("Unexpected RPC call breaks" + e);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
