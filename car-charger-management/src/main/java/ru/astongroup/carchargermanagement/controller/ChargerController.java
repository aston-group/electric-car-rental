package ru.astongroup.carchargermanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.service.ChargerStationService;

import java.util.List;

@RestController
@RequestMapping("/chargers")
public class ChargerController {

    private final ChargerStationService chargerStationService;

    public ChargerController(ChargerStationService chargerStationService) {
        this.chargerStationService = chargerStationService;
    }

    @GetMapping
    public ResponseEntity<List<ChargerStationResponseDto>> findAll() {
        List<ChargerStationResponseDto> chargerStationResponseDtos = chargerStationService.findAll();
        return new ResponseEntity<>(chargerStationResponseDtos, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargerStationResponseDto> findById(@PathVariable Long id) {
        ChargerStationResponseDto chargerStationResponseDto = chargerStationService.findById(id);
        return new ResponseEntity<>(chargerStationResponseDto, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/create")
    public ResponseEntity<ChargerStationResponseDto> create(@Valid @RequestBody ChargerStationRequestDto chargerStationRequestDto) {
        ChargerStationResponseDto chargerStationResponseDto = chargerStationService.create(chargerStationRequestDto);
        return new ResponseEntity<>(chargerStationResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargerStationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody ChargerStationRequestDto chargerStationRequestDto) {
        chargerStationService.update(id, chargerStationRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<ChargerStationResponseDto> startCharging(
            @PathVariable Long id,
            @RequestParam Long carId) {
        ChargerStationResponseDto responseDto = chargerStationService.startCharging(id, carId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}