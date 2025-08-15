package oxy_project.OxygenMonitor.controller;

import oxy_project.OxygenMonitor.service.OxygenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/oxygen")
@CrossOrigin(origins = "http://localhost:3000")
public class OxygenController {

    @Autowired
    private OxygenService oxygenService;

    @GetMapping("/{city}")
    public Map<String, Object> getOxygenLevel(@PathVariable String city) {
        return oxygenService.getOxygenLevel(city);
    }
}
