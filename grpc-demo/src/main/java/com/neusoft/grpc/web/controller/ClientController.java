package com.neusoft.grpc.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.grpc.app.DemoClient;
import com.neusoft.grpc.app.utils.CacheUtils;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Value("${mygrpc.server-port}")
	private int port;
	
	@Value("${mygrpc.use-ssl}")
	private boolean usessl;
	
	@Value("${mygrpc.server-host}")
	private String serverhost;
	
	@Value("${mygrpc.client-trust-cert-collection-file}")
	private String trustCertFile;
	
	@Value("${mygrpc.client-use-interceptor}")
	private boolean useInterceptor;
	
	
	@GetMapping("/create/{clientId}")
	public String create(@PathVariable("clientId") String clientId) throws IOException{
		CacheUtils.createClient(clientId, serverhost, port,usessl, trustCertFile,useInterceptor);
		return "OK";
	}
	@GetMapping("/subscribeTopic/{clientId}/{topic}")
	public String subscribeTopic(@PathVariable("clientId") String clientId, @PathVariable("topic") String topic){
		DemoClient client = CacheUtils.getClient(clientId);
		return client.subscribeTopic(topic);
	}
	@GetMapping("/cancelTopic/{clientId}/{topic}")
	public String cancelTopic(@PathVariable("clientId") String clientId, @PathVariable("topic") String topic){
		DemoClient client = CacheUtils.getClient(clientId);
		return client.cancelTopic(topic);
	}
	
	@GetMapping("/registerToServer/{clientId}")
	public String registerToServer(@PathVariable("clientId") String clientId){
		DemoClient client = CacheUtils.getClient(clientId);
		client.registerToServer();
		return "OK";
	}
	
	@GetMapping("/logoutFromServer/{clientId}")
	public String logoutFromServer(@PathVariable("clientId") String clientId){
		DemoClient client = CacheUtils.getClient(clientId);
		return client.logoutFromServer();
	}
	
	@GetMapping("/shutDown/{clientId}")
	public String shutDown(@PathVariable("clientId") String clientId) throws InterruptedException{
		CacheUtils.shutDownClient(clientId);
		return "OK";
	}
	
	@GetMapping("/list")
	public List<String> list(){
		return CacheUtils.getCallbackInfo();
	}
}
