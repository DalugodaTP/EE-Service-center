package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ItemDto;
import dto.tm.ItemTm;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ItemModel;
import model.impl.ItemModelImpl;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InventoryFormController {
    public AnchorPane inventoryPane;
    public JFXTreeTableView<ItemTm> tblItems;
    public TreeTableColumn colCode;
    public TreeTableColumn colDesc;
    public TreeTableColumn colUnitPrice;
    public TreeTableColumn colQty;
    public TreeTableColumn colOptions;
    public MFXTextField txtSearch;
    public MFXTextField txtDescription;
    public MFXTextField txtUnitPrice;
    public MFXTextField txtQtyOnHand;
    public MFXTextField txtCode;

    ItemModel itemModel = new ItemModelImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        //------Declare columns and mapping the ItemTm with the columns
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOptions.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        //--Adding selection event to the table
        JColorChooser itemTableView;
        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

        //--Search Table (JFoenix version)
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) ->
                tblItems.setPredicate(itemTmTreeItem -> {
                    String code = itemTmTreeItem.getValue().getCode().toLowerCase();
                    String desc = itemTmTreeItem.getValue().getDescription().toLowerCase();

                    return code.contains(newValue.toLowerCase()) || desc.contains(newValue.toLowerCase());
                }));

    }

    private void setData(TreeItem<ItemTm> newValue) {
        if (newValue != null) {
            //tblItem.refresh();txtCode.setEditable(false); //lock the code so that it cannot be changed
            txtCode.setText((newValue.getValue().getCode()));
            txtDescription.setText((newValue.getValue().getDescription()));
            txtUnitPrice.setText(String.valueOf(newValue.getValue().getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(newValue.getValue().getQty()));
        }
    }

    private void loadItemTable() throws SQLException, ClassNotFoundException {
        List<ItemDto> itemDtos = itemModel.allItems();
        //--List needs to be an obervable list to be able to pass into customer table
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        for (ItemDto x:
             itemDtos) {
            JFXButton btn = new JFXButton("Delete");
            ItemTm c = new ItemTm(
                 x.getCode(),
                 x.getDesc(),
                 x.getUnitPrice(),
                 x.getQty(),
                 btn
            );

            btn.setOnAction(actionEvent -> {
                try {
                    itemModel.deleteItem(x);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            tmList.add(c);
        }
        TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblItems.setRoot(treeItem);
        tblItems.setShowRoot(false);

    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
    }

    //--Routing
    public void dashBoardButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)inventoryPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ControlPanelForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)inventoryPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void orderManagementButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)inventoryPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderManagementForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void SettingsButtonOnAction(ActionEvent actionEvent) {
        //--No Implementation
    }

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
        //--Current Window
    }
}
