/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.controllers.products;

import campis.dp1.Main;
import campis.dp1.models.Product;

import campis.dp1.models.ProductType;
import campis.dp1.models.UnitOfMeasure;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Eddy
 */
public class CreateController implements Initializable {
    private Main main;
    
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField weightField;
    @FXML
    private JFXComboBox measureField;
    @FXML
    private JFXTextField trademarkField;
    @FXML
    private JFXTextField priceField;
    @FXML
    private JFXComboBox typeField;
    @FXML
    private JFXTextArea descripField;
    @FXML
    private JFXComboBox currencyType;
    
    @FXML
    private void goListProduct() throws IOException{
        main.showListProduct();
    }
    
    private void goCreateProduct(ActionEvent event) throws IOException{
        main.showCreateProduct();
    }
    
    public static Integer searchCodMeasure(String measure) throws SQLException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(UnitOfMeasure.class);
        criteria.add(Restrictions.eq("description",measure));
        Integer codMeasure;
        List rsMeasure = criteria.list();
        UnitOfMeasure result = (UnitOfMeasure)rsMeasure.get(0);
        codMeasure = result.getId_unit_of_measure();
        return codMeasure;
    }
    
    public static Integer searchCodType(String type) throws SQLException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ProductType.class);
        criteria.add(Restrictions.eq("description",type));
        Integer codType;
        List rsType = criteria.list();
        ProductType result = (ProductType) rsType.get(0);
        codType = result.getId_prodType();
        session.close();
        sessionFactory.close();
        return codType;
    }
    
    @FXML
    private void insertProduct (ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int measure = searchCodMeasure(measureField.getEditor().getText());
        int type = searchCodType(typeField.getEditor().getText());
        
        Product product = new Product(nameField.getText(), descripField.getText(), 1, 1, Float.parseFloat(weightField.getText()),
                                     trademarkField.getText(), Float.parseFloat(priceField.getText()), measure, type);
        
        session.save(product);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        this.goListProduct();
    }
    
    public static List<UnitOfMeasure> getUnitsOfMeasure() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(UnitOfMeasure.class)
                .setProjection(Projections.projectionList()
                .add(Projections.property("description"),"description"))
                .setResultTransformer(Transformers.aliasToBean(UnitOfMeasure.class));
        List<UnitOfMeasure> measures = criteria.list();
        return measures;
    }
    
    public static List<ProductType> getTypes() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ProductType.class)
                .setProjection(Projections.projectionList()
                .add(Projections.property("description"),"description"))
                .setResultTransformer(Transformers.aliasToBean(ProductType.class));
        List<ProductType> types = criteria.list();
        return types;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currencyType.getItems().addAll("S/.","$","€");
        List<UnitOfMeasure> list = getUnitsOfMeasure();
        for (int i = 0; i < list.size(); i++) {
            measureField.getItems().addAll(list.get(i).getDescription());
        }
        List<ProductType> typeList = getTypes(); 
        for (int i = 0; i < typeList.size(); i++) {
            typeField.getItems().addAll(typeList.get(i).getDescription());
        }
    }
}
