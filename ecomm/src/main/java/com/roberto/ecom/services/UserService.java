package com.roberto.ecom.services;

import com.roberto.ecom.domain.enums.UserProfile;
import com.roberto.ecom.security.EcomUserPrincipal;
import com.roberto.ecom.services.exceptions.AuthorizationException;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static EcomUserPrincipal getAuthenticated() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof EcomUserPrincipal) {
            return (EcomUserPrincipal) user;
        }
        return null;
    }

    public static void checkCustomer(String id) {
        EcomUserPrincipal user = UserService.getAuthenticated();
        if (user == null || !user.hasRole(UserProfile.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Access denied: customer with different id of user logged in.");
        }
    }
}