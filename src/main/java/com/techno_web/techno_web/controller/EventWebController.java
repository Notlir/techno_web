package com.techno_web.techno_web.controller;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.services.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class EventWebController {

    @Autowired
    EventServiceImpl moEventService;



    @PostMapping(
            path="/getSeries/{id}/newEvent",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    public String createNewEvent(Model model, @CookieValue("Authorization") String token, EventDto poNewEvent, @PathVariable("id") String id)
    {
        Calendar date = Calendar.getInstance();
        poNewEvent.setTime(date);
        moEventService.createNewEvent(poNewEvent, token, id);
        return "redirect:/getSeries/"+id;
    }
}
