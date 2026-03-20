package org.assignment.loanapp.repository;

import org.assignment.loanapp.models.LoanResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class LoanRepository {


    private final Map<String, LoanResponse> store = new HashMap<>();

    public void save(String id, LoanResponse response) {
        store.put(id, response);
    }

    public Optional<LoanResponse> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}