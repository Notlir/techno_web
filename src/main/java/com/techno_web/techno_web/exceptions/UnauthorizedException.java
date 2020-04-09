package com.techno_web.techno_web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="login ou mot de passe incorrect")
public class UnauthorizedException extends RuntimeException {}
