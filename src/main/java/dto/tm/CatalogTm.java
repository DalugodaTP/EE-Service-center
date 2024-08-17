package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CatalogTm extends RecursiveTreeObject<CatalogTm> {
    private String catalogId;
    private String productName;
    private String category;
    private String staff_id;
    private JFXButton action;
}
