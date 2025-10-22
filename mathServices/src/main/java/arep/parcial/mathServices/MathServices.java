package arep.parcial.mathServices;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MathServices {
    public static List<Integer> lucasSequence(Integer value){
        List<Integer> sequence = new ArrayList<>();

        int count = 0;
        while (count <= value){
            sequence.add(lucasSequenceRec(count));
            count = count + 1;
        }
        return sequence;
    }

    private static Integer lucasSequenceRec(Integer value){
        int count = 0;
        if (value == 0){
            return 2;
        }else if (value == 1){
            return 1;
        }else{
            return lucasSequenceRec(value-1) + lucasSequenceRec(value-2);
        }
    }
}