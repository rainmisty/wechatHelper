package com.nuoxin.virtual.rep.api.web.controller;


import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.MessageService;
import com.nuoxin.virtual.rep.api.web.controller.request.message.MessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageLinkmanResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(value = "微信消息或者短信消息相关接口")
@RequestMapping(value = "/message")
@RestController
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;


    @ApiOperation(value = "测试", notes = "测试")
    @GetMapping("/test")
    @ResponseBody
    public Object test(){
        messageService.test();
        return null;
    }


    @ApiOperation(value = "微信消息导入接口", notes = "微信消息导入接口")
    @PostMapping("/wechat/importExcel")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> importExcel(MultipartFile file){

        boolean b = messageService.importExcel(file);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(b);

        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "查询微信消息接口", notes = "查询微信消息接口")
    @PostMapping("/getList")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<PageResponseBean<MessageResponseBean>>> getList(@RequestBody MessageRequestBean bean){

        PageResponseBean<MessageResponseBean> messageList = messageService.getMessageList(bean);

        DefaultResponseBean<PageResponseBean<MessageResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(messageList);

        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "今日会话接口", notes = "今日会话接口")
    @GetMapping("/getMessageCountList/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Map<String,Integer>>> getList(@PathVariable(value = "id") Long drugUserId){

        Map<String, Integer> messageCountList = messageService.getMessageCountList(drugUserId);

        DefaultResponseBean<Map<String, Integer>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(messageCountList);

        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "消息联系人接口", notes = "消息联系人接口")
    @PostMapping("/getMessageLinkmanList")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<PageResponseBean<MessageLinkmanResponseBean>>> getMessageLinkmanList(@RequestBody MessageRequestBean bean){

        PageResponseBean<MessageLinkmanResponseBean> messageLinkmanList = messageService.getMessageLinkmanList(bean);
        DefaultResponseBean<PageResponseBean<MessageLinkmanResponseBean>> responseBean = new DefaultResponseBean<>();

        responseBean.setData(messageLinkmanList);

        return ResponseEntity.ok(responseBean);
    }


}

