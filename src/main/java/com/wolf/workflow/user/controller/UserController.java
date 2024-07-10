package com.wolf.workflow.user.controller;

import com.wolf.workflow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;


//    @GetMapping("/")
//    public void test(
//
//    ){
//        ResponseEntity > ApiResponse > Response(Service)
//        ResponseEntity.ok()
//                .body(ApiResponse.of("등록했습니다.", responseDto));
//    }
}
