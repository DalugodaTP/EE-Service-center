package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.impl.CustomerBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.CustomerDto;
import dto.ItemDto;

import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.tm.OrderTm;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.custom.OrderDao;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.OrderDaoImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementFormController {
    public Label lblOrderID;
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

    private double tot;

    //--To Store orders before passing into order
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();


    //--Import Models
    private CustomerBo customerBo = new CustomerBoImpl();
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    private OrderDao orderDao = new OrderDaoImpl();
    public void initialize() throws SQLException, ClassNotFoundException {
        //------Declare columns and mapping the ItemTm with the columns
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOptions.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        loadCustomerId();
        generateId();
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
                    txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        }));

    }

    private void loadItemCode() {
        try {
            items = itemBo.allItems();
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

    private void loadCustomerId() throws SQLException, ClassNotFoundException {
        customers = customerBo.allCustomers();
        ObservableList list = FXCollections.observableArrayList();
        for(CustomerDto x: customers){
            list.add(x.getId());
        }
        cmbCustomerId.setItems( list);
    }

    public void addToCartButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //--Capture the new amount of new added items
        double amount = itemBo.getItem(
                cmbItemCode.getValue().toString()).getUnitPrice()* Integer.parseInt(txtBuyingQty.getText()
        );

        //--Get the quantity in hand before placing the order
        int qtyInHand = itemBo.getItem(cmbItemCode.getValue().toString()).getQty();

        if (qtyInHand > Integer.parseInt(txtBuyingQty.getText())){
            //--Create a button to delete the items
            JFXButton btn = new JFXButton("Delete");

            //--Create a table object to insert the data into the table by getting the values from fields
            OrderTm tm = new OrderTm(
                    cmbItemCode.getValue().toString(),
                    txtDescription.getText(),
                    Integer.parseInt(txtBuyingQty.getText()),
                    amount,
                    btn
            );

            //--Set action to delete button
            btn.setOnAction(actionEvent1 -> {
                tmList.remove(tm);
                tot-=tm.getAmount();
                tblOrders.refresh();
                lblTotal.setText(String.valueOf(tot));
            });

            //--Variable to store if the item already exists
            boolean isExist = false;


            //--Check if the item has been added previously and update that item
            for (OrderTm order:tmList) {
                if (order.getCode().equals(tm.getCode())){
                    order.setQty(order.getQty()+tm.getQty());
                    order.setAmount(order.getAmount()+tm.getAmount());
                    isExist =true;
                    tot+=order.getAmount();
                }
            }

            //--If the item does not exist then perform this block
            if (!isExist){
                tmList.add(tm);
                tot+=tm.getAmount();
            }

            //--Add the item list into the table
            TreeItem<OrderTm> treeObject = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblOrders.setRoot(treeObject);
            tblOrders.setShowRoot(false);

            //--Set the value to the label
            lblTotal.setText(String.format("%.2f",tot));
            clearFields();
        }
        else{
            operationErrorAlert("Failed to place order", "Please place a lesser quantity than "+ itemBo.getItem(cmbItemCode.getValue().toString()).getQty()+"");
            clearFields();
        }
    }

    public void generateId(){
        try {
            OrderDto dto = orderDao.lastOrder();
            if (dto!=null){
                String id = dto.getOrderId();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                lblOrderID.setText(String.format("D%03d",num));
            }else{
                lblOrderID.setText("D001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
        //--From the temp list we have saved the orders, a new list with orderDetails is prepared
        List<OrderDetailsDto> list = new ArrayList<>();

        for (OrderTm tm:tmList) {
            list.add(new OrderDetailsDto(
                    lblOrderID.getText(),
                    tm.getCode(),
                    tm.getQty(),
                    tm.getAmount()/tm.getQty()
            ));
        }
        //--Check if the order list is not empty
        if (!tmList.isEmpty()){
            //--proceed to save the orderlist (tmList) through orderModel
            boolean isSaved = false;
            try {
                isSaved = orderDao.saveOrder(new OrderDto(
                        lblOrderID.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                        cmbCustomerId.getValue().toString(),
                        list
                ));

                if (isSaved){
                    operationSuccessAlert("Order status", "Order is saved successfully!");
                    orderManagementButtonOnAction();
                } else {
                    operationErrorAlert("Order Status", "Order failed to save");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void clearFields() {
        tblOrders.refresh();
        txtBuyingQty.clear();
    }

    //--Routing
    public void dashBoardButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ControlPanelForm.fxml"))));
            stage.show();
        } catch (Exception e) {
            operationErrorAlert("Failed to Load window", "Dashboard window in the path is missing");
        }
    }
    public void orderManagementButtonOnAction() {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderManagementForm.fxml"))));
            stage.show();
        } catch (Exception e) {
            operationErrorAlert("Failed to Load window", "Order Management window in the path is missing");
        }
    }

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("CustomerForm window in the path is missing");
        }
    }

    public void settingsButtonOnAction(ActionEvent actionEvent) {
    }

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/InventoryForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Inventory window in the path is missing");
        }
    }

    //--Alerts
    void operationSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    void operationErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
