// DebugController.java
package com.JavaWebmlzz.ad_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/cors-test")
    public ResponseEntity<Map<String, Object>> corsTest(HttpServletRequest request) {
        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", "CORS测试成功");
        debugInfo.put("origin", request.getHeader("Origin"));
        debugInfo.put("method", request.getMethod());
        debugInfo.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(debugInfo);
    }

    @PostMapping("/cors-test")
    public ResponseEntity<Map<String, Object>> corsTestPost(@RequestBody Map<String, Object> data,
                                                            HttpServletRequest request) {
        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", "CORS POST测试成功");
        debugInfo.put("receivedData", data);
        debugInfo.put("origin", request.getHeader("Origin"));
        debugInfo.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(debugInfo);
    }
}