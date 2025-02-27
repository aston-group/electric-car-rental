package ru.astongroup.carchargermanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name= "Car Charger Management", description = "API для управления зарядными станциями автомобилей")
public class ChargerController {

    private final ChargerStationService chargerStationService;

    public ChargerController(ChargerStationService chargerStationService) {
        this.chargerStationService = chargerStationService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех зарядных станций", description = "возвращает список всех зарядных станций")
    public ResponseEntity<List<ChargerStationResponseDto>> findAll() {
        List<ChargerStationResponseDto> chargerStationResponseDtos = chargerStationService.findAll();
        return new ResponseEntity<>(chargerStationResponseDtos, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о зарядной станции по Id", description = "возвращает данные о зарядной станции по Id")
    public ResponseEntity<ChargerStationResponseDto> findById(@PathVariable Long id) {
        ChargerStationResponseDto chargerStationResponseDto = chargerStationService.findById(id);
        return new ResponseEntity<>(chargerStationResponseDto, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/create")
    @Operation(summary = "Создать зарядную станцию", description = "создает зарядную станцию")
    public ResponseEntity<ChargerStationResponseDto> create(@Valid @RequestBody ChargerStationRequestDto chargerStationRequestDto) {
        ChargerStationResponseDto chargerStationResponseDto = chargerStationService.create(chargerStationRequestDto);
        return new ResponseEntity<>(chargerStationResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить зарядную станцию", description = "удаляет зарядную станцию")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargerStationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить зарядную станцию", description = "изменяет зарядную станцию")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody ChargerStationRequestDto chargerStationRequestDto) {
        chargerStationService.update(id, chargerStationRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/start")
    @Operation(summary = "Запустить зарядную станцию", description = "Помечает зарядную станцию как недоступную для использования")
    public ResponseEntity<ChargerStationResponseDto> startCharging(
            @PathVariable Long id,
            @RequestParam Long carId) {
        ChargerStationResponseDto responseDto = chargerStationService.startCharging(id, carId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/end")
    @Operation(summary = "Завершить использовать зарядную станцию", description = "Помечает зарядную станцию как доступную для использования")
    public ResponseEntity<ChargerStationResponseDto> endCharging(
            @PathVariable Long id,
            @RequestParam Long carId) {
        ChargerStationResponseDto responseDto = chargerStationService.endCharging(id, carId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}