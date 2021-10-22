package com.roberto.ecom.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.roberto.ecom.domain.enums.CustomerType;
import com.roberto.ecom.dto.CustomerNewDTO;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class NewCustomerGroupSequenceProvider implements DefaultGroupSequenceProvider<CustomerNewDTO> {

    @Override
    public List<Class<?>> getValidationGroups(CustomerNewDTO customer) {
        List<Class<?>> groups = new ArrayList<>();

        groups.add(CustomerNewDTO.class);

        if (customer != null && customer.getType() != null) {
            CustomerType type = CustomerType.toEnum(customer.getType());
            if (type != null) {
                groups.add(type.getGroup());
            }
        }
        return groups;
    }
}
