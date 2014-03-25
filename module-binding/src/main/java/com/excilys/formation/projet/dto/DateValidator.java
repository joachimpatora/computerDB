package com.excilys.formation.projet.dto;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateValidator implements ConstraintValidator<DateFormat, String>,
		MessageSourceAware {

	public DateValidator() {
		super();
	}

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void initialize(DateFormat constraintAnnotation) {

	}

	public boolean isValid(String object,
			ConstraintValidatorContext constraintContext) {

		if (!object.matches(messageSource.getMessage("date.regexp", null,
				LocaleContextHolder.getLocale()))) {
			// constraintContext.buildConstraintViolationWithTemplate("DateFormat");
			return false;
		}

		return true;

	}

	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String message() {
		
		return messageSource.getMessage("Pattern", null,
				LocaleContextHolder.getLocale());
	}

	public Class<?>[] groups() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<? extends Payload>[] payload() {
		// TODO Auto-generated method stub
		return null;
	}

}
