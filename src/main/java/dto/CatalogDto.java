package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CatalogDto {
    private String catalogId;
    private String productName;
    private String category;
    private String imageLink;
    private String staffId;
}
