package com.capo.sub_agent_layout_analyzer.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.capo.sub_agent_layout_analyzer.request.BasicTemplateRequest;
import com.capo.sub_agent_layout_analyzer.response.BasicTemplateResponse;


@Service
public class ExecutingActionLayoutAnalyzerService {
	
	private final ChatClient chatClient;
	private final String systemPrompt;
	private final CallingToWebFluxMicroservice callingToWebFlux;
	
	public ExecutingActionLayoutAnalyzerService(@Qualifier("chatClientGeneral") ChatClient chatClient,
			@Qualifier("systemPrompt") String systemPrompt,
			CallingToWebFluxMicroservice callingToWebFlux) {
		this.chatClient = chatClient;
		this.systemPrompt = systemPrompt;
		this.callingToWebFlux= callingToWebFlux;
	}
	
	public CompletableFuture<String> generateActionLayoutAnalyzerAsync(String id){
		String rawCode= getBasicHTML(id); 
		return CompletableFuture.supplyAsync(() -> {
			String userMessage = "[INPUT_FORMAT: RAW_CODE] " + rawCode;
			return this.chatClient.prompt()
					.messages(new SystemMessage(systemPrompt))
					.user(userMessage)
					.call()
					.content();
		});
	} 
	
	private String getBasicHTML(String id) {
		BasicTemplateRequest request = new BasicTemplateRequest();
		request.setId(id);
		BasicTemplateResponse response = callingToWebFlux.fetchBasicTemplate(request);
		if (response == null) {
			return "";
		}
		return response.getHtmlString();
	}
}
