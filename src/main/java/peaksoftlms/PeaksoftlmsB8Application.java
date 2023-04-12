package peaksoftlms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class PeaksoftlmsB8Application {

    public static void main (String[] args) {
        SpringApplication.run(PeaksoftlmsB8Application.class, args);
    }

    @GetMapping
    String introduction(){
        return "introduction";
    }
}
