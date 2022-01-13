package net.kicchi.todos.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AutomationException extends Exception {
    public AutomationException(String message) {
        super(message);
    }
}
