package com.haertz.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@Tag(name = "Example API", description = "Swagger 테스트용 API")
public class TestController {

    @Operation(summary = "Hello API", description = "이 API는 Hello 메시지를 반환합니다.")
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "Hello, " + name;
    }
    private final Random random = new Random();

    private final List<String> dogNames = List.of("뽀삐", "초코", "몽이", "콩이", "바둑이");
    private final List<String> snacks = List.of("딸기 마카롱", "초코칩 쿠키", "크로와상", "푸딩", "도넛");
    private final List<String> catMoods = List.of("고양이가 골골거려요~ 😻", "고양이가 눈을 꿈뻑거려요~ 💤", "고양이가 꼬리를 흔들어요~ 🐈");
    private final List<String> bearGreetings = List.of("곰돌이가 포옹을 해줍니다~ 🐻🤗", "곰돌이가 손을 흔들어요~ 👋", "곰돌이가 꿀을 핥아요~ 🍯");

    @Operation(summary = "Hello API", description = "이 API는 랜덤 강아지 메시지를 반환합니다.")
    @GetMapping("/dog-name")
    public String getDogName() {
        return "{ \"name\": \"" + dogNames.get(random.nextInt(dogNames.size())) + "\" }";
    }

    @GetMapping("/snack")
    public String getSnack() {
        return "{ \"snack\": \"" + snacks.get(random.nextInt(snacks.size())) + "\" }";
    }

    @GetMapping("/cat-mood")
    public String getCatMood() {
        return "{ \"mood\": \"" + catMoods.get(random.nextInt(catMoods.size())) + "\" }";
    }

    @GetMapping("/bear-hello")
    public String getBearGreeting() {
        return "{ \"message\": \"" + bearGreetings.get(random.nextInt(bearGreetings.size())) + "\" }";
    }

    @GetMapping("/rabbit-jump")
    public String getRabbitJump() {
        int jumpHeight = random.nextInt(50) + 20; // 20 ~ 70 cm
        return "{ \"height\": \"토끼가 " + jumpHeight + "cm 점프했어요! 🐇✨\" }";
    }
}
