/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.controllers.departures;

import campis.dp1.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author david
 */
public class ListDepartureController implements Initializable {

    private Main main;
    
    @FXML
    private void goVisualizeDeparture() throws IOException {
        main.showVisualizeDeparture();
    }
    
    @FXML
    private void goNewDeparture() throws IOException {
        main.showNewDeparture();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
