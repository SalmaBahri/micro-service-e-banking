package ma.enset.bankaccountservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.bankaccountservice.dto.BankAccountRequestDTO;
import ma.enset.bankaccountservice.dto.BankAccountResponseDTO;
import ma.enset.bankaccountservice.entities.BankAccount;
import ma.enset.bankaccountservice.entities.Customer;
import ma.enset.bankaccountservice.repositories.BankAccountRepository;
import ma.enset.bankaccountservice.repositories.CustomerRepository;
import ma.enset.bankaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    private CustomerRepository customerRepository;

    @QueryMapping
    public List<BankAccount> accountsList() {
        return bankAccountRepository.findAll();
    }

    @QueryMapping
    public BankAccount bankAccountById(@Argument String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
    }

    @MutationMapping
    public BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount) {

        return accountService.addAccount(bankAccount);
    }
    @MutationMapping
    public BankAccountResponseDTO updateAccount(@Argument String id,@Argument BankAccountRequestDTO bankAccount) {

        return accountService.updateAccount(id,bankAccount);
    }
    @MutationMapping
    public Boolean deleteAccount(String id){
        bankAccountRepository.deleteById(id);
        return true;
    }
    @QueryMapping
    public List<Customer>customers(){
        return customerRepository.findAll();

    }
}




/*
record BankAccountDto(Double balance,String type, String currency){

}*/
