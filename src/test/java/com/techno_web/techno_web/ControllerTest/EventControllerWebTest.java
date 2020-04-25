package com.techno_web.techno_web.ControllerTest;

import com.techno_web.techno_web.controller.EventWebController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EventControllerWebTest {

    @Autowired
    private EventWebController eventWebController;

    @Test
    void contextLoads() {
        assertThat(eventWebController).isNotNull();
    }

}