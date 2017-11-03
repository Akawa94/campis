/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campis.dp1.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author david
 */
public class SaleConditionDisplay {
    
    private final IntegerProperty id_sale_condition;
    private final StringProperty initial_date;
    private final StringProperty final_date;
    private final FloatProperty amount;
    private final StringProperty sale_condition_type;
    private final IntegerProperty limits;
    private final StringProperty applied_to;

    /**
     * @return the id_sale_condition
     */
    public IntegerProperty getId_sale_condition() {
        return id_sale_condition;
    }

    /**
     * @return the initial_date
     */
    public StringProperty getInitial_date() {
        return initial_date;
    }

    /**
     * @return the final_date
     */
    public StringProperty getFinal_date() {
        return final_date;
    }

    /**
     * @return the amount
     */
    public FloatProperty getAmount() {
        return amount;
    }

    /**
     * @return the sale_condition_type
     */
    public StringProperty getSale_condition_type() {
        return sale_condition_type;
    }

    /**
     * @return the limits
     */
    public IntegerProperty getLimits() {
        return limits;
    }

    /**
     * @return the applied_to
     */
    public StringProperty getApplied_to() {
        return applied_to;
    }

    public SaleConditionDisplay(IntegerProperty id_sale_condition, StringProperty initial_date, StringProperty final_date, FloatProperty amount, StringProperty sale_condition_type, IntegerProperty limits, StringProperty applied_to) {
        this.id_sale_condition = id_sale_condition;
        this.initial_date = initial_date;
        this.final_date = final_date;
        this.amount = amount;
        this.sale_condition_type = sale_condition_type;
        this.limits = limits;
        this.applied_to = applied_to;
    }
    
    
    
}
