package ga;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArtemisPostTest {
	/**
	 * 请根据自己的appKey和appSecret更换static静态块中的三个参数. [1 host]
	 * 如果你选择的是同现场环境对接,host要修改为现场环境的ip,https端口默认为443，http端口默认为80.例如10.33.25.22:443 或者10.33.25.22:80
	 * appKey和appSecret请按照或得到的appKey和appSecret更改.
	 * TODO 调用前看清接口传入的是什么，是传入json就用doPostStringArtemis方法，是表单提交就用doPostFromArtemis方法,下载图片doPostStringImgArtemis方法
	 */
	static {
		/*ArtemisConfig.host ="10.33.47.50:443"; // 代理API网关nginx服务器ip端口
		ArtemisConfig.appKey ="24747926";  // 秘钥appkey
		ArtemisConfig.appSecret ="mcsioUGkT5GRMZTvjwA";// 秘钥appSecret*/

		//APPKEY :23751780  sercret :NkasAqofzdqcCmyzbRaa
		ArtemisConfig.host ="192.168.21.250:443"; // 代理API网关nginx服务器ip端口
		ArtemisConfig.appKey ="23751780";  // 秘钥appkey
		ArtemisConfig.appSecret ="NkasAqofzdqcCmyzbRaa";// 秘钥appSecret
	}
	/**
	 * 能力开放平台的网站路径
	 * TODO 路径不用修改，就是/artemis
	 */
	private static final String ARTEMIS_PATH = "/artemis";

	/**
	 * 调用post请求表单类型的接口,这里以简单的加法接口示例
	 *
	 * @return
	 */
	public static String callPostFormApi() {
		/**
		 * https://open8200.hikvision.com/artemis-portal/document?version=58&docId=278&apiBlock=1
		 * 根据API文档可以看出来,这是一个POST请求的Rest接口, 而且传入的参数值为是一个form值.
		 * ArtemisHttpUtil工具类提供了doPostFormArtemis这个函数, 一共六个参数在文档里写明其中的意思. 因为接口是https,
		 * 所以第一个参数path是个hashmap类型,请put一个key-value, querys为传入的参数.
		 * paramMap 为form的参数值.
		 * queryString不存在,所以传入null,accept和contentType不指定按照默认传null.
		 * header没有额外参数可不传,指定为null
		 */
		String getCamsApi = ARTEMIS_PATH + "/api/artemis/v1/plus";
		Map<String, String> paramMap = new HashMap<String, String>();// post请求Form表单参数
		paramMap.put("a", "3");
		paramMap.put("b", "2");
		Map<String, String> path = new HashMap<String, String>(2) {
			{
				put("https://", getCamsApi);
			}
		};
		String result = ArtemisHttpUtil.doPostFormArtemis(path, paramMap, null, null, null,null);
		return result;
	}

	/**
	 * 调用POST请求类型(application/json)接口，这里以入侵报警事件日志为例
	 * https://10.33.47.50:443/artemis//api/scpms/v1/eventLogs/searches
	 *
	 * @return
	 */
	public static String callPostStringApi(){
		/**
		 * https://10.33.47.50:443/artemis//api/scpms/v1/eventLogs/searches
		 * 根据API文档可以看出来，这是一个POST请求的Rest接口，而且传入的参数值为一个json
		 * ArtemisHttpUtil工具类提供了doPostStringArtemis这个函数，一共六个参数在文档里写明其中的意思，因为接口是https，
		 * 所以第一个参数path是一个hashmap类型，请put一个key-value，query为传入的参数，body为传入的json数据
		 * 传入的contentType为application/json，accept不指定为null
		 * header没有额外参数可不传,指定为null
		 *
		 */
		final String getCamsApi = ARTEMIS_PATH + "/api/scpms/v1/eventLogs/searches";
		Map<String, String> path = new HashMap<String, String>(2) {
			{
				put("https://", getCamsApi);//根据现场环境部署确认是http还是https
			}
		};

		JSONObject jsonBody = new JSONObject();
		jsonBody.put("pageNo", 1);
		jsonBody.put("pageSize", 20);
		jsonBody.put("srcType", "defence");
		jsonBody.put("startTime", "2019-02-13 08:00:00");
		jsonBody.put("endTime", "2019-02-14 08:00:00");
		jsonBody.put("327681", 1);
		jsonBody.put("srcName", "防区1");
		String body = jsonBody.toJSONString();

		String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json",null);// post请求application/json类型参数
		return result;
	}

	/**
	 * 调用POST请求下载图片类型接口，这里以获取门禁事件图片接口为例
	 * https://10.40.239.202/artemis/api/acs/v1/event/pictures
	 *
	 * @return
	 */
	public static void callPostImgStringApi(){
		/**
		 * https://10.40.239.202/artemis/api/acs/v1/event/pictures
		 * 根据API文档可以看出来，这是一个POST请求的Rest接口，而且传入的参数值为一个json
		 * ArtemisHttpUtil工具类提供了doPostStringImgArtemis这个函数，一共六个参数在文档里写明其中的意思，因为接口是https，
		 * 所以第一个参数path是一个hashmap类型，请put一个key-value，query为传入的参数，body为传入的json数据
		 * 传入的contentType为application/json，accept不指定为null
		 * header没有额外参数可不传,指定为null
		 *
		 */
		final String VechicleDataApi = ARTEMIS_PATH +"/api/acs/v1/event/pictures";
		Map<String,String> path = new HashMap<String,String>(2){
			{
				put("https://",VechicleDataApi);
			}
		};

		JSONObject jsonBody = new JSONObject();
		jsonBody.put("svrIndexCode", "30a80833-9ced-46c0-bf53-441a6121856e");
		jsonBody.put("picUri", "/pic?6dd599z4a-=s72e48118f119--7037797c5e819i0b2*=ids1*=idp3*=*d3i0t=pe4m5115-726ccd4*ef8bi12i73=");
		String body = jsonBody.toJSONString();
		System.out.println("body: "+body);
		HttpResponse result =ArtemisHttpUtil.doPostStringImgArtemis(path,body,null,null,"application/json",null);
		try {
			HttpResponse resp = result;
			if (200==resp.getStatusLine().getStatusCode()) {
				HttpEntity entity = resp.getEntity();
				InputStream in = entity.getContent();
				Tools.savePicToDisk(in, "d:/", "test.jpg");
			}else{
				System.out.println("下载出错");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//fomeResult结果示例: {"timestamp":1558162265435,"status":404,"error":"Not Found","message":"Not Found","path":"/artemis/api/artemis/v1/plus"}
		String fomeResult =callPostFormApi();
		System.out.println("fomeResult结果示例: "+fomeResult);
		method1("fomeResult结果示例: "+fomeResult);

		//StringeResult结果示例: {"code":"0x02401008","msg":"Unauthorized Consumer!"}
		String StringeResult = callPostStringApi();
		System.out.println("StringeResult结果示例: "+StringeResult);
		method1("StringeResult结果示例: "+StringeResult);
		callPostImgStringApi();
	}

	public static void method1(String info) {
		FileWriter fw = null;
		try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f=new File("C:\\Users\\Administrator\\Documents\\ul\\log.txt");
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(info);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
