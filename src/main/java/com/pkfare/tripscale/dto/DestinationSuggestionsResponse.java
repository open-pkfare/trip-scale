package com.pkfare.tripscale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 目的地建议响应 DTO
 * 包装目的地建议列表，用于 MCP 工具响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationSuggestionsResponse {
    
    /**
     * 目的地建议列表
     */
    @NotNull(message = "建议列表不能为空")
    @Size(min = 0, max = 50, message = "建议数量必须在 0 到 50 之间")
    @Valid
    @JsonProperty("suggestions")
    private List<DestinationSuggestion> suggestions;
    
    /**
     * 响应状态
     */
    @NotNull(message = "状态不能为空")
    @JsonProperty("status")
    private String status;
    
    /**
     * 响应消息
     */
    @JsonProperty("message")
    private String message;
    
    /**
     * 用户 ID（用于日志和追踪）
     */
    @JsonProperty("userId")
    private String userId;
    
    /**
     * 建议总数
     */
    @JsonProperty("totalCount")
    public int getTotalCount() {
        return suggestions != null ? suggestions.size() : 0;
    }
    
    /**
     * 创建成功响应
     * 
     * @param suggestions 目的地建议列表
     * @param userId 用户 ID
     * @return 成功响应对象
     */
    public static DestinationSuggestionsResponse success(List<DestinationSuggestion> suggestions, String userId) {
        return new DestinationSuggestionsResponse(
            suggestions,
            "success",
            String.format("成功获取 %d 个目的地建议", suggestions.size()),
            userId
        );
    }
    
    /**
     * 创建错误响应
     * 
     * @param errorMessage 错误消息
     * @param userId 用户 ID
     * @return 错误响应对象
     */
    public static DestinationSuggestionsResponse error(String errorMessage, String userId) {
        return new DestinationSuggestionsResponse(
            List.of(),
            "error",
            errorMessage,
            userId
        );
    }
    
    /**
     * 创建空结果响应
     * 
     * @param userId 用户 ID
     * @return 空结果响应对象
     */
    public static DestinationSuggestionsResponse empty(String userId) {
        return new DestinationSuggestionsResponse(
            List.of(),
            "success",
            "未找到匹配的目的地建议",
            userId
        );
    }
}