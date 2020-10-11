package Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Column {

    String attributeName;
    String type;
    Integer length;
    boolean isnull = false;
}
