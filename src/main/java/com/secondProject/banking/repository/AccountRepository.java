package com.secondProject.banking.repository;

import com.secondProject.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account , Long> {
}
