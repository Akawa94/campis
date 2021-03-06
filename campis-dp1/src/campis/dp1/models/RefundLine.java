package campis.dp1.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Eddy
 */
@Entity
@Table (name="refund_line")
public class RefundLine { 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id_refund_line;
    Integer id_refund;
    Integer quantity;
    Integer id_product;

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public RefundLine() {
        super();
    }

    public RefundLine(Integer id_refund, Integer quant) {
        this.quantity = quant;
        this.id_refund = id_refund;
    }
    
    public RefundLine(Integer id_refund, Integer quant, Integer id_product) {
        this.quantity = quant;
        this.id_refund = id_refund;
        this.id_product = id_product;
    }

    /**
     * @return the id_refund_line
     */
    public Integer getId_refund_line() {
        return id_refund_line;
    }

    /**
     * @param id_refund_line the id_refund_line to set
     */
    public void setId_refund_line(Integer id_refund_line) {
        this.id_refund_line = id_refund_line;
    }

    /**
     * @return the id_refund
     */
    public Integer getId_refund() {
        return id_refund;
    }

    /**
     * @param id_refund the id_refund to set
     */
    public void setId_refund(Integer id_refund) {
        this.id_refund = id_refund;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer get_idProduct() {
        return id_product;
    }
}