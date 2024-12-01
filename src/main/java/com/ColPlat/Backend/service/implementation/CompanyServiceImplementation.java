package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.repository.CompanyRepository;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public CompanyResponse getCompanyInfoFromToken(String token) {
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email);
        Optional<Company> company = companyRepository.findById(user.getCompanyId());
        if(company.isPresent()) {
            Company companyData = company.get();
            CompanyResponse companyResponse = null;

            try {
                companyResponse = companyData.getCompanyResponse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return companyResponse;
        }
        return null;
    }
}
