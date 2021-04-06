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
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import xingyu.lu.individual.rpc.sofa.builder.SofaXServiceTriple;

/**
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
public class ProtobufServiceServer {

    public static void main(String[] args) {

        ApplicationConfig serverApp = new ApplicationConfig().setAppName("grpc-sofa-server");

        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("nacos")
                .setAddress("nacos-headless.micro-service.101.101.101.2.nip.io:30598/wave-flux");

        ServerConfig serverConfig = new ServerConfig()
                .setProtocol(RpcConstants.PROTOCOL_TYPE_TRIPLE)
                .setSerialization(RpcConstants.SERIALIZE_PROTOBUF)
                .setPort(30032);

        ProviderConfig<SofaXServiceTriple.IXService> providerConfig =
                new ProviderConfig<SofaXServiceTriple.IXService>()
                        .setApplication(serverApp)
                        .setUniqueId("Grpc-SOFA-Tri")
                        .setBootstrap(RpcConstants.PROTOCOL_TYPE_TRIPLE)
                        .setInterfaceId(SofaXServiceTriple.IXService.class.getName())
                        .setRef(new XServiceImpl())
                        .setServer(serverConfig)
                        .setRegistry(registryConfig);
        providerConfig.export();
    }
}
