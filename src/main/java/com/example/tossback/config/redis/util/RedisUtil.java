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
    //하이퍼로그로그(통계용)
    public void addHyperLogLog(String key, String value) {
        redisTemplate.opsForHyperLogLog().add(key, value);
    }

    public long getHyperLogLogCount(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }
    //해쉬
    public void putHash(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }
    public void addZSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }
    //sorted set
    public Set<String> getTopRank(String key, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }
    //list
    public void pushList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }
    public List<String> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
