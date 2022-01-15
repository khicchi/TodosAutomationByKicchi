package net.kicchi.todos.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {

    private int id;
    private String title;
    private boolean completed;
    private int userId;

    public ToDo(String title, int userId) {
        this.title = title;
        this.completed = completed;
        this.userId = userId;
    }

    /* json object
    {
        "userId": 1,
        "id": 10,
        "title": "illo est ratione doloremque quia maiores aut",
        "completed": true
      }
     */
}
