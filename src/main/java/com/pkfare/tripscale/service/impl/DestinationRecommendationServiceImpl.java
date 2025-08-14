package com.pkfare.tripscale.service.impl;

import com.pkfare.tripscale.config.MockDestinationConfig;
import com.pkfare.tripscale.dto.DestinationSuggestion;
import com.pkfare.tripscale.dto.DestinationSuggestionsResponse;
import com.pkfare.tripscale.service.DestinationRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 目的地推荐服务实现类，使用模拟数据
 * 基于配置的模拟数据提供目的地建议
 */
@Slf4j
@Service
public class DestinationRecommendationServiceImpl implements DestinationRecommendationService {

  private final MockDestinationConfig mockDestinationConfig;

  @Value("${mcp.server.max-suggestions:10}")
  private int maxSuggestions;

  @Autowired
  public DestinationRecommendationServiceImpl(MockDestinationConfig mockDestinationConfig) {
    this.mockDestinationConfig = mockDestinationConfig;
  }

  @Tool(description = "get destination suggestions by userId, if user have no idea where he is willing to go")
  @Override
  public DestinationSuggestionsResponse getDestinationSuggestions(String userId) {
    if (userId == null || userId.trim().isEmpty()) {
      throw new IllegalArgumentException("用户 ID 不能为空");
    }

    log.info("正在为用户获取目的地建议: {}", userId);

    try {
      // 对于模拟实现，返回配置的目的地子集
      // 在真实实现中，这将基于用户偏好进行个性化
      List<DestinationSuggestion> suggestions = new ArrayList<>(mockDestinationConfig.getSuggestions());

      // 随机打乱以为不同请求提供多样性
      Collections.shuffle(suggestions);

      // 限制到最大建议数量
      int limit = Math.min(maxSuggestions, suggestions.size());
      List<DestinationSuggestion> result = suggestions.subList(0, limit);

      log.info("为用户返回 {} 个目的地建议: {}", result.size(), userId);

      return DestinationSuggestionsResponse.success(result, userId);

    } catch (Exception e) {
      log.error("获取用户目的地建议时发生错误: {}", userId, e);
      return DestinationSuggestionsResponse.error("获取目的地建议失败: " + e.getMessage(), userId);
    }
  }
}