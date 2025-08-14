package com.pkfare.tripscale.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DestinationSuggestionsResponse DTO 的单元测试
 */
class DestinationSuggestionsResponseTest {
    
    private Validator validator;
    private ObjectMapper objectMapper;
    private List<DestinationSuggestion> mockSuggestions;
    
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        objectMapper = new ObjectMapper();
        
        // 设置模拟建议数据
        mockSuggestions = Arrays.asList(
            new DestinationSuggestion("日本, 京都", "丰富的文化遗产和美丽的寺庙", 0.92),
            new DestinationSuggestion("泰国, 清迈", "正宗的当地体验和美食场景", 0.78),
            new DestinationSuggestion("葡萄牙, 波尔图", "迷人的建筑和优秀的葡萄酒文化", 0.81)
        );
    }
    
    @Test
    void testValidResponse() {
        // 创建有效的响应对象
        DestinationSuggestionsResponse response = new DestinationSuggestionsResponse(
            mockSuggestions,
            "success",
            "成功获取建议",
            "user123"
        );
        
        // 验证对象
        Set<ConstraintViolation<DestinationSuggestionsResponse>> violations = validator.validate(response);
        
        // 断言
        assertTrue(violations.isEmpty(), "有效的响应对象不应该有验证错误");
        assertEquals(3, response.getTotalCount());
        assertEquals("success", response.getStatus());
        assertEquals("成功获取建议", response.getMessage());
        assertEquals("user123", response.getUserId());
        assertEquals(mockSuggestions, response.getSuggestions());
    }
    
    @Test
    void testNullSuggestions() {
        // 创建建议列表为 null 的响应对象
        DestinationSuggestionsResponse response = new DestinationSuggestionsResponse(
            null,
            "success",
            "测试消息",
            "user123"
        );
        
        // 验证对象
        Set<ConstraintViolation<DestinationSuggestionsResponse>> violations = validator.validate(response);
        
        // 断言
        assertFalse(violations.isEmpty(), "建议列表为 null 应该有验证错误");
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("建议列表不能为空"));
        assertEquals(0, response.getTotalCount());
    }
    
    @Test
    void testNullStatus() {
        // 创建状态为 null 的响应对象
        DestinationSuggestionsResponse response = new DestinationSuggestionsResponse(
            mockSuggestions,
            null,
            "测试消息",
            "user123"
        );
        
        // 验证对象
        Set<ConstraintViolation<DestinationSuggestionsResponse>> violations = validator.validate(response);
        
        // 断言
        assertFalse(violations.isEmpty(), "状态为 null 应该有验证错误");
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("状态不能为空"));
    }
    
    @Test
    void testEmptySuggestions() {
        // 创建空建议列表的响应对象
        DestinationSuggestionsResponse response = new DestinationSuggestionsResponse(
            List.of(),
            "success",
            "无建议",
            "user123"
        );
        
        // 验证对象
        Set<ConstraintViolation<DestinationSuggestionsResponse>> violations = validator.validate(response);
        
        // 断言
        assertTrue(violations.isEmpty(), "空建议列表应该是有效的");
        assertEquals(0, response.getTotalCount());
    }
    
    @Test
    void testTooManySuggestions() {
        // 创建超过最大数量的建议列表
        List<DestinationSuggestion> tooManySuggestions = Arrays.asList(new DestinationSuggestion[51]);
        for (int i = 0; i < 51; i++) {
            tooManySuggestions.set(i, new DestinationSuggestion("目的地" + i, "原因" + i, 0.5));
        }
        
        DestinationSuggestionsResponse response = new DestinationSuggestionsResponse(
            tooManySuggestions,
            "success",
            "太多建议",
            "user123"
        );
        
        // 验证对象
        Set<ConstraintViolation<DestinationSuggestionsResponse>> violations = validator.validate(response);
        
        // 断言
        assertFalse(violations.isEmpty(), "超过最大建议数量应该有验证错误");
        assertTrue(violations.iterator().next().getMessage().contains("建议数量必须在 0 到 50 之间"));
    }
    
    @Test
    void testSuccessFactoryMethod() {
        // 使用工厂方法创建成功响应
        DestinationSuggestionsResponse response = DestinationSuggestionsResponse.success(mockSuggestions, "user123");
        
        // 断言
        assertNotNull(response);
        assertEquals(mockSuggestions, response.getSuggestions());
        assertEquals("success", response.getStatus());
        assertEquals("成功获取 3 个目的地建议", response.getMessage());
        assertEquals("user123", response.getUserId());
        assertEquals(3, response.getTotalCount());
    }
    
    @Test
    void testErrorFactoryMethod() {
        // 使用工厂方法创建错误响应
        String errorMessage = "用户未找到";
        DestinationSuggestionsResponse response = DestinationSuggestionsResponse.error(errorMessage, "user123");
        
        // 断言
        assertNotNull(response);
        assertTrue(response.getSuggestions().isEmpty());
        assertEquals("error", response.getStatus());
        assertEquals(errorMessage, response.getMessage());
        assertEquals("user123", response.getUserId());
        assertEquals(0, response.getTotalCount());
    }
    
    @Test
    void testEmptyFactoryMethod() {
        // 使用工厂方法创建空结果响应
        DestinationSuggestionsResponse response = DestinationSuggestionsResponse.empty("user123");
        
        // 断言
        assertNotNull(response);
        assertTrue(response.getSuggestions().isEmpty());
        assertEquals("success", response.getStatus());
        assertEquals("未找到匹配的目的地建议", response.getMessage());
        assertEquals("user123", response.getUserId());
        assertEquals(0, response.getTotalCount());
    }
    
    @Test
    void testJsonSerialization() throws Exception {
        // 创建响应对象
        DestinationSuggestionsResponse response = DestinationSuggestionsResponse.success(mockSuggestions, "user123");
        
        // 序列化为 JSON
        String json = objectMapper.writeValueAsString(response);
        
        // 断言 JSON 包含预期字段
        assertNotNull(json);
        assertTrue(json.contains("\"suggestions\""));
        assertTrue(json.contains("\"status\":\"success\""));
        assertTrue(json.contains("\"message\""));
        assertTrue(json.contains("\"userId\":\"user123\""));
        assertTrue(json.contains("\"totalCount\":3"));
        
        // 验证可以反序列化
        DestinationSuggestionsResponse deserialized = objectMapper.readValue(json, DestinationSuggestionsResponse.class);
        assertEquals(response.getSuggestions().size(), deserialized.getSuggestions().size());
        assertEquals(response.getStatus(), deserialized.getStatus());
        assertEquals(response.getUserId(), deserialized.getUserId());
    }
    
    @Test
    void testJsonDeserialization() throws Exception {
        // 创建 JSON 字符串
        String json = """
            {
                "suggestions": [
                    {
                        "destination": "日本, 京都",
                        "reason": "丰富的文化遗产",
                        "confidence": 0.92
                    }
                ],
                "status": "success",
                "message": "测试消息",
                "userId": "user123"
            }
            """;
        
        // 反序列化
        DestinationSuggestionsResponse response = objectMapper.readValue(json, DestinationSuggestionsResponse.class);
        
        // 断言
        assertNotNull(response);
        assertEquals(1, response.getSuggestions().size());
        assertEquals("日本, 京都", response.getSuggestions().get(0).getDestination());
        assertEquals("success", response.getStatus());
        assertEquals("测试消息", response.getMessage());
        assertEquals("user123", response.getUserId());
        assertEquals(1, response.getTotalCount());
    }
    
    @Test
    void testInvalidSuggestionValidation() {
        // 创建包含无效建议的响应对象
        List<DestinationSuggestion> invalidSuggestions = Arrays.asList(
            new DestinationSuggestion("", "有效原因", 0.5), // 空目的地
            new DestinationSuggestion("有效目的地", "有效原因", 1.5) // 无效置信度
        );
        
        DestinationSuggestionsResponse response = new DestinationSuggestionsResponse(
            invalidSuggestions,
            "success",
            "测试消息",
            "user123"
        );
        
        // 验证对象
        Set<ConstraintViolation<DestinationSuggestionsResponse>> violations = validator.validate(response);
        
        // 断言
        assertFalse(violations.isEmpty(), "包含无效建议的响应应该有验证错误");
        // 应该有两个验证错误：空目的地和无效置信度
        assertTrue(violations.size() >= 2);
    }
}