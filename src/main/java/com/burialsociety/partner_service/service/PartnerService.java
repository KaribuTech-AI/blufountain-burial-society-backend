package com.burialsociety.partner_service.service;

import com.burialsociety.partner_service.entity.Partner;
import com.burialsociety.partner_service.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public Partner createPartner(Partner partner) {
        return partnerRepository.save(partner);
    }
}
