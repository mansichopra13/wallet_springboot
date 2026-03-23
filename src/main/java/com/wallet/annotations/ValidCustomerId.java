package com.wallet.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;

import java.lang.annotation.*;

//@Constraint(validatedBy =CustomerIdValidator.class)


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustomerId {
}
