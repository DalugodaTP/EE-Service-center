package controller;

import bo.BoFactory;
import bo.custom.CatalogBo;
import bo.util.BoType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CatalogDto;
import dto.StaffDto;
import dto.tm.CatalogTm;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class CatalogViewFormController {
    public ImageView imageView;
    public MFXTextField txtProductName;
    @FXML
    private AnchorPane controlPanelPane;

    @FXML
    private JFXTreeTableView<CatalogTm> tblCatalogView;

    @FXML
    private TreeTableColumn colCatalogid;

    @FXML
    private TreeTableColumn colProductName;

    @FXML
    private TreeTableColumn colCatagory;

    @FXML
    private TreeTableColumn colStaffId;

    @FXML
    private TreeTableColumn colAction;

    @FXML
    private MFXTextField txtCatalogtId;

    @FXML
    private MFXTextField txtProductId;

    @FXML
    private Label lblUserName;

    @FXML
    private MFXComboBox cmbCatagory;

    CatalogBo catalogBo = BoFactory.getInstance().getBo(BoType.CATALOG_ITEM);
    //--Image Link
    File selectedImage;

    private StaffDto loggedStaff;

    public void initialize() throws SQLException, ClassNotFoundException {
        txtCatalogtId.setEditable(false);

        //------Declare columns and mapping the CatalogTm with the columns
        colCatalogid.setCellValueFactory(new TreeItemPropertyValueFactory<>("catalogId"));
        colProductName.setCellValueFactory(new TreeItemPropertyValueFactory<>("productName"));
        colCatagory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colStaffId.setCellValueFactory(new TreeItemPropertyValueFactory<>("staff_id"));
        colAction.setCellValueFactory(new TreeItemPropertyValueFactory<>("action"));

        loadTableView();

        //--set items for categories
        cmbCatagory.getItems().add("electronic");
        cmbCatagory.getItems().add("electrical");

        //--Selection model
        //--Adding selection event to the table
        tblCatalogView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(TreeItem<CatalogTm> newValue) {
        if (newValue != null) {
            //tblItem.refresh();
            txtCatalogtId.setEditable(false); //lock the code so that it cannot be changed
            txtCatalogtId.setText((newValue.getValue().getCatalogId()));
            txtProductName.setText((newValue.getValue().getProductName()));
            cmbCatagory.setText((newValue.getValue().getCategory()));
            //--Get the image and set to the selectedFile variable
            CatalogDto catalogItem = catalogBo.getCatalogItem(newValue.getValue().getCatalogId());
            String imageLink = catalogItem.getImageLink();
            selectedImage = new File(""+imageLink+"");

            javafx.scene.image.Image image = new Image(selectedImage.getAbsolutePath());
            imageView.setImage(image);
        }
    }

    public void selectImageFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("c:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(lblUserName.getScene().getWindow());

        if (selectedFile != null){
            javafx.scene.image.Image image = new Image(selectedFile.getAbsolutePath());
            imageView.setImage(image);
            selectedImage = selectedFile;
        }
    }

    void loadTableView() throws SQLException, ClassNotFoundException {
        List<CatalogDto> catalogDto = catalogBo.getAllCatalog();

        //--List needs to be an obervable list to be able to pass into customer table
        ObservableList<CatalogTm> tmList = FXCollections.observableArrayList();

        for (CatalogDto x:
                catalogDto) {

            JFXButton btn = new JFXButton("Delete");
            CatalogTm c = new CatalogTm(
                    x.getCatalogId(),
                    x.getProductName(),
                    x.getCategory(),
                    x.getStaffId(),
                    btn
            );

            btn.setOnAction(actionEvent -> {
                try {
                    deleteItem(x);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            tmList.add(c);
        }
        TreeItem<CatalogTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblCatalogView.setRoot(treeItem);
        tblCatalogView.setShowRoot(false);

    }

    private void deleteItem(CatalogDto tm) throws SQLException, ClassNotFoundException {
        //--Capture the image file
        File deleteFile = new File(tm.getImageLink());

        Alert confirmAlert = new Alert
                (Alert.AlertType.CONFIRMATION,
                        "Do you want to delete this item?",
                        ButtonType.YES, ButtonType.NO);

        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
            if (catalogBo.deleteCatalog(tm)) {
                deleteFile.delete();
                operationSuccessAlert("Deleted!", "Item Deleted Successfully!");
            }
            else{
                operationErrorAlert("Failed!", "Item failed to delete!");
            }
            confirmAlert.close();
            clearFields();
            loadTableView();
        }
    }

    private void clearFields() {
        tblCatalogView.refresh();
        txtCatalogtId.clear();
        txtProductName.clear();
        cmbCatagory.clear();
        //--Clear the image view
        imageView.setImage(null);
    }

    public void clearFieldsButtonOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    @FXML
    void ManageCustomersButtonOnAction(ActionEvent event) {

    }

    @FXML
    void SettingsButtonOnAction(ActionEvent event) {

    }

    @FXML
    void addImageButtonOnAction(ActionEvent event) {
        selectImageFile();
    }

    @FXML
    void addItemButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String newLocation = createFolder(selectedImage);

        //--rename and move the file
        selectedImage.renameTo(new File(newLocation));

        //--Create an object
        CatalogDto catalogDto = new CatalogDto(
                null,
                txtProductName.getText(),
                cmbCatagory.getText(),
                newLocation,
                loggedStaff.getStaffId()
        );

        if (catalogBo.saveCatalog(catalogDto)){
            operationSuccessAlert("Saved Catalog item", "Catalog item saved successfully");
        }else {
            operationErrorAlert("Saved Catalog item", "Catalog item failed to save");
        }
        loadTableView();
    }

    private String createFolder(File selectedImage) {
        File folder = new File("product images");
        if (!folder.exists()){
            folder.mkdir();
        }
        return folder.getAbsolutePath() + File.separator + selectedImage.getName();
    }

    @FXML
    void dashBoardButtonOnAction(ActionEvent event) {

    }

    @FXML
    void inventoryButtonOnAction(ActionEvent event) {

    }
    

    @FXML
    void orderManagementButtonOnAction(ActionEvent event) {

    }

    @FXML
    void updateItemButtonOnAction(ActionEvent event) {
        String newLocation = createFolder(selectedImage);

        //--To delete the file
        File deleteFile = selectedImage.getAbsoluteFile();

                //--rename and move the file
        selectedImage.renameTo(new File(newLocation));

        CatalogDto c = new CatalogDto(
                txtCatalogtId.getText(),
                txtProductName.getText(),
                cmbCatagory.getText(),
                newLocation,
                loggedStaff.getStaffId()
        );

        try {
            if (catalogBo.updateCatalog(c)) {
                operationSuccessAlert("Udated succefully", "Catalog " + c.getCatalogId() + " Updated!");

                //--Delete the original file
                deleteFile.delete();
                loadTableView();
                clearFields();
            }
        } catch (Exception e) {
            operationErrorAlert("Filed to update", "The item failed to update, please try again!");
        }
    }

        public void backButtonOnAction(MouseEvent mouseEvent) {
    }

    public void logOutButtonOnAction(MouseEvent mouseEvent) {
    }

    void loadCatalogView(StaffDto staff){
        //--Initialize logged user from LoginFormController
        this.loggedStaff = staff;
        lblUserName.setText("Staff");
    }

    //--Notifications
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
