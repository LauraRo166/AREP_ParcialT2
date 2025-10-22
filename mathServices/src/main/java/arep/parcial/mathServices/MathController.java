package arep.parcial.mathServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class MathController {
    @GetMapping("/lucasSequence")
    public MathLucas lucasSequence(@RequestParam(value = "value") Integer value) {
        List<Integer> sequence = MathServices.lucasSequence(value);
        return new MathLucas("Secuencia de Lucas", value, sequence);
    }
}