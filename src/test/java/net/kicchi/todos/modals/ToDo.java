package net.kicchi.todos.modals;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ToDo {
    private int userId;
    private String title;
    private boolean completed;
    private int id;
}
