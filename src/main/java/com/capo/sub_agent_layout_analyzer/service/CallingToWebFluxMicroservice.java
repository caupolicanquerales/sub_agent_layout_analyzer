package com.capo.sub_agent_layout_analyzer.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.capo.sub_agent_layout_analyzer.request.BasicTemplateRequest;
import com.capo.sub_agent_layout_analyzer.response.BasicTemplateResponse;

@Service
public class CallingToWebFluxMicroservice {
	
	private final WebClient webClient;
	private static final String GET_BASIC_TEMPLATE = "/get-basic-template";
	
    public CallingToWebFluxMicroservice(WebClient webClient) {
        this.webClient = webClient;
    }

    public BasicTemplateResponse fetchBasicTemplate(BasicTemplateRequest request) {
        return webClient.method(HttpMethod.POST)
                 .uri(GET_BASIC_TEMPLATE)
                 .bodyValue(request)
                 .retrieve()
                 .bodyToMono(BasicTemplateResponse.class)
                 .doOnSuccess(body -> {
                	 
                 })
                 .block();
    }
}
