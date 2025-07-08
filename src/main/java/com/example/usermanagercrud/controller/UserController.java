package com.example.usermanagercrud.controller;

import com.example.usermanagercrud.entity.SysUser;
import com.example.usermanagercrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 创建用户
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody SysUser user) {
        int result = userService.addUser(user);
        return result > 0 ? 
            ResponseEntity.ok("用户创建成功") : 
            ResponseEntity.badRequest().body("用户创建失败");
    }

    // 获取单个用户
    @GetMapping("/{id}")
    public ResponseEntity<SysUser> getUser(@PathVariable Long id) {
        SysUser user = userService.getUserById(id);
        return user != null ? 
            ResponseEntity.ok(user) : 
            ResponseEntity.notFound().build();
    }

    // 获取所有用户
    @GetMapping
    public ResponseEntity<List<SysUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 更新用户
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id, 
            @RequestBody SysUser user) {
        user.setUserId(id);
        int result = userService.updateUser(user);
        return result > 0 ? 
            ResponseEntity.ok("用户更新成功") : 
            ResponseEntity.badRequest().body("用户更新失败");
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        int result = userService.deleteUser(id);
        return result > 0 ? 
            ResponseEntity.ok("用户删除成功") : 
            ResponseEntity.badRequest().body("用户删除失败");
    }
}
