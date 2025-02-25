package ru.astongroup.carchargermanagement.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;
import ru.astongroup.carchargermanagement.exception.DataNotFoundException;
import ru.astongroup.carchargermanagement.mapper.ChargerStationMapper;
import ru.astongroup.carchargermanagement.repository.ChargerStationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChargerStationServiceImpl implements ChargerStationService {
    private final ChargerStationRepository chargerStationRepository;

    public ChargerStationServiceImpl(ChargerStationRepository chargingStationRepository) {
        this.chargerStationRepository = chargingStationRepository;
    }

    @Override
    public ChargerStationResponseDto create(ChargerStationRequestDto chargingStationRequestDto) {
        ChargerStation chargerStation = chargerStationRepository.save(ChargerStationMapper.toChargerStationDto(chargingStationRequestDto));
        return ChargerStationMapper.toChargerStationResponseDto(chargerStation);
    }

    @Override
    public List<ChargerStationResponseDto> findAll() {
        return chargerStationRepository.findAll().stream()
                .map(ChargerStationMapper::toChargerStationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChargerStationResponseDto findById(Long id) {
        Optional<ChargerStation> chargerStation = chargerStationRepository.findById(id);
        if (chargerStation.isPresent()) {
            return ChargerStationMapper.toChargerStationResponseDto(chargerStation.get());
        } else {
            throw new DataNotFoundException("Charger station not found" + id);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!chargerStationRepository.existsById(id)) {
            throw new DataNotFoundException("Charger station not found" + id);
        }
        chargerStationRepository.deleteById(id);
    }

    @Override
    public void update(Long id, ChargerStationRequestDto chargerStationRequestDto) {
        Optional<ChargerStation> chargerStationOptional = chargerStationRepository.findById(id);
        if (chargerStationOptional.isPresent()) {
            ChargerStation chargerStation = chargerStationOptional.get();
            chargerStation.setNameStation(chargerStationRequestDto.getNameStation());
            chargerStation.setAddressStation(chargerStationRequestDto.getAddressStation());
            chargerStation.setIsAvailableStation(chargerStationRequestDto.getIsAvailableStation());
            chargerStation.setLatitude(chargerStationRequestDto.getLatitude());
            chargerStation.setLongitude(chargerStationRequestDto.getLongitude());
            chargerStationRepository.save(chargerStation);
        }
    }

    @Override
    @Transactional
    public ChargerStationResponseDto startCharging(Long stationId, Long carId) {
        ChargerStation station = chargerStationRepository.findById(stationId)
                .orElseThrow(() -> new DataNotFoundException("Charger station not found" + stationId));

        if(!station.getIsAvailableStation()) {
            throw new IllegalStateException("Charger station is not available");
        }

        station.setIsAvailableStation(false);
        chargerStationRepository.save(station);

        log.info("Станция {} занята электромобилем {}", stationId, carId);
        return ChargerStationMapper.toChargerStationResponseDto(station);
    }
}
