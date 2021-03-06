/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.controllers.dispatch;

import campis.dp1.Main;
import campis.dp1.controllers.suppliers.ListController;
import campis.dp1.models.Client;
import campis.dp1.models.DispatchOrder;
import campis.dp1.models.Delivery;
import campis.dp1.models.DispatchOrderLine;
import campis.dp1.models.DispatchOrderLineDisplay;
import campis.dp1.models.District;
import campis.dp1.models.Product;
import campis.dp1.models.RequestOrder;
import campis.dp1.models.UnitOfMeasure;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.itextpdf.text.Document;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author Gina Bustamante
 */
public class CreateDeliveryController implements Initializable {
    private Main main;
    
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXTextField vehicleField;
    
    @FXML
    private Label addressMessage;
    @FXML
    private Label vehicleMessage;
    @FXML
    private Label do_message;
    
    @FXML
    private JFXComboBox<String> dipatchOrderCb;
    
    private ObservableList<DispatchOrder> dispatchOrders;
    ObservableList<DispatchOrderLine> do_lines;
    
    @FXML
    private TableView<DispatchOrderLineDisplay> tableDelivery;
    @FXML
    private TableColumn<DispatchOrderLineDisplay, Integer> idCol;
    @FXML
    private TableColumn<DispatchOrderLineDisplay, String> prodCol;
    @FXML
    private TableColumn<DispatchOrderLineDisplay, Integer> cantCol;
    @FXML
    private TableColumn<DispatchOrderLineDisplay, String> unitCol;
    @FXML
    private TableColumn<DispatchOrderLineDisplay, Float> weigthCol;
    
    private ObservableList<Delivery> deliveries;
   
    private ObservableList<DispatchOrderLineDisplay>  delieveriesView;
    private Integer selected_del; 
    private Integer selected_do; 
    private Integer selected_ro;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setUpDipatchOrderCb();
        setupDeliveriesTable();
        this.selected_do = 0;
        
        dipatchOrderCb.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String> options, String oldValue, String newValue) -> {
            System.out.println(newValue);
            this.selected_do = Integer.parseInt(newValue);  
            
            try {
                loadData_deliveries();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
    }
    
    private void setupDeliveriesTable() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().id_dispatch_order_lineProperty().asObject());
        prodCol.setCellValueFactory(cellData -> cellData.getValue().product_nameProperty());
        cantCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        unitCol.setCellValueFactory(cellData -> cellData.getValue().unit_of_measure_nameProperty());
        weigthCol.setCellValueFactory(cellData -> cellData.getValue().weigthProperty().asObject());       
    }
    
    private void loadData_deliveries() throws SQLException, ClassNotFoundException {
        //Obtenemos el dispatch order
//        for (int i = 0; i < this.dispatchOrders.size(); i++) {
//            if (Objects.equals(this.selected_del, this.deliveries.get(i).getId_delivery())) {
//                this.selected_do = this.deliveries.get(i).getId_dispatch_order();
//            }
//        }
       
        //Obtenemos las lineas de las ordenes de despacho
        this.do_lines = getDispatchOrderLines(this.selected_do);
        
        //llenamos la tabla con las líneas de la orden de despacho
        delieveriesView = FXCollections.observableArrayList();
        
        for (int i = 0; i < this.do_lines.size(); i++) {
            DispatchOrderLine aux = this.do_lines.get(i);
            
            Product aux_prod = Product.getProduct(aux.getId_prod());
            
            String nombProd = aux_prod.getName();
            Float w = aux_prod.getWeight()*aux.getQuantity();
            Integer unid  = aux_prod.getId_unit_of_measure();
            String unid_name = UnitOfMeasure.getName(unid);
            
            DispatchOrderLineDisplay d = new DispatchOrderLineDisplay(aux.getId_dispatch_order_line(), 
                                                                     aux.getId_dispatch_order(), 
                                                                     aux.getId_prod(), nombProd, aux.getQuantity(), 
                                                                     unid, unid_name, w, aux.isDelivered());

            
            delieveriesView.add(d);
        }
        
        this.tableDelivery.setItems(null);
        this.tableDelivery.setItems(delieveriesView);
    }    
    
    private ObservableList<DispatchOrderLine> getDispatchOrderLines(int aux_id_do) {
         //Enlistamos las dispatch order lines
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String qryStr3 = "SELECT * FROM campis.dispatch_order_line WHERE id_dispatch_order = " + aux_id_do;
        SQLQuery qry3 = session.createSQLQuery(qryStr3);
        List<Object[]> rows3 = qry3.list();
        ObservableList<DispatchOrderLine> do_lines = FXCollections.observableArrayList();

        for (Object[] row : rows3) {
            DispatchOrderLine sup = new DispatchOrderLine(Integer.parseInt(row[0].toString()),
                                        Integer.parseInt(row[1].toString()),
                                        Integer.parseInt(row[2].toString()),
                                        Integer.parseInt(row[3].toString()));
            do_lines.add(sup);

        }
        
        session.close();
        sessionFactory.close();
        
        return do_lines;
     }
     
    
    @FXML
    private void setUpDipatchOrderCb() {
       this.dispatchOrders = getDispatchOrders();

        System.out.println("campis.dp1.controllers.dispatch.CreateDeliveryController.setUpDipatchOrderCb()");
        System.out.println(dispatchOrders.size());
        
       for (int i = 0; i < dispatchOrders.size(); i++) {
           dipatchOrderCb.getItems().add(this.dispatchOrders.get(i).getId_dispatch_order().toString());
       }

    }
    
     private ObservableList<DispatchOrder> getDispatchOrders() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(DispatchOrder.class);
        //criteria.add(Restrictions.eq("status", "POR DESPACHAR"));
        
        ObservableList<DispatchOrder> returnable = FXCollections.observableArrayList();
        
        List list = criteria.list();
        
        for (int i = 0; i < list.size(); i++) {
            // Verify that it does not have a delivery created
            int id_dispatch_order = ((DispatchOrder)list.get(i)).getId_dispatch_order();
            
            if (session.createSQLQuery("SELECT  * FROM campis.delivery WHERE id_dispatch_order = "+id_dispatch_order).list().size()>0)
                continue;

            returnable.add((DispatchOrder) list.get(i));
        }
        
        session.close();
        sessionFactory.close();
        return returnable;
    }
     
    public boolean validation() {

        boolean addressValid = addressField.getText().length() == 0;
        boolean vhValid = vehicleField.getText().length() == 0;
        boolean doValid = this.selected_do == 0;

        
        addressMessage.setText("");
        vehicleMessage.setText("");
        do_message.setText("");

        if (addressValid) {
            addressMessage.setText("Campo obligatorio");
        }

        if (vhValid) {
            vehicleMessage.setText("Campo obligatorio");
        }
        
        if (doValid) {
            do_message.setText("Campo obligatorio");
        }

        return (!addressValid && !vhValid && !doValid);
    }

    @FXML
    private void insertDelivery(ActionEvent event) throws IOException, DocumentException {
        if (validation()) {
            Delivery d = new Delivery(addressField.getText(), vehicleField.getText(), this.selected_do);
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String qryStr = "INSERT INTO campis.delivery VALUES(DEFAULT,'" + d.getLocation()+ "',"
                    + "'"+d.getVh_plate()+"',"
                    + "'"+d.getId_dispatch_order()+"')";
            SQLQuery qry = session.createSQLQuery(qryStr);
            qry.executeUpdate();
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
            createDeliveryDoc();
            this.goDeliveryList();
        }
    }

    public void createDeliveryDoc() throws IOException, DocumentException {
        String dest = "reports/referralGuide/GUIA_REMISION_" + this.selected_do  + ".pdf";
        File file = new File(dest);
        
        RequestOrder ro = RequestOrder.getRO(DispatchOrder.getRequestOrder(this.selected_do));
        String client = Client.getName(ro.getId_client());
        String district = District.getName(ro.getId_district());
        List<DispatchOrderLine> dispatch_order_lines = DispatchOrder.getDispatchOrderLines(this.selected_do);
        createPdf(dest, client, district, dispatch_order_lines);

    }

    public void createPdf(String dest, String client, String district_name, List<DispatchOrderLine> dispatch_order_lines) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font smallfont = new Font(FontFamily.HELVETICA, 10);
        PdfPCell blank = new PdfPCell(new Phrase(""));
        blank.setBorder(Rectangle.NO_BORDER);

        PdfPTable titulo = new PdfPTable(1);
        titulo.setTotalWidth(new float[]{ 160 });
        titulo.setLockedWidth(true);
        PdfPCell cell = new PdfPCell(new Phrase("GUÍA DE REMISIÓN"));
        cell.setFixedHeight(30);
        cell.setBorder(Rectangle.NO_BORDER);
        titulo.addCell(cell);

        PdfPTable info = new PdfPTable(5);

        PdfPCell nombre_cli_label = new PdfPCell(new Phrase("Nombre del Cliente:"));
        nombre_cli_label.setBorder(Rectangle.NO_BORDER);
        PdfPCell address_dispatch_label = new PdfPCell(new Phrase("Dirección de Despacho:"));
        address_dispatch_label.setBorder(Rectangle.NO_BORDER);
        PdfPCell district_label = new PdfPCell(new Phrase("Departamento:"));
        district_label.setBorder(Rectangle.NO_BORDER);
        PdfPCell delivery_date_label = new PdfPCell(new Phrase("Fecha Delivery:"));
        delivery_date_label.setBorder(Rectangle.NO_BORDER);
        PdfPCell vehicle_label = new PdfPCell(new Phrase("Placa vehículo:"));
        vehicle_label.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell nombre_cli = new PdfPCell(new Phrase(client));
        nombre_cli.setBorder(Rectangle.NO_BORDER);
        PdfPCell address_dispatch = new PdfPCell(new Phrase(addressField.getText()));
        address_dispatch.setBorder(Rectangle.NO_BORDER);
        PdfPCell district = new PdfPCell(new Phrase(district_name));
        district.setBorder(Rectangle.NO_BORDER);
        PdfPCell delivery_date = new PdfPCell(new Phrase("11/10/2017"));
        delivery_date.setBorder(Rectangle.NO_BORDER);
        PdfPCell vehicle = new PdfPCell(new Phrase(vehicleField.getText()));
        vehicle.setBorder(Rectangle.NO_BORDER);


        info.addCell(nombre_cli_label);
        info.addCell(nombre_cli);
        info.addCell(blank);
        info.addCell(district_label);
        info.addCell(district);

        info.addCell(address_dispatch_label);
        info.addCell(address_dispatch);
        info.addCell(blank);
        info.addCell(delivery_date_label);
        info.addCell(delivery_date);

        info.addCell(vehicle_label);
        info.addCell(vehicle);
        info.addCell(blank);
        info.addCell(blank);
        info.addCell(blank);

        PdfPTable table = new PdfPTable(4);
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("Unidad de Medida");
        table.addCell("Peso");
        for(int i = 0; i < dispatch_order_lines.size(); i++) {
            Product pro = Product.getProduct(dispatch_order_lines.get(i).getId_prod());
            table.addCell(pro.getName());
            Integer cant = dispatch_order_lines.get(i).getQuantity();
            table.addCell(cant.toString());
            table.addCell(UnitOfMeasure.getName(pro.getId_unit_of_measure()));
            Float weight = cant*pro.getWeight();
            table.addCell(weight.toString());
        }

        document.add(titulo);
        document.add(info);
        document.add(table);
        document.close();
    }

    @FXML
    private void goDeliveryList() throws IOException {
        main.showDeliveryList();
    }
    
}
