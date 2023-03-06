package com.example.case4.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "customers")
//@SQLDelete(sql = "UPDATE customers SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "Name can not blank")
    private String fullName;

    @Column(nullable = false)
    @NotBlank(message = "Email can not blank")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$", message = "Email is not valid")
    private String email;

    private String phone;
    private String address;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal balance;

    @OneToMany(targetEntity = Deposit.class)
    private List<Deposit> deposits;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> senders;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> recipients;

    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Customer.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//
//        Customer customer = (Customer) target;
//
//        String fullName = customer.getFullName();
//        String email = customer.getEmail();
//
//        if (fullName.length() == 0) {
//            errors.rejectValue("fullName", "fullName.null");
//        }
//
//        if (!email.matches("^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
//            errors.rejectValue("email", "email.matches");
//        }
//
//    }
}
