package com.techno_web.techno_web.controller;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.services.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        moEventService.createNewEvent(poNewEvent, token, id);
        return "redirect:/getSeries/"+id;
    }

    @PutMapping(
            path="/getSeries/{id_series}/updateEvent/{id_event}",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    public ResponseEntity<String> updateEvent(@CookieValue("Authorization") String token, EventDto poNewEvent, @PathVariable("id_series") String idSeries, @PathVariable("id_event")String id_event )
    {
        moEventService.updateEvent(token, idSeries, id_event, poNewEvent);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path="/getSeries/{id_series}/deleteEvent/{id_event}",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    public @ResponseBody String deleteEvent(@CookieValue("Authorization") String token, @PathVariable("id_series") String idSeries, @PathVariable("id_event")String id_event )
    {
        moEventService.deleteEvent(token, idSeries, id_event);

        return "redirect:/getSeries/"+idSeries;
    }
}
