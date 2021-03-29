package com.example.springsecuritydemo.rest;

import com.example.springsecuritydemo.model.Developer;
import com.example.springsecuritydemo.model.Permission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
    private List<Developer> DEVS = Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2L, "Sergey", "Sergeev"),
            new Developer(3L, "Petr", "Petrov")
    ).collect(Collectors.toList());

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('developers:read')")
    public List<Developer> getAll() {
        return DEVS;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developers:read')")
    public Developer getById(@PathVariable Long id) {
        return DEVS.stream().filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public Developer create(@RequestBody Developer developer) {
        DEVS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public void deleteById(@PathVariable Long id) {
        DEVS.removeIf(dev -> dev.getId().equals(id));
    }
}
