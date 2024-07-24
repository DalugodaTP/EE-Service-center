package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.ItemTm;
import dto.tm.OrderTm;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementFormController {
    @FXML
    private AnchorPane orderManagementPane;

    @FXML
    private AnchorPane tableViewPane;

    @FXML
    private JFXTreeTableView<OrderTm> tblOrders;

    @FXML
    private MFXTextField txtDescription;

    @FXML
    private MFXTextField txtUnitPrice;

    @FXML
    private MFXTextField txtBuyingQty;

    @FXML
    private MFXTextField txtCustomerName;

    @FXML
    private Label lblTotal;

    @FXML
    private TreeTableColumn colCode;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colAmount;

    @FXML
    private TreeTableColumn colOptions;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private JFXComboBox cmbCustomerId;

    private List<CustomerDto> customers;
    private List<ItemDto> items;

    //--To Store orders before passing into order
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();

    //--Import Models
    CustomerModel customerModel = new CustomerModelImpl();
    ItemModel itemModel = new ItemModelImpl();
    public void initialize(){
        //------Declare columns and mapping the ItemTm with the columns
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOptions.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        loadCustomerId();
        loadItemCode();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, id) -> {
            for (CustomerDto dto:customers) {
                if (dto.getId().equals(id)){
                    txtCustomerName.setText(dto.getName());
                }
            }
        }));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, code) -> {
            for (ItemDto dto:items) {
                if (dto.getCode().equals(code)){
                    txtDescription.setText(dto.getDesc());
                    txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
                }
            }
        }));

    }

    private void loadItemCode() {
        try {
            items = itemModel.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for(ItemDto x: items){
                list.add(x.getCode());
            }
            cmbItemCode.setItems( list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerId() {
        try {
            customers = customerModel.allCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for(CustomerDto x: customers){
                list.add(x.getId());
            }
            cmbCustomerId.setItems( list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCartButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double amount =itemModel.getItem(cmbItemCode.getValue().toString()).getUnitPrice()* Integer.parseInt(txtBuyingQty.getText());
        JFXButton btn = new JFXButton("Delete");
        OrderTm tm = new OrderTm(
                cmbItemCode.getValue().toString(),
                txtDescription.getText(),
                Integer.parseInt(txtBuyingQty.getText()),
                amount,
                btn
        );

        boolean isExist = false;
        //--Check if the item has been added previously and update that item
        for (OrderTm order:tmList) {
            if (order.getCode().equals(tm.getCode())){
                order.setQty(order.getQty()+tm.getQty());
                order.setAmount(order.getAmount()+tm.getAmount());
                isExist =true;
            }
        }

        if (!isExist){
            tmList.add(tm);
        }

        TreeItem<OrderTm> treeObject = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
        tblOrders.setRoot(treeObject);
        tblOrders.setShowRoot(false);
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
    }
    public void settingButtonOnAction(ActionEvent actionEvent) {
        //        No implemenation
    }

    //--Routing
    public void dashBoardButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ControlPanelForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }
    public void orderManagementButtonOnAction(ActionEvent actionEvent) {
        //        Current pane
    }

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomersForm.fxml"))));
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("CustomerForm window in the path is missing");
        }
    }

    public void SettingsButtonOnAction(ActionEvent actionEvent) {
    }

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/InventoryForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Inventory window in the path is missing");
        }
    }
}
