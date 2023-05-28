package budkevych.squareapi.controller;

import budkevych.squareapi.dto.SquareResponseDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
@RequestMapping("square")
public class SquareController {
    @GetMapping("/{num}")
    public SquareResponseDto square(@PathVariable Long num) {
        SquareResponseDto squareResponseDto = new SquareResponseDto();
        squareResponseDto.setValue(num * num);
        return squareResponseDto;
    }
}
