package org.misha;

public class JsonGenerationException extends RuntimeException {
    protected JsonGenerationException(final String message,
                                      final Throwable cause

    ) {
        super(message, cause, false, true);
    }
}
