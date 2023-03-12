package io.allteran.plutos.util;

import io.allteran.plutos.domain.*;
import io.allteran.plutos.dto.*;
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

    public static Privilege convertToEntity(PrivilegeDTO dto) {
        Privilege p = new Privilege(dto.getId(), dto.getName());
        return p;
    }

    public static PrivilegeDTO convertToDTO(Privilege p) {
        PrivilegeDTO dto = new PrivilegeDTO(p.getId(), p.getName());
        return dto;
    }

    public static ShiftDTO convertToDTO(Shift s) {
        ShiftDTO dto = new ShiftDTO();
        BeanUtils.copyProperties(s, dto);
        return dto;
    }

    public static Shift convertToEntity(ShiftDTO dto) {
        Shift s = new Shift();
        BeanUtils.copyProperties(dto, s);
        return s;
    }

    public static UserDTO convertToDTO(User u) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(u, dto);
        return dto;
    }

    public static User convertToEntity(UserDTO dto) {
        User u = new User();
        BeanUtils.copyProperties(dto, u);
        return u;
    }
}
