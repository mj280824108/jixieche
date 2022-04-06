package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcScore;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 用户评价评分
 * @Author:      liuy
 * @Date:  2019/8/23 10:40
 */
@Api(tags="用户评价评分表")
@RestController
@RequestMapping("jxcScore")
public class JxcScoreController {
       @Resource
       private JxcScoreService jxcScoreService;

       /**
        * 添加用户评价评分
        * @author  liuy
        * @param jxcScore
        * @return
        * @date    2019/8/23 10:41
        */
       @ApiOperation(httpMethod="POST", value="添加用户评价评分表")
       @PostMapping("/insert")
       public ResponseMessage<JxcScore> insert(AuthorizationUser authorizationUser, @RequestBody JxcScore jxcScore) {
              jxcScore.setSourceId(authorizationUser.getUserId());
              return this.jxcScoreService.addObj(jxcScore);
       }

       @ApiOperation(httpMethod="GET", value="获取用户评论搜索热词")
       @GetMapping("/queryScoreFireWord")
       public ResponseMessage queryFireWord() {
              return this.jxcScoreService.queryScoreFireWord();
       }

}