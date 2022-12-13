package io.allteran.plutos.util;

import io.allteran.plutos.domain.Company;
import io.allteran.plutos.domain.Country;
import io.allteran.plutos.dto.CompanyDTO;
import io.allteran.plutos.dto.CountryDTO;
import org.springframework.beans.BeanUtils;

public class EntityMapper {
    public static CountryDTO convertToDTO(Country c) {
        CountryDTO dto = new CountryDTO();
        BeanUtils.copyProperties(c, dto);
        return dto;
    }

    public static Country convertToEntity(CountryDTO dto) {
        Country c = new Country();
        BeanUtils.copyProperties(dto, c);
        return c;
    }

    public static CompanyDTO convertToDTO(Company co) {
        CompanyDTO dto = new CompanyDTO();
        BeanUtils.copyProperties(co, dto);
        return dto;
    }

    public static Company convertToEntity(CompanyDTO dto) {
        Company co = new Company();
        BeanUtils.copyProperties(dto, co);
        return co;
    }
}
