package com.pkfare.tripscale.service;

import com.pkfare.tripscale.dto.DestinationSuggestionsResponse;

/**
 * 目的地推荐服务接口
 * 基于用户偏好提供个性化的旅行目的地建议
 */
public interface DestinationRecommendationService {

  /**
   * 获取特定用户的目的地建议
   *
   * @param userId 用户 ID
   * @return 包装的目的地建议响应
   * @throws IllegalArgumentException 如果用户 ID 为空或无效
   */
  DestinationSuggestionsResponse getDestinationSuggestions(String userId);
}