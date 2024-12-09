package com.david.n1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.n1.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
