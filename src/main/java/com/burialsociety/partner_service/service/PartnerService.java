package com.burialsociety.partner_service.service;

import com.burialsociety.partner_service.entity.Partner;
import com.burialsociety.partner_service.dto.PartnerRequestDto;
import com.burialsociety.partner_service.dto.PartnerResponseDto;
import com.burialsociety.partner_service.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public List<PartnerResponseDto> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public PartnerResponseDto getPartnerById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
        return mapToResponseDto(partner);
    }

    public PartnerResponseDto createPartner(PartnerRequestDto requestDto) {
        Partner partner = mapToEntity(requestDto);
        Partner savedPartner = partnerRepository.save(partner);
        return mapToResponseDto(savedPartner);
    }

    public PartnerResponseDto updatePartner(Long id, PartnerRequestDto requestDto) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));

        partner.setName(requestDto.getName());
        partner.setCategory(requestDto.getCategory());
        partner.setContactNumber(requestDto.getContactNumber());
        partner.setEmail(requestDto.getEmail());
        partner.setSlaAdherence(requestDto.getSlaAdherence());
        partner.setRating(requestDto.getRating());
        partner.setStatus(requestDto.getStatus());

        Partner updatedPartner = partnerRepository.save(partner);
        return mapToResponseDto(updatedPartner);
    }

    public void deletePartner(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new RuntimeException("Partner not found with id: " + id);
        }
        partnerRepository.deleteById(id);
    }

    private PartnerResponseDto mapToResponseDto(Partner partner) {
        return PartnerResponseDto.builder()
                .id(partner.getId())
                .name(partner.getName())
                .category(partner.getCategory())
                .contactNumber(partner.getContactNumber())
                .email(partner.getEmail())
                .slaAdherence(partner.getSlaAdherence())
                .rating(partner.getRating())
                .status(partner.getStatus())
                .build();
    }

    private Partner mapToEntity(PartnerRequestDto dto) {
        return Partner.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail())
                .slaAdherence(dto.getSlaAdherence())
                .rating(dto.getRating())
                .status(dto.getStatus())
                .build();
    }
}
