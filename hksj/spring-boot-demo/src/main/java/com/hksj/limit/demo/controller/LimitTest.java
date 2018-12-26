package com.hksj.limit.demo.controller;

import com.hksj.limit.annotation.Limit;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/com/hksj/limit")
public class LimitTest {

//    @Autowired
//    RedisTemplate<Object,Object> redisTemplate;
    String key ="user";

    @ApiOperation("产品运营数据分析-广告位数据明细")
    @PostMapping("/redisTest01")
    public String redisTest01(){
        String result="";
//        String name = (String) redisTemplate.opsForValue().get(key);
//        if(StringUtils.isEmpty(name)){
//            name="zhangsan";
//            redisTemplate.opsForValue().set("user",name,5, TimeUnit.SECONDS);
//            result="无从缓存读取="+name;
//        }else{
//            result="从缓存读取="+name;
//        }
        return result;
    }

    @PostMapping("/limit01")
    @Limit(name = "limit", key = "#id1 + \"-\" + #id2", limitPeriod = 10, limitCount = 3)
    public String limit01(String id1, String id2){
        return "xxx="+id1+id2;
    }


}
