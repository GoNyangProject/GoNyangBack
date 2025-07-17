package com.example.tossback;

import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<CommonResponse> test() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "test123");
        return new ResponseEntity<>(new CommonResponse(map), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CommonResponse> testPost() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "test123");
        return new ResponseEntity<>(new CommonResponse(map), HttpStatus.OK);
    }

}
