package com.gw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gw.common.Json;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public static HttpJson easemobPost(String url, String token, JSONObject jsonParam) {
		HttpJson httpJson = new HttpJson();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(6000).setConnectionRequestTimeout(3000)
				.setSocketTimeout(6000).build();
		post.setConfig(requestConfig);
		post.addHeader("Authorization", "Bearer " + token);
		try {
            CloseableHttpResponse res = getClient().execute(post);
            httpJson = getResJson(res);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
		}
		return httpJson;
	}

    public static HttpJson easemobPost2(String url, String token, JSONArray jsonParam) {
		HttpJson httpJson = new HttpJson();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);
		post.addHeader("Authorization", "access_token" + token);
		try {
            HttpResponse res = getClient().execute(post);
			int statusCode = res.getStatusLine().getStatusCode();// 返回状态
			// 如果服务器成功地返回响应
            String responseContent; // 响应内容
			HttpEntity httpEntity = res.getEntity();
			responseContent = EntityUtils.toString(httpEntity, "UTF-8");
			if (statusCode == 200) {
				httpJson.setMsg(responseContent);
				httpJson.setSuccess(true);
			} else {
				httpJson.setSuccess(false);
				httpJson.setMsg("同步失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpJson;
	}

    public static HttpJson easemobGet(String url, String token) {
		HttpJson httpJson = new HttpJson();
		HttpGet post = new HttpGet(url);
		post.addHeader("Authorization", "Bearer " + token);
		try {
            CloseableHttpResponse res = getClient().execute(post);
            httpJson = getResJson(res);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
		}
		return httpJson;
	}

    public static HttpJson easemobPut(String url, String token, JSONObject jsonParam) {
		HttpJson httpJson = new HttpJson();
		HttpPut post = new HttpPut(url);
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);
		post.addHeader("Authorization", "Bearer " + token);
		try {
            CloseableHttpResponse res = getClient().execute(post);
            httpJson = getResJson(res);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
		}
		return httpJson;
	}

    public static HttpJson easemobDelete(String url, String token, JSONObject jsonParam) {
		HttpJson httpJson = new HttpJson();
		HttpDelete post = new HttpDelete(url);
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.addHeader("Authorization", "Bearer " + token);
		try {
            CloseableHttpResponse res = getClient().execute(post);
            httpJson = getResJson(res);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
		}
		return httpJson;
	}

	public static HttpJson httpGet(String url) {
        HttpJson httpJson;
		HttpGet httpGet = new HttpGet(url);
		try {
			// httpGet.addHeader("Content-type", "application/x-www-form-urlencoded");
			httpGet.addHeader("Content-type", "application/json");
			httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727");//伪装成浏览器请求
            HttpResponse response = getClient().execute(httpGet);
            httpJson = explainHttpRes(response);
		} catch (Exception e) {
            httpJson = new HttpJson();
            log.error(e.getMessage(), e);
		}

		return httpJson;
	}

	public static HttpJson httpPostRaw(String url, String body) {
        HttpJson httpJson;
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new StringEntity(body, "UTF-8"));
            HttpResponse response = getClient().execute(httppost);
            httpJson = explainHttpRes(response);
		} catch (Exception e) {
            httpJson = new HttpJson();
            log.error(e.getMessage(), e);
		}

		return httpJson;
	}

    public static Json doPost(String url, String body) {
        Json json;
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(body, "UTF-8"));
            HttpResponse response = getClient().execute(httppost);
            json = explainResponse(response);
		} catch (Exception e) {
            json = new Json();
			json.setMsg(UtilMessage.HTTP_CLIENT_ERROR);
            log.error(e.getMessage(), e);
        }
        return json;
    }

    public static Json doGet(String url) {
        Json json;
		try {
			HttpGet httpget = new HttpGet(url);
            HttpResponse response = getClient().execute(httpget);
            json = explainResponse(response);
		} catch (Exception e) {
            json = new Json();
			json.setMsg(UtilMessage.HTTP_CLIENT_ERROR);
            log.error(e.getMessage(), e);
        }
        return json;
    }

    public static Json thirdGet(String url, String token) {
        Json json;
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("token", token);
//			httpget.addHeader("Content-type", "application/json");
            HttpResponse response = getClient().execute(httpget);
            json = explainResponse(response);
		} catch (Exception e) {
            json = new Json();
			json.setMsg(UtilMessage.HTTP_CLIENT_ERROR);
            log.error(e.getMessage(), e);
        }
        return json;
    }

    public static HttpJson doPost(String url) {
        HttpJson httpJson;
        HttpPost httppost = new HttpPost(url);
        try {
            HttpResponse response = getClient().execute(httppost);
            httpJson = explainHttpRes(response);
        } catch (Exception e) {
            httpJson = new HttpJson();
            log.error(e.getMessage(), e);
        }

        return httpJson;
    }

    private static Json explainResponse(HttpResponse response) throws IOException {
        Json json = new Json();
        int _statusCode = response.getStatusLine().getStatusCode();
        if (_statusCode == HttpStatus.SC_OK || _statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            /* 读返回数据 */
            String conResult = EntityUtils.toString(response.getEntity(), "UTF-8");
            EntityUtils.consume(response.getEntity());//使用copy的方式解析完response,务必consume
            log.error("conResult:" + conResult);
            json.setSuccess(true);
            json.setObj(conResult);
        } else {
            json.setMsg(UtilMessage.HTTP_CLIENT_UNRESPONSIVE);
            log.error("doGet(String url) error" + _statusCode);
        }
        return json;
    }

    private static HttpJson explainHttpRes(HttpResponse response) throws IOException {
        HttpJson httpJson = new HttpJson();
        int _statusCode = response.getStatusLine().getStatusCode();
        if (_statusCode == HttpStatus.SC_OK) {
            /* 读返回数据 */
            String conResult = EntityUtils.toString(response.getEntity(), "UTF-8");
            EntityUtils.consume(response.getEntity());//使用copy的方式解析完response,务必consume
            httpJson.setSuccess(true);
            httpJson.setMsg(conResult);
        } else {
            httpJson.setSuccess(false);
            httpJson.setMsg("错误码：" + _statusCode);
        }
        return httpJson;
    }

    private static HttpJson getResJson(CloseableHttpResponse res) {
        HttpJson httpJson = new HttpJson();
        try {
            JSONObject jsonObject = null;
            if (Util.isNotEmpty(res.getEntity())) {
                HttpEntity entity = res.getEntity();
                jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
                EntityUtils.consume(entity);//使用copy的方式解析完response,务必consume
            }
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (Util.isNotEmpty(jsonObject) && jsonObject.get("errcode") != null) {
                    httpJson.setMsg(jsonObject.get("errcode").toString());
                } else {// 正常情况下
                    httpJson.setSuccess(true);
                    httpJson.setMsg("操作成功");
                    httpJson.setObj(jsonObject);
                }
            } else {
                httpJson.setMsg("错误码：" + res.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                res.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        return httpJson;
    }

    public static HttpJson thirdPost(String url, String token, JSONObject jsonParam) {
		HttpJson httpJson = new HttpJson();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);
		post.addHeader("Authorization", "Bearer " + token);
		try {
            HttpResponse res = getClient().execute(post);
            httpJson = explainHttpRes(res);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
		}
		return httpJson;
	}

    public static Json thirdPost(String url, String token, String body) throws IOException {
		Json json = new Json();
		log.info("url:"+url+",body:"+body.toString());
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(body, "UTF-8"));
			httppost.addHeader("token", token);
			httppost.addHeader("Content-type", "application/json");
            HttpResponse response = getClient().execute(httppost);
            json = explainResponse(response);
		} catch (Exception e) {
			json.setMsg(UtilMessage.HTTP_CLIENT_ERROR);
            log.error(e.getMessage(), e);
        }
        return json;
	}

	/**
	 * post请求
	 *
	 * @param url
	 * @param id
	 * @return
	 */
	public static Json doPostFour(String url, String id) {
		Map map = new HashMap();
		Json json = new Json();
		log.info("url:"+url+",id:"+id);
		// 获得Http客户端
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// 参数
		StringBuffer params = new StringBuffer();
		params.append("id="+id);

		// 创建Post请求
		HttpPost httpPost = new HttpPost(url + "?" + params);

		// 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			log.info("响应状态为:" + response.getStatusLine());
			int statusCode = response.getStatusLine().getStatusCode();
			String content = EntityUtils.toString(responseEntity);
			if (statusCode  == HttpStatus.SC_OK) {
				log.info("响应内容长度为:" + responseEntity.getContentLength());
				log.info("响应内容为:" + content);
				map.put("Code",statusCode);
				map.put("Msg",content);
				String jsonString = JSON.toJSONString(map);
				json.setSuccess(true);
				json.setObj(jsonString);
				return json;
			}else {
				json.setMsg(UtilMessage.HTTP_CLIENT_UNRESPONSIVE);
				log.error("doPostFour(String url, String body) error" + statusCode);
				return json;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error("doPostFour(String url, String id) error" + e.getMessage());
			return json;
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("doPostFour(String url, String id) error" + e.getMessage());
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("doPostFour(String url, String id) error" + e.getMessage());
			return json;
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    public static CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(30);
        connectionManager.setDefaultMaxPerRoute(10);//例如默认每路由最高50并发，具体依据业务来定
        connectionManager.setValidateAfterInactivity(5000);
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;//如果没有约定，则默认定义时长为60s
            }
        };
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(myStrategy)
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(6000).setConnectionRequestTimeout(3000)
                        .setSocketTimeout(6000).build())
                .build();

    }


}
