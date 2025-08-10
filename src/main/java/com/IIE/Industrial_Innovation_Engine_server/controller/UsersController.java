package com.IIE.Industrial_Innovation_Engine_server.controller;

import com.IIE.Industrial_Innovation_Engine_server.dto.*;
import com.IIE.Industrial_Innovation_Engine_server.mapper.TokenMapper;
import com.IIE.Industrial_Innovation_Engine_server.mapper.UsersMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") // 实际应用中应该限制跨域来源
public class UsersController {

    private final TokenMapper tokenMapper;
    private final UsersService usersService;
    private final UsersMapper usersMapper;

    @Autowired
    public UsersController(TokenMapper tokenMapper, UsersService usersService, UsersMapper usersMapper) {
        this.tokenMapper = tokenMapper;
        this.usersService = usersService;
        this.usersMapper = usersMapper;
    }

    /**
     * 用户管理 - 查
     */
    @GetMapping("")
    public BaseResponse getUsers(@RequestHeader String Token) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return usersService.getUsers();
    }

    /**
     * 用户管理 - 修改状态
     */
    @PatchMapping("{id}/status")
    public BaseResponse changeUserStatus(
            @PathVariable Long id,
            @RequestHeader String Token,
            @RequestBody Map<String, Integer> requestBody
    ) {
        Long userId = tokenMapper.getIdByToken(Token);
        if(userId==null) {
            return BaseResponse.error("Token错误");
        }
        Integer status = requestBody.get("status");
        return usersService.changeUserStatus(id,userId,status);
    }

}
