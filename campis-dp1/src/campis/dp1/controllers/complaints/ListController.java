package campis.dp1.controllers.complaints;

import campis.dp1.Main;
import campis.dp1.ContextFX;
import campis.dp1.models.Complaint;
import campis.dp1.models.ComplaintDisplay;
import campis.dp1.models.Permission;
import campis.dp1.models.Refund;
import campis.dp1.models.RefundLine;
import campis.dp1.models.RequestOrder;
import campis.dp1.models.RequestOrderLine;
import campis.dp1.models.View;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.Boolean.TRUE;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ListController implements Initializable {
    private Main main;
    private ObservableList<Complaint> complaints;
    private ObservableList<ComplaintDisplay> complaintsView;
    private int selected_id;
    private int id_role;
    private String selected_status;

    @FXML
    private Button searchButton;
    @FXML
    private JFXTextField searchField;
    @FXML
    private TableView<ComplaintDisplay> tableComplaint;
    @FXML
    private TableColumn<ComplaintDisplay,String> descriptionColumn;
    @FXML
    private TableColumn<ComplaintDisplay,Integer> idRequestColumn;
    @FXML
    private TableColumn<ComplaintDisplay,String> statusColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button createButton;

    @FXML
    private void goCreateComplaint() throws IOException {
        main.showCreateComplaint();
    }

    @FXML
    private void goAttendComplaint(ActionEvent event) throws IOException {
        if ((selected_id > 0) || selected_status.equals("En trámite")) {
            ContextFX.getInstance().setId(selected_id);
            main.goAttendComplaint(); 
        }
        /*Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<RequestOrderLine> request_order_lines = RequestOrder.getRequestOrderLines(selected_id);
        if (statusField.getValue().equals("Aceptado")) {
            Refund refund = new Refund(typeField.getValue(), id);
            session.save(refund);
            for (int i = 0; i < request_order_lines.size(); i++) {
                RefundLine refund_line = new RefundLine(request_order_lines.get(i).getId_request_order_line(), refund.getId_refund());
                session.save(refund_line);
            }
        }
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.selected_id = 0;
        id_role = (ContextFX.getInstance().getUser().getId_role());
        View whView = View.getView("complaints");

        if (!Permission.canModify(id_role, whView.getId_view())) {
            editButton.setVisible(false);
            createButton.setVisible(false);
        }
        tableComplaint.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            this.selected_id = newValue.getId_complaint().getValue().intValue();
            this.selected_status = newValue.getStatus().getValue().toString();
            }
        );
        try {
            descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescription());
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatus());
            idRequestColumn.setCellValueFactory(cellData -> cellData.getValue().getId_request_order().asObject());
            /**/
            loadData();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ObservableList<Complaint> getComplaints() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Complaint.class);
        List lista = criteria.list();
        ObservableList<Complaint> returnable;
        returnable = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            returnable.add((Complaint)lista.get(i));
        }
        session.close();
        sessionFactory.close();
        return returnable;
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        complaints = FXCollections.observableArrayList();
        complaintsView = FXCollections.observableArrayList();
        complaints = getComplaints();
        for (int i = 0; i < complaints.size(); i++) {
            ComplaintDisplay complaint = new ComplaintDisplay(complaints.get(i).getId_complaint(), complaints.get(i).getDescription(), complaints.get(i).getStatus(), complaints.get(i).getId_request_order());
            complaintsView.add(complaint);
        }
        tableComplaint.setItems(null);
        tableComplaint.setItems(complaintsView);
    }

    private void deleteComplaint(int cod) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Complaint.class);
        Complaint complaint = new Complaint();
        complaint.setId_complaint(cod);
        session.delete(complaint);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    @FXML
    private void deleteComplaint(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (selected_id > 0) {
            ContextFX.getInstance().setId(selected_id);
            Integer id_complaint = ContextFX.getInstance().getId();
            deleteComplaint(selected_id);
            for (int i = 0; i < complaints.size(); i++) {
                if (complaints.get(i).getId_complaint() == selected_id) {
                    complaints.remove(i);
                }
            }
            loadData();
        } 
    }
}
