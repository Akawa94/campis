/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.controllers;

import campis.dp1.Main;
import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 * @author Marco
 */
public class CommerceSidebarController {
    
    private Main main;
    
    @FXML
    private void goListRequestOrder() throws IOException{
        main.showListRequestOrder();
    }
    
}
