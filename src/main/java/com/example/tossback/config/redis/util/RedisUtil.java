package com.example.tossback.config.redis.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper;

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T getData(String key, Class<T> classType) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            return mapper.readValue(value, classType);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> void setData(String key, T data) {
        try {
            String value = mapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Transactional
    public void addSetDataType(String key, String data) {
        redisTemplate.opsForSet().add(key, data);
    }

    @Transactional
    public <T> void addSetDataType(String key, T data) {
        String jsonData;
        try {
            jsonData = mapper.writeValueAsString(data);
            redisTemplate.opsForSet().add(key, jsonData);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Transactional
    public void deleteSetDataType(String key, String data) {
        redisTemplate.opsForSet().remove(key, data);
    }

    public List<String> getSetDataType(String key) {
        Set<String> members = redisTemplate.opsForSet().members(key);
        return Objects.requireNonNull(members).stream().map(Object::toString).toList();
    }

    public <T> List<T> getSetDataType(String key, Class<T> classType) {
        Set<String> members = redisTemplate.opsForSet().members(key);
        return Objects.requireNonNull(members).stream()
                .map(member -> mapper.convertValue(member, classType))
                .toList();
    }

    public <T> void setDataExpire(String key, T data, long duration) {
        try {
            String value = (data instanceof String stringData) ? stringData : mapper.writeValueAsString(data);
            Duration expireDuration = Duration.ofSeconds(duration);
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value, expireDuration);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public long getExpirationTime(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return (expire != null && expire > 0) ? expire : 0;
    }
}
