package oxy_project.OxygenMonitor.controller;

import oxy_project.OxygenMonitor.service.OxygenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/oxygen")
public class OxygenController {

    private final OxygenService oxygenService;

    public OxygenController(OxygenService oxygenService) {
        this.oxygenService = oxygenService;
    }

    @GetMapping("/{city}")
    public Map<String, Object> getOxygenLevel(@PathVariable String city) {
        return oxygenService.getOxygenLevel(city);
    }
}
