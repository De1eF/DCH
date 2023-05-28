package budkevych.squareapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("square")
public class SquareController {
    @GetMapping("/{num}")
    public String square(@PathVariable Long num) {
        return String.valueOf(num * num);
    }
}
