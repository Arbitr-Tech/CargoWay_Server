package com.arbitr.cargoway.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cargos/")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Управление профилем пользователя")
public class ResponseController {
}
