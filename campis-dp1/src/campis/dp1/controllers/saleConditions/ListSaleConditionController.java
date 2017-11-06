/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.controllers.saleConditions;

import campis.dp1.ContextFX;
import campis.dp1.Main;
import campis.dp1.models.Campaign;
import campis.dp1.models.Product;
import campis.dp1.models.ProductType;
import campis.dp1.models.SaleCondition;
import campis.dp1.models.SaleConditionDisplay;
import campis.dp1.models.SaleConditionType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * FXML Controller class
 *
 * @author david
 */
public class ListSaleConditionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Main main;
    private ObservableList<SaleCondition> condiciones;
    private ObservableList<SaleConditionDisplay> condicionesView;

    
    private int selected_id;
    
    @FXML
    private TableView<SaleConditionDisplay> saleCondTable;

    @FXML
    private TableColumn<SaleConditionDisplay, Integer> codColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, String> initialColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, String> endColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, Float> amountColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, String> typeColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, String> totakeColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, Integer> limitColumn;

    @FXML
    private TableColumn<SaleConditionDisplay, String> campaignColumn;
    
    @FXML
    private JFXDatePicker pickerInitial;

    @FXML
    private JFXDatePicker pickerFinal;
    
    @FXML
    private JFXComboBox<String> cmbCampaign;
    
    //
    @FXML
    private Label iniini;

    @FXML
    private Label endend;
    //
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Campaign> cmpList = getCampaigns();
        cmbCampaign.getItems().addAll("");
        for (int i = 0; i < cmpList.size(); i++) {
            cmbCampaign.getItems().addAll(cmpList.get(i).getName());
        }
        
        saleCondTable.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            this.selected_id = newValue.getId_sale_condition().getValue().intValue();
            }
        );
        try {
            codColumn.setCellValueFactory(cellData -> cellData.getValue().getId_sale_condition().asObject());
            initialColumn.setCellValueFactory(cellData -> cellData.getValue().getInitial_date());
            endColumn.setCellValueFactory(cellData -> cellData.getValue().getFinal_date());
            amountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmount().asObject());
            typeColumn.setCellValueFactory(cellData -> cellData.getValue().getSale_condition_type());
            totakeColumn.setCellValueFactory(cellData -> cellData.getValue().getApplied_to());
            limitColumn.setCellValueFactory(cellData -> cellData.getValue().getLimits().asObject());
            campaignColumn.setCellValueFactory(cellData -> cellData.getValue().getCampaign());
            /**/
            cargarData();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ListSaleConditionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public static Integer searchCodCampaign(String campaign) throws SQLException, ClassNotFoundException {
        if (campaign == "") return -1;
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Campaign.class);
        criteria.add(Restrictions.eq("name",campaign));
        Integer cod;
        List rsCampaign = criteria.list();
        Campaign result = (Campaign)rsCampaign.get(0);
        cod = result.getId_campaign();
        return cod;
    }
    
    
    private Date getDate(LocalDate value) {
        
        Calendar calendar = new GregorianCalendar(value.getYear(),
                                                    value.getMonthValue(),
                                                    value.getDayOfMonth());
        return calendar.getTime();
    }
    
    @FXML
    private void searchButtonAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String txtCampaign = this.cmbCampaign.getValue();
        int idCampaignSelected = searchCodCampaign(txtCampaign);
        
        
        Date dateInit = getDate(pickerInitial.getValue());
        Date dateEnd = getDate(pickerFinal.getValue());
        //
        iniini.setText(dateInit.toString());
        endend.setText(dateEnd.toString());
        //
        if (txtCampaign.compareTo("") == 0) { //faltan condiciones
            cargarData();
        } else {
            condiciones = FXCollections.observableArrayList();
            condicionesView = FXCollections.observableArrayList();
            condiciones = getSearchList(idCampaignSelected,dateInit,dateEnd);
            
        
        for (int i = 0; i < condiciones.size(); i++) {

            SaleConditionDisplay sc = new SaleConditionDisplay(condiciones.get(i).getId_sale_condition(), condiciones.get(i).getInitial_date().toString(),
                    condiciones.get(i).getFinal_date().toString(), condiciones.get(i).getAmount(), getType(condiciones.get(i).getId_sale_condition_type()), 
                    condiciones.get(i).getLimits(),getObjective(condiciones.get(i).getId_to_take(),condiciones.get(i).getId_sale_condition_type()),
                    getCampaign(condiciones.get(i).getId_campaign()));
            condicionesView.add(sc);
        }
        saleCondTable.setItems(null);
        saleCondTable.setItems(condicionesView);
        }
    }
    
    private ObservableList<SaleCondition> getSearchList(int id, Date ini, Date fin) {
        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        ObservableList<SaleCondition> returnable;
        returnable = FXCollections.observableArrayList();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(SaleCondition.class);
        List<SaleCondition> list = criteria.list();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getId_campaign() == id) && 
               (list.get(i).getInitial_date().compareTo(Timestamp.valueOf(formatIn.format(ini))) >= 0) &&
               (list.get(i).getFinal_date().compareTo(Timestamp.valueOf(formatIn.format(fin))) <= 0))
                {
                    returnable.add(list.get(i));
                }
        }
        session.close();
        sessionFactory.close();
        return returnable;
    }
    
    
    
    private void cargarData() throws SQLException, ClassNotFoundException {
        String campaign, type, objective;
        int id_type;
        
        condiciones = FXCollections.observableArrayList();
        condicionesView = FXCollections.observableArrayList();
        condiciones = getSaleConditions();
        
        for (int i = 0; i < condiciones.size(); i++) {

            SaleConditionDisplay sc = new SaleConditionDisplay(condiciones.get(i).getId_sale_condition(), condiciones.get(i).getInitial_date().toString(),
                    condiciones.get(i).getFinal_date().toString(), condiciones.get(i).getAmount(), getType(condiciones.get(i).getId_sale_condition_type()), 
                    condiciones.get(i).getLimits(),getObjective(condiciones.get(i).getId_to_take(),condiciones.get(i).getId_sale_condition_type()),
                    getCampaign(condiciones.get(i).getId_campaign()));
            condicionesView.add(sc);
        }
        saleCondTable.setItems(null);
        saleCondTable.setItems(condicionesView);
    }
    
    
    public static String getCampaign(int cod) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Campaign.class);
        criteria.add(Restrictions.eq("id_campaign",cod));
        String descripType;
        List rsType = criteria.list();
        Campaign result = (Campaign) rsType.get(0);
        descripType = result.getName();
        session.close();
        sessionFactory.close();

        return descripType;
    }
    
    public static List<Campaign> getCampaigns() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Campaign.class)
                .setProjection(Projections.projectionList()
                .add(Projections.property("name"),"name"))
                .setResultTransformer(Transformers.aliasToBean(Campaign.class));
        List<Campaign> types = criteria.list();
        return types;
    }
    
    public static String getType(int cod) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(SaleConditionType.class);
        criteria.add(Restrictions.eq("id_sale_condition_type",cod));
        String descripType;
        List rsType = criteria.list();
        SaleConditionType result = (SaleConditionType) rsType.get(0);
        descripType = result.getDescription();
        session.close();
        sessionFactory.close();

        return descripType;
    }
    
    public static String getObjective(int cod, int type) {
        String descripType;
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if (type == 1){
            Criteria criteria = session.createCriteria(Product.class);
            criteria.add(Restrictions.eq("id_product",cod));
            
            List rsType = criteria.list();
            Product result = (Product) rsType.get(0);
            descripType = result.getName();
        }else{
            Criteria criteria = session.createCriteria(ProductType.class);
            criteria.add(Restrictions.eq("id_product_type",cod));
            
            List rsType = criteria.list();
            ProductType result = (ProductType) rsType.get(0);
            descripType = result.getDescription();
        }
        session.close();
        sessionFactory.close();

        return descripType;
    }
    
    
    private ObservableList<SaleCondition> getSaleConditions() {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(SaleCondition.class);
        List lista = criteria.list();
        ObservableList<SaleCondition> returnable;
        returnable = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            returnable.add((SaleCondition) lista.get(i));
        }
        session.close();
        sessionFactory.close();
        return returnable;
    }
    
    @FXML
    private void goNewSaleCondition(ActionEvent event) throws IOException {
        main.showNewSaleCondition();
    }

    @FXML
    private void goEditSaleCondition(ActionEvent event) throws IOException {
        ContextFX.getInstance().setId(selected_id);
        main.showEditSaleCondition();
    }
    
    @FXML
    private void goViewSaleCondition(ActionEvent event) throws IOException {
        ContextFX.getInstance().setId(selected_id);
        main.showViewSaleCondition();
    }
    
    
    
    private void deleteSaleCondition(int cod) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(SaleCondition.class);
        SaleCondition sc = new SaleCondition();
        sc.setId_sale_condition(cod);
        session.delete(sc);
        session.getTransaction().commit();

        sessionFactory.close();
    }
    
    
    
    
    @FXML
    private void deleteSaleCondition(ActionEvent event) throws SQLException, ClassNotFoundException {
        ContextFX.getInstance().setId(selected_id);
        Integer id_sale_condition = ContextFX.getInstance().getId();
        deleteSaleCondition(selected_id);
        
        cargarData();
    }
}
