package com.techno_web.techno_web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason="identifiant déjà utilisé")
public class ConflictException extends RuntimeException {}
