package net.kicchi.todos.modals;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ToDo {

    private int id;
    private String title;
    private boolean completed;
    private int userId;

    public ToDo(String title, int userId) {
        this.title = title;
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
