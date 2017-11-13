/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.controllers.vehicles;


import campis.dp1.models.Vehicle;
import campis.dp1.ContextFX;
import campis.dp1.Main;
import static campis.dp1.controllers.vehicles.CreateVehicleController.getWarehouses;
import campis.dp1.models.Warehouse;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Gina Bustamante
 */
public class EditVehicleController implements Initializable {
    private Main main;
    private int vehicle_id;
    
    @FXML
    private JFXTextField lblWeight;

    @FXML
    private JFXTextField lblSpeed;

    @FXML
    private JFXComboBox<String> cmWarehouse;

    @FXML
    private JFXTextField lblPlate;

    @FXML
    private Label lblWeightMessage;

    @FXML
    private Label lblSpeedMessage;

    @FXML
    private Label cmWarehouseMessage;

    @FXML
    private Label lblPlateMessage;
   
    @FXML
    private void goListVehicles() throws IOException {
        main.showListVehicle();
    }

    
    /*public static Integer searchWarehouse(String wr) throws SQLException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Warehouse.class);
        criteria.add(Restrictions.eq("name",wr));
        Integer codWr;
        List rsWarehouse = criteria.list();
        Warehouse result = (Warehouse)rsWarehouse.get(0);
        codWr = result.getId();
        return codWr;
    }*/
    
    public boolean validation() {
        boolean lblWeightValid = lblWeight.getText().length() == 0;
        boolean lblSpeedValid = lblSpeed.getText().length() == 0;
        boolean lblPlateValid = lblPlate.getText().length() == 0;
        boolean cmWarehouseValid = cmWarehouse.getValue() == null;
        
        lblPlateMessage.setText("");
        cmWarehouseMessage.setText("");
        lblSpeedMessage.setText("");
        lblWeightMessage.setText("");

        if (lblPlateValid)
            lblPlateMessage.setText("Campo obligatorio");
        if (cmWarehouseValid)
            cmWarehouseMessage.setText("Campo obligatorio");
        if (lblSpeedValid)
            lblSpeedMessage.setText("Campo obligatorio");
        if(lblWeightValid)
            lblWeightMessage.setText("Campo obligatorio");

        return (!lblWeightValid && !lblSpeedValid && !lblPlateValid && !cmWarehouseValid);
    }

    @FXML
    private void updateVehicle() throws IOException{
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        int codWr=1;
        
        /*try
         {
            codWr = searchWarehouse(cmWarehouse.getValue());
         }
        catch (ClassNotFoundException |SQLException e)
         {
            e.printStackTrace();
            //agregar error
         }*/
        
        
        Query query = session.createQuery("update Vehicle set max_weight=:newMweight,"+
                                            "speed=:newSpeed,"+
                                          //  "active=:newActive,"+
                                            "id_warehouse=:newidW,"+
                                            "plate=:newPlate"+
                                            " where id_vehicle=:oldId");
        query.setParameter("newMweight", Double.parseDouble(lblWeight.getText()));
        query.setParameter("newSpeed", Integer.parseInt(lblSpeed.getText()));
        query.setParameter("newidW", codWr);
        query.setParameter("newPlate", lblPlate.getText());
        query.setParameter("oldId", this.vehicle_id);
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        this.goListVehicles();
    }
    
    public static List<Warehouse> getWarehouses() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Warehouse.class)
                .setProjection(Projections.projectionList()
                .add(Projections.property("name"),"name"))
                .setResultTransformer(Transformers.aliasToBean(Warehouse.class));
        List<Warehouse> measures = criteria.list();
        return measures;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.vehicle_id = ContextFX.getInstance().getId();
        
        /*List<Warehouse> list = getWarehouses();
        for (int i = 0; i < list.size(); i++) {
            cmWarehouse.getItems().addAll(list.get(i).getName());
        }*/
        
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Vehicle.class);
        criteria.add(Restrictions.eq("id_vehicle",this.vehicle_id));
        Vehicle v = (Vehicle) criteria.list().get(0);
        this.lblWeight.setText(Double.toString(v.getMax_weight()));
        this.lblSpeed.setText(Integer.toString(v.getSpeed()));
        this.lblPlate.setText(v.getPlate());
        
    } 
}
