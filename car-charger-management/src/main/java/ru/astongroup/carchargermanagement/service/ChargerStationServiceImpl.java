package ru.astongroup.carchargermanagement.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;
import ru.astongroup.carchargermanagement.mapper.ChargerStationMapper;
import ru.astongroup.carchargermanagement.repository.ChargerStationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return ChargerStationMapper.toChargerStationResponseDto(chargerStation.get());
    }


    @Override
    @Transactional
    public void delete(Long id) {
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
}
