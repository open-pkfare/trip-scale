package com.pkfare.tripscale.config;

import com.pkfare.tripscale.service.DestinationRecommendationService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

  @Bean
  public ToolCallbackProvider orderTools(DestinationRecommendationService airOrderAssistanceService) {
    return MethodToolCallbackProvider.builder().toolObjects(airOrderAssistanceService).build();
  }

}
