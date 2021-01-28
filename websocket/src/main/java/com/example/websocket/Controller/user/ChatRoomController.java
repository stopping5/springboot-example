package com.example.websocket.Controller.user;

import com.example.websocket.model.CommonResult;
import com.example.websocket.model.User;
import com.example.websocket.utils.RedisUtil;
import com.example.websocket.websocket.ChatWebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description ChatRoomController 聊天室
 * @Author stopping
 * @date: 2021/1/27 22:41
 */
@RestController
@RequestMapping("/chat")
@Api(tags = "聊天室接口")
@Slf4j
public class ChatRoomController {
    @Resource
    RedisUtil redisUtil;

    /**
     * 获取当前在线用户
     * */
    @GetMapping("/onlineUser")
    @ApiOperation(value = "获取聊天室在线人数")
    public CommonResult<List<User>> onlineUser(){
        ConcurrentMap<String, Session> userSession = ChatWebSocket.getUserSession();
        List<User> users = new ArrayList<>();
        for (String username :userSession.keySet()){
            User u = new User();
            u.setUsername(username);
            users.add(u);
        }
        return CommonResult.success(users,"success");
    }
    /**
     * 确认离线查看信息
     * */
    @GetMapping("/ackOutLineMsg/{username}")
    public CommonResult ackOutLineMsg(
            @PathVariable("username")String username){
        //清除离线缓存信息
        log.info("用户{}查看离线消息",username);
        try{
            redisUtil.delete(username);
        }catch (Exception e){
            return CommonResult.failed();
        }
        return CommonResult.success("success");
    }
}
