package com.example.case4.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;


@Entity
@Table(name = "deposits")
public class Deposit extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    @DecimalMin(value = "1000", message = "Số tiền nạp tối thiểu là 1,000 đ")
    @DecimalMax(value = "1000000", message = "Số tiền nạp tối đa là 1,000,000 đ")
//    @NotBlank(message = "Transsaction Amount can not blank")
//    @Pattern(regexp = "[0-9]", message = "Transaction Amount is not valid number")
//    @NotNull(message = "Transsaction Amount can not blank")

    private BigDecimal transactionAmount;

    public Deposit() {
    }

    public Deposit(Long id, Customer customer, BigDecimal transactionAmount) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Deposit.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Deposit deposit = (Deposit) target;

        String transactionAmount = String.valueOf(deposit.getTransactionAmount());


        if (transactionAmount.length() == 0) {
            errors.rejectValue("transactionAmount", "transactionAmount.null");
        }

        if (!transactionAmount.matches("[0-9]")) {
            errors.rejectValue("transactionAmount", "transactionAmount.number");
        }
    }
}

