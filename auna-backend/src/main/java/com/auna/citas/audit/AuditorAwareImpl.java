package com.auna.citas.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		/*
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication = null || !authentication.isAuthenticated()) {
			return null;
		}
		return Optional.of(authentication.getName().toUpperCase());
		*/
		return Optional.of("COD_USUARIO");
	}
	
}
