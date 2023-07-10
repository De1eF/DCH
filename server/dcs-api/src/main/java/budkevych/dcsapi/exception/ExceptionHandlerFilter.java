package budkevych.dcsapi.exception;

import budkevych.dcsapi.dto.response.ExceptionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Throwable e) {
            ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.builder()
                    .exception(e.getClass().getName())
                    .message(e.getMessage())
                    .build();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(exceptionResponseDto)
            );
        }
    }
}
