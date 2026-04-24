package com.capo.sub_agent_layout_analyzer.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.capo.sub_agent_layout_analyzer.request.GenerationTemplateJsonInfoRequest;
import com.capo.sub_agent_layout_analyzer.service.ExecutingActionLayoutAnalyzerService;
import com.capo.sub_agent_layout_analyzer.utils.SseStreamUtil;


@RestController
@RequestMapping("sub-agent-layout-analyzer")
@CrossOrigin(origins = "${app.frontend.url}")
public class SubAgentController {
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final ExecutingActionLayoutAnalyzerService executingActionLayout;
	
	@Value(value="${event.name}")
	private String eventName;
	
	public SubAgentController(ExecutingActionLayoutAnalyzerService executingActionLayout) {
		this.executingActionLayout= executingActionLayout;
	}
	
	@PostMapping(path = "/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamTemplateAnalyzerGeneration(@RequestBody GenerationTemplateJsonInfoRequest request) {
		return SseStreamUtil.stream(executor, eventName, "Analyzing template is starting for prompt",
                () -> executingActionLayout.generateActionLayoutAnalyzerAsync(
                        request.getId()),
                result -> {
                    return result;
                });
	}
}
