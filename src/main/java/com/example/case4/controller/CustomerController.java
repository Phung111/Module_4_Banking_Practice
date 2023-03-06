package com.example.case4.controller;

import com.example.case4.model.*;
import com.example.case4.repository.CustomerRepository;
import com.example.case4.service.customer.ICustomerService;
import com.example.case4.service.deposit.IDepositService;
import com.example.case4.service.transfer.ITransferService;
import com.example.case4.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private ITransferService transferService;

//---------------------------------------------GET------------------------------------------------//
    @GetMapping("")
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @GetMapping("/edit/{customerId}")
    public String showEditPage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        return "customer/edit";

    }

    @GetMapping("/delete/{customerId}")
    public String showDeletePage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        return "customer/delete";
    }

    @GetMapping("/view/{customerId}")
    public String showViewPage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        return "customer/view";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchKey") String searchKey, Model model) {

        String searchKeyJQuery = "%" + searchKey + "%";

        List<Customer> customers = customerService.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLikeAndDeletedIsFalse(searchKeyJQuery, searchKeyJQuery, searchKeyJQuery, searchKeyJQuery);

        model.addAttribute("customers", customers);
        model.addAttribute("searchKey", searchKey);

        return "customer/list";
    }



    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        Deposit deposit = new Deposit();
        deposit.setCustomer(customerOptional.get());
        model.addAttribute("deposit", deposit);

        return "banking/deposit";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customerOptional.get());

        model.addAttribute("withdraw", withdraw);

        return "banking/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(senderId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        Customer sender = customerOptional.get();

        List<Customer> recipients = customerService.findAllByIdNot(senderId);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);

//        model.addAttribute("sender", sender);
        model.addAttribute("recipients", recipients);
        model.addAttribute("transfer", transfer);

        return "banking/transfer";
    }

    @GetMapping("/banking-history")
    public String showBankingHistory(Model model){
        List<Transfer> transfers = transferService.findAll();
        List<Deposit> deposits = depositService.findAll();
        List<Withdraw> withdraws = withdrawService.findAll();

        model.addAttribute("transfers", transfers);
        model.addAttribute("deposits", deposits);
        model.addAttribute("withdraws", withdraws);

        return "banking/banking-history";
    }
//---------------------------------------------END GET------------------------------------------------//
//---------------------------------------------POST------------------------------------------------//

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute Customer customer,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes
    ) {

//        new Customer().validate(customer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            model.addAttribute("customer", customer);
            model.addAttribute("tittleMessage","Create Customer");
            model.addAttribute("message", "Create Customer Fail");
            return "customer/create";
        }

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);

        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("tittleMessage","Create Customer");
        redirectAttributes.addFlashAttribute("message", "Create New Customer Successfully");

        return "redirect:/customers";
    }

    @PostMapping("/edit/{customerId}")
    public String update(@PathVariable Long customerId,
                         @Validated @ModelAttribute Customer customer,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         BindingResult bindingResult
    ) {

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            model.addAttribute("customer", customer);
            model.addAttribute("tittleMessage","Edit Customer");
            model.addAttribute("message", "Edit Customer Fail");
            return "customer/edit";
        }

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }


        customer.setBalance(customerOptional.get().getBalance());
        customer.setId(customerId);
        customerService.save(customer);

        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("tittleMessage","Edit Customer");
        redirectAttributes.addFlashAttribute("message", "Edit Customer Successfully");

        return "redirect:/customers";
    }


    @PostMapping("/delete/{customerId}")
    public String delete(@PathVariable Long customerId,
                         @ModelAttribute Customer customer,
                         Model model,
                         RedirectAttributes redirectAttributes
    ) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }
        customer.setId(customerId);
        customer.setBalance(customerOptional.get().getBalance());

        customerService.delete(customer);

        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("tittleMessage","Delete Customer");
        redirectAttributes.addFlashAttribute("message", "Delete Customer Successfully");

        return "redirect:/customers";
    }



    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId,
                          @Validated @ModelAttribute Deposit deposit,
                          BindingResult bindingResult,
                          Model model ) {
        try{
            if (bindingResult.hasFieldErrors()) {
                model.addAttribute("deposit", deposit);
                model.addAttribute("errors", true);
                model.addAttribute("tittleMessage","Deposit");
                model.addAttribute("message", "Deposit Fail");
                return "banking/deposit";
            }

            Optional<Customer> customerOptional = customerService.findById(customerId);
            if (!customerOptional.isPresent()) {
                return "error/404";
            }

            BigDecimal transactionAmount = deposit.getTransactionAmount();

            Customer customer = customerOptional.get();
            BigDecimal currentBalance = customer.getBalance();

            BigDecimal newBalance = currentBalance.add(transactionAmount);

            customer.setBalance(newBalance);

            deposit.setCustomer(customer);

            customerService.deposit(deposit);

            deposit.setTransactionAmount(BigDecimal.ZERO);

            model.addAttribute("deposit", deposit);

            model.addAttribute("success", true);
            model.addAttribute("tittleMessage","Deposit");
            model.addAttribute("message", "Deposit Successfully");

        }
        catch(NullPointerException e ){
            model.addAttribute("errors", true);
            model.addAttribute("tittleMessage","Deposit");
            model.addAttribute("message", "Transaction Amount cant not blank");
            return "banking/deposit";
        }
        catch (NumberFormatException e){
            model.addAttribute("errors", true);
            model.addAttribute("tittleMessage","Deposit");
            model.addAttribute("message", "Transaction Amount is not valid number");
            return "banking/deposit";
        }
        return "banking/deposit";
    }

    @PostMapping("/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId,
                           @Validated @ModelAttribute Withdraw withdraw,
                           BindingResult bindingResult,
                           Model model) {
        try {
            if (bindingResult.hasFieldErrors()) {
                model.addAttribute("errors", true);
                model.addAttribute("tittleMessage", "Withdraw");
                model.addAttribute("message", "Withdraw Fail");
                return "banking/withdraw";
            }

            Optional<Customer> customerOptional = customerService.findById(customerId);
            if (!customerOptional.isPresent()) {
                return "error/404";
            }

            Customer customer = customerOptional.get();
            BigDecimal currentBalance = customer.getBalance();
            BigDecimal transactionAmount = withdraw.getTransactionAmount();

            if (currentBalance.compareTo(transactionAmount) < 0) {
                model.addAttribute("error", true);
            } else {
                BigDecimal newBalance = currentBalance.subtract(transactionAmount);

                customer.setBalance(newBalance);

                withdraw.setCustomer(customer);

                customerService.withdraw(withdraw);

                withdraw.setTransactionAmount(BigDecimal.ZERO);

                model.addAttribute("success", true);
                model.addAttribute("tittleMessage", "Withdraw");
                model.addAttribute("message", "Withdraw Successfully");
            }
        }
        catch(NullPointerException e ){
            model.addAttribute("errors", true);
            model.addAttribute("tittleMessage","Withdraw");
            model.addAttribute("message", "Transaction Amount cant not blank");
            return "banking/withdraw";
        }
        catch (NumberFormatException e){
            model.addAttribute("errors", true);
            model.addAttribute("tittleMessage","Withdraw");
            model.addAttribute("message", "Transaction Amount is not valid number");
            return "banking/withdraw";
        }
        return "banking/withdraw";
    }

    @PostMapping("/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId,
                           @Validated @ModelAttribute Transfer transfer,
                           BindingResult bindingResult,
                           Model model) {
        try {
            List<Customer> recipients = customerService.findAllByIdNot(senderId);
            model.addAttribute("recipients", recipients);

            if (bindingResult.hasFieldErrors()) {
                model.addAttribute("errors", true);
                model.addAttribute("tittleMessage", "Transfer");
                model.addAttribute("message", "Transfer Fail");
                return "banking/transfer";
            }

            Optional<Customer> senderOptional = customerService.findById(senderId);
            if (!senderOptional.isPresent()) {
                return "error/404";
            }

            Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());
            if (!recipientOptional.isPresent()) {
                return "error/404";
            }

            Customer sender = senderOptional.get();
            BigDecimal senderBalance = sender.getBalance();

            Customer recipient = recipientOptional.get();
            BigDecimal recipientBalance = recipient.getBalance();

            BigDecimal transferAmount = transfer.getTransferAmount();
            long fees = 10L;
            BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
            BigDecimal transactionAmount = transferAmount.add(feesAmount);

            BigDecimal minAmount = BigDecimal.valueOf(10000L);
            BigDecimal maxAmount = BigDecimal.valueOf(1000000L);

            if (transferAmount.compareTo(minAmount) < 0) {
                model.addAttribute("error", true);
                model.addAttribute("errors", true);
                model.addAttribute("message", "Số tiền chuyển khoản tối thiểu là $10.000");
                model.addAttribute("tittleMessage", "Transfer");
            } else if (transferAmount.compareTo(maxAmount) > 0) {
                model.addAttribute("error", true);
                model.addAttribute("errors", true);
                model.addAttribute("message", "Số tiền chuyển khoản tối đa là $100.000.000");
                model.addAttribute("tittleMessage", "Transfer");
            } else {
                if (senderBalance.compareTo(transactionAmount) < 0) {
                    model.addAttribute("error", true);
                    model.addAttribute("errors", true);
                    model.addAttribute("message", "Số dư người gửi không đủ để thực hiện giao dịch chuyển khoản");
                    model.addAttribute("tittleMessage", "Transfer");

                    return "banking/transfer";
                }

                if (sender.getId().equals(recipient.getId())) {
                    model.addAttribute("error", true);
                    model.addAttribute("errors", true);
                    model.addAttribute("message", "Tài khoản người nhận không hợp lệ");
                    model.addAttribute("tittleMessage", "Transfer");

                    return "banking/transfer";
                }

                BigDecimal newSenderBalance = senderBalance.subtract(transactionAmount);
                sender.setBalance(newSenderBalance);

                BigDecimal newRecipientBalance = recipientBalance.add(transferAmount);
                recipient.setBalance(newRecipientBalance);

                transfer.setSender(sender);
                transfer.setRecipient(recipient);
                transfer.setFees(fees);
                transfer.setFeesAmount(feesAmount);
                transfer.setTransactionAmount(transactionAmount);

                customerService.transfer(transfer);

                model.addAttribute("success", true);
                model.addAttribute("tittleMessage", "Transfer");
                model.addAttribute("message", "Transfer Successfully");
            }
        }
        catch(NullPointerException e ){
                model.addAttribute("errors", true);
                model.addAttribute("tittleMessage","Transfer");
                model.addAttribute("message", "Transaction Amount cant not blank");
                return "banking/transfer";
        }
        catch (NumberFormatException e){
            model.addAttribute("errors", true);
            model.addAttribute("tittleMessage","Transfer");
            model.addAttribute("message", "Transaction Amount is not valid number");
            return "banking/transfer";
        }
        return "banking/transfer";
    }


//---------------------------------------------END POST------------------------------------------------//


}
