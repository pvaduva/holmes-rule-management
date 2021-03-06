/**
 * Copyright 2017 ZTE Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.holmes.rulemgt.bolt.enginebolt;

import java.io.IOException;
import java.util.HashMap;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jvnet.hk2.annotations.Service;
import org.onap.holmes.common.utils.GsonUtil;
import org.onap.holmes.common.utils.HttpsUtils;
import org.onap.holmes.rulemgt.bean.request.CorrelationCheckRule4Engine;
import org.onap.holmes.rulemgt.bean.request.CorrelationDeployRule4Engine;
import org.onap.holmes.rulemgt.constant.RuleMgtConstant;

@Slf4j
@Service
public class EngineService {

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";
    private static final String PORT = ":9102";

    protected HttpResponse delete(String packageName, String ip) throws Exception {
        HashMap headers = createHeaders();
        String url = getRequestPref() + ip + PORT + RuleMgtConstant.ENGINE_PATH + "/" + packageName;
        CloseableHttpClient httpClient = null;
        HttpDelete httpDelete = new HttpDelete(url);
        try {
            httpClient = HttpsUtils.getConditionalHttpsClient(HttpsUtils.DEFUALT_TIMEOUT);
            return HttpsUtils.delete(httpDelete, headers, httpClient);
        } finally {
            httpDelete.releaseConnection();
            closeHttpClient(httpClient);
        }
    }

    protected HttpResponse check(CorrelationCheckRule4Engine correlationCheckRule4Engine, String ip)
            throws Exception {
        String content = GsonUtil.beanToJson(correlationCheckRule4Engine);
        HashMap headers = createHeaders();
        String url = getRequestPref() + ip + PORT + RuleMgtConstant.ENGINE_PATH;
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            httpClient = HttpsUtils.getConditionalHttpsClient(HttpsUtils.DEFUALT_TIMEOUT);
            return HttpsUtils.post(httpPost, headers, new HashMap<>(), new StringEntity(content), httpClient);
        } finally {
            httpPost.releaseConnection();
            closeHttpClient(httpClient);
        }
    }

    protected HttpResponse deploy(CorrelationDeployRule4Engine correlationDeployRule4Engine, String ip) throws Exception {
        String content = GsonUtil.beanToJson(correlationDeployRule4Engine);
        HashMap headers = createHeaders();
        String url = getRequestPref() + ip + PORT + RuleMgtConstant.ENGINE_PATH;
        CloseableHttpClient httpClient = null;
        HttpPut httpPut = new HttpPut(url);
        try {
            httpClient = HttpsUtils.getConditionalHttpsClient(HttpsUtils.DEFUALT_TIMEOUT);
            return HttpsUtils.put(httpPut, headers, new HashMap<>(), new StringEntity(content),httpClient);
        } finally {
            closeHttpClient(httpClient);
        }
    }

    private void closeHttpClient(CloseableHttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.warn("Failed to close http client!");
            }
        }
    }

    private HashMap<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON);
        headers.put("Accept", MediaType.APPLICATION_JSON);
        return headers;
    }

    private String getRequestPref(){
        return HttpsUtils.isHttpsEnabled() ? HTTPS : HTTP;
    }
}
